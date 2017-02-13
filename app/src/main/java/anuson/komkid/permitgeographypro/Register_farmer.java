package anuson.komkid.permitgeographypro;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Register_farmer extends Activity {

    GetCurrentLocation currentLoc;

    private EditText userEditText, passEditText, con_passEditText, nameEditText, addEditText, keyEditText, telEditText, name_famEditText, tpyeEditText, aresEditText, add_famEditText;
    private String userString, passString, con_passString, nameString, addString, keyString, telString, name_famString, tpyeString, aresString, add_famString;
    private String addImageString;
    private ImageView addImagefarmer;
    private boolean aBoolean = true;
    private static final String urlPHP = "http://swiftcodingthai.com/gam/php_add_member_farmer.php";
    private Uri uri;
    private String LatitudeStrings, LongitudeStrings;
    private String lattitude;
    private String longtitude;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_farmer);

        currentLoc = new GetCurrentLocation(this);

        Intent intent = getIntent();
        lattitude = intent.getStringExtra("lat");
        longtitude = intent.getStringExtra("long");

        Log.i("farmerLat", lattitude);
        Log.i("farmerLong", longtitude);

        nameEditText = (EditText) findViewById(R.id.editText3);
        addEditText = (EditText) findViewById(R.id.editText4);
        keyEditText = (EditText) findViewById(R.id.editText5);
        telEditText = (EditText) findViewById(R.id.editText6);

        name_famEditText = (EditText) findViewById(R.id.editText16);
        tpyeEditText = (EditText) findViewById(R.id.editText17);
        aresEditText = (EditText) findViewById(R.id.editText18);
        add_famEditText = (EditText) findViewById(R.id.editText20);

        userEditText = (EditText) findViewById(R.id.editText15);
        passEditText = (EditText) findViewById(R.id.editText22);
        con_passEditText = (EditText) findViewById(R.id.editText23);

        addImagefarmer = (ImageView) findViewById(R.id.imageView_byfarmer);

        imageviewController();


    }//onCreate

    private void imageviewController() {
        addImagefarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "กรุณาเลือกรูปภาพ "), 1);
            }
        });
    }//imageviewController()

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1://image1
                    aBoolean = false;
                    setupImage(addImagefarmer, uri);
                    addImageString = myFindPath(uri);
                    Log.d("9janV1", "Path==>" + addImagefarmer);
                    break;
            }
        }
    }//onActivityResult

    private String myFindPath(Uri uri) {
        String result = null;
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(i);
        } else {
            result = uri.getPath();
        }
        return result;
    }//myFindPath

    private void setupImage(ImageView addImagefarmer, Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory
                    .decodeStream(getContentResolver().openInputStream(uri));
            addImagefarmer.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//setupImage

    public void clickSaveData(View view) {
        nameString = nameEditText.getText().toString().trim();
        addString = addEditText.getText().toString().trim();
        keyString = keyEditText.getText().toString().trim();
        telString = telEditText.getText().toString().trim();

        name_famString = name_famEditText.getText().toString().trim();
        tpyeString = tpyeEditText.getText().toString().trim();
        aresString = aresEditText.getText().toString().trim();
        add_famString = add_famEditText.getText().toString().trim();

        userString = userEditText.getText().toString().trim();
        passString = passEditText.getText().toString().trim();
        con_passString = con_passEditText.getText().toString().trim();

        int length = passString.length();

        if (checkSpace()) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกทุกช่องครับ"); //เมื่อมีช่องว่างให้แสดง ข้อความ

        } else if (length < 8) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "รหัสผิดพลาด", "กรุณากรอกรหัสมากกว่า 8 ตัวขึ้นไป");

        } else if (!passString.equals(con_passString)) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "รหัสไม่ตรงกัน", "กรุณากรอกรหัสผ่านให้ตรงกันครับ");//เช็ครหัสผ่านให้ตรง

        } else if (aBoolean) {
            // Non Choose Image
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ยังไม่ได้เลือกรูป", "กรุณาเลือกรูปภาพ");

        } else {
            CheckUser checUser = new CheckUser(this);
            checUser.execute();
        }

    }//clickSaveData

    private class CheckUser extends AsyncTask<Void, Void, String> {
        private Context context;
        private static final String urlJSON = "http://swiftcodingthai.com/gam/php_get_member_farmer.php";
        private boolean aBoolean = true;

        public CheckUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("15JanV1", "e doInBack ==>" + e.toString());
                return null;
            }
        }//doInback

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("2octv1", "JSON==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i += 1) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (userString.equals(jsonObject.getString("mem_user"))) {

                        aBoolean = false; //User ซ้ำ

                    }//if
                }//for
                if (aBoolean) {
                    //User OK
                    confirmData_Byfarmer();
                } else {
                    //User Not OK
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "User ซ้ำ",
                            "กรุณาเปลี่ยน ชื่อผู้ใช้งาน");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }//onPost
    }

    private void confirmData_Byfarmer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.dule_icon);
        builder.setTitle("โปรดรวจสอบข้อมูล");
        builder.setMessage("ชื่อผู้ใช้งาน = " + userString + "\n" +
                "ชื่อ-นามสกุล = " + nameString + "\n" +
                "เบอร์โทรติดต่อ = " + telString + "\n");
        //โชวข้อมูลการกรอก การแจ้งเตือนครั้งสุดท้าย

        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadToServer();
                uploadToImage();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void uploadToImage() {
        try {
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "gam@swiftcodingthai.com",
                    "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("ImageFarmer");
            simpleFTP.stor(new File(addImageString));
            simpleFTP.disconnect();

            Toast.makeText(Register_farmer.this, "บันทึกรูปภาพ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void uploadToServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Username", userString)
                .add("Password", passString)
                .add("Name", nameString)
                .add("Add", addString)
                .add("Telephone", telString)
                .add("Key", keyString)
                .add("Farmername", name_famString)
                .add("Farmertype", tpyeString)
                .add("Farmerarea", aresString)
                .add("Latitude", lattitude)
                .add("Longtitude", longtitude)
                .add("Add_farm", add_famString)
                .add("pictures", "http://swiftcodingthai.com/gam/ImageFarmer" +
                        addImageString.substring(addImageString.lastIndexOf("/")))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("4SepV2", "e==>" + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("4SepV2", "Result ==>" + response.body().string());

                finish();
            }
        });
    }

    private boolean checkSpace() {
        return nameString.equals("") ||
                addString.equals("") ||
                keyString.equals("") ||
                telString.equals("") ||
                name_famString.equals("") ||
                tpyeString.equals("") ||
                aresString.equals("") ||
                addString.equals("") ||
                userString.equals("") ||
                passString.equals("") ||
                con_passString.equals("");
    }
} //Main Class


