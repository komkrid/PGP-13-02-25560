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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Register_user extends Activity {

    private EditText nameEditText, addEditText, emailEditText, telEditText, keyEditText, usernameEditText, passwordEditText, password_conEditText;
    private String nameString, addString, emailString, telString, keyString, usernameString, passwordString, password_conString, pathImageString;
    private ImageView addusersImageView;
    private Uri uri;
    private boolean aBoolean = true;

    private static final String urlPHP = "http://swiftcodingthai.com/gam/php_add_member_user.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        nameEditText = (EditText) findViewById(R.id.editText7);
        addEditText = (EditText) findViewById(R.id.editText9);
        emailEditText = (EditText) findViewById(R.id.editText10);
        telEditText = (EditText) findViewById(R.id.editText11);
        keyEditText = (EditText) findViewById(R.id.editText12);

        usernameEditText = (EditText) findViewById(R.id.editText8);
        passwordEditText = (EditText) findViewById(R.id.editText13);
        password_conEditText = (EditText) findViewById(R.id.editText14);

        addusersImageView = (ImageView) findViewById(R.id.imageView8);

        imageviewController();

    }//onCreate

    private void imageviewController() {
        addusersImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "กรุณาเลือกรูปภาพ"), 1);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1://image1
                    aBoolean = false;
                    setupImage(addusersImageView, uri);
                    pathImageString = myFindPath(uri);
                    Log.d("9janV1","Path==>" + pathImageString);
                    break;
            }
        }
    }
    private  String myFindPath(Uri uri){
        String result = null;
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings,null,null,null);
        if(cursor !=null) {
            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(i);
        }else {
            result = uri.getPath();
        }
        return  result;
    }
    private void setupImage(ImageView imageView, Uri uri){
        try{
            Bitmap bitmap = BitmapFactory
                    .decodeStream(getContentResolver().openInputStream(uri));
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clickSaveData(View view){
        nameString  = nameEditText.getText().toString().trim();
        addString   = addEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();
        telString   = telEditText.getText().toString().trim();
        keyString   = keyEditText.getText().toString().trim();
        usernameString = usernameEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        password_conString = password_conEditText.getText().toString().trim();

        int length = passwordString.length();

        if (checkSpace()) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกทุกช่องครับ"); //เมื่อมีช่องว่างให้แสดง ข้อความ

        }else if(length<8){
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "รหัสผิดพลาด","กรุณากรอกรหัสมากกว่า 8 ตัวขึ้นไป");

        }else if (!passwordString.equals(password_conString)){
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "รหัสไม่ตรงกัน","กรุณากรอกรหัสผ่านให้ตรงกันครับ");//เช็ครหัสผ่านให้ตรง

        } else if (aBoolean) {
            // Non Choose Image
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ยังไม่ได้เลือกรูป", "กรุณาเลือกรูปภาพ");

        }else {

            CheckUser checkUser = new CheckUser(this);
            checkUser.execute();
        }
    }//clickSaveData

    private class CheckUser extends AsyncTask<Void, Void, String>{
        //Explict
        private Context context;
        private static final String urlJSON="http://swiftcodingthai.com/gam/php_get_member_user.php";
        private boolean aBoolean = true;

        public CheckUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new  Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            }catch (Exception e){
                Log.d("2octV1","e doInBack ==>" + e.toString());
                return null;
            }
        }//DoInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("2octv1","JSON==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i+=1){
                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    if (usernameString.equals(jsonObject.getString("mem_u_user"))) {

                        aBoolean = false; //User ซ้ำ

                    }//if
                }//for
                if (aBoolean) {
                    //User OK
                    confirmData();
                }else {
                    //User Not OK
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context,"User ซ้ำ",
                            "กรุณาเปลี่ยน ชื่อผู้ใช้งาน");

                }

            }catch (Exception e ){
                e.printStackTrace();
            }
        }//onPost
    }//CheckUser Class




    private void confirmData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.dule_icon);
        builder.setTitle("โปรดรวจสอบข้อมูล");
        builder.setMessage("ชื่อผู้ใช้งาน = " + usernameString + "\n"+
                "ชื่อ-นามสกุล = " + nameString + "\n"+
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
    }//confirmData
    private void uploadToImage() {

        try {
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "gam@swiftcodingthai.com",
                    "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("ImageUser");
            simpleFTP.stor(new File(pathImageString));
            simpleFTP.disconnect();

            Toast.makeText(Register_user.this, "บันทึกรูปภาพ", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void uploadToServer(){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Username", usernameString)
                .add("Password", passwordString)
                .add("Name", nameString)
                .add("Add", addString)
                .add("Email", emailString)
                .add("Telephone", telString)
                .add("Key", keyString)
                .add("Image", "http://swiftcodingthai.com/gam/ImageUser" +
                        pathImageString.substring(pathImageString.lastIndexOf("/")))
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
                Log.d("4SepV2", "Result ==>"+ response.body().string());

                finish();
            }
        });
    }//uploadToServer

    private boolean checkSpace() {
        return  nameString.equals("")||
                addString.equals("")||
                emailString.equals("")||
                telString.equals("")||
                keyString.equals("")||
                usernameString.equals("")||
                passwordString.equals("")||
                password_conString.equals(""); //เมื่อมีช่องว่าง
    }//checkSpace

}//Main Method