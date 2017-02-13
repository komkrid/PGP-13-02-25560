package anuson.komkid.permitgeographypro;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class Menu_farmer_2 extends Activity {

    //Explicit
    private String[] userLoginStrings;
    private EditText tiltleEditText, textEditText;
    private String tiltleString, textString, memberString, datatimeString, endTimeString,
            pathImage1String, pahtImage2String;
    private static final String urlPHP = "http://swiftcodingthai.com/gam/php_add_post.php";
    private Spinner spnITEM;
    private TextView textView;
    private int dayAnTnt;
    private ImageView image1ImageView, image2ImageView;
    private Uri uri;
    private boolean aBoolean = true, a2Boolean = true;
    private Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_farmer_2);

        //ฺฺBind Widget
        tiltleEditText = (EditText) findViewById(R.id.editText19);
        textEditText = (EditText) findViewById(R.id.editText21);
        textView = (TextView) findViewById(R.id.textView16);
        spnITEM = (Spinner) findViewById(R.id.spinner);
        image1ImageView = (ImageView) findViewById(R.id.imageView4);
        image2ImageView = (ImageView) findViewById(R.id.imageView3);
        button = (Button) findViewById(R.id.button3);


        //Get Valuse From
        userLoginStrings = getIntent().getStringArrayExtra("Login");

        //Check userLogin
        for (int i = 0; i < userLoginStrings.length; i++) {
            Log.d("4novV3", "userLogin" + i + "]=" + userLoginStrings[i]);
        }//for

        // Show Text
        textView.setText(userLoginStrings[7]);

        // Current Date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatteDate = df.format(c.getTime());
        //txtResult


        datatimeString = formatteDate;
        Log.d("27novV1", "dataTimeString ==>" + datatimeString);


        //Create Spinner
        createSpinner();

        //Controller
        image1Controller();
        image2Controller();


    }//onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=null;
        uri = data.getData();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        try {

              bitmap = BitmapFactory
                    .decodeStream(getContentResolver().openInputStream(uri));


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (resultCode == RESULT_OK) {


            switch (requestCode) {
                case 1: // image1

                    aBoolean = false;
                    setupImage(image1ImageView, uri);


                    pathImage1String = "Image-"+ n +".jpg";
                    storeImage(bitmap,pathImage1String);
                   // pathImage1String = myFindPath(uri);

                    Toast.makeText(this, ""+pathImage1String, Toast.LENGTH_SHORT).show();
                    Log.d("18decV1", "Path ==> " + pathImage1String);
                    break;
                case 2: // image2

                    a2Boolean = false;
                    setupImage(image2ImageView, uri);
                  //  pahtImage2String = myFindPath(uri);
                    pahtImage2String = "Image-"+ n +".jpg";
                    storeImage(bitmap,pahtImage2String);
                    Toast.makeText(this, ""+pahtImage2String, Toast.LENGTH_SHORT).show();

                    break;
            }   // switch

        }


    }   // onActivityResult

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
    }

    private void setupImage(ImageView imageView, Uri uri) {
        try {

            Bitmap bitmap = BitmapFactory
                    .decodeStream(getContentResolver().openInputStream(uri));
            imageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void image1Controller() {
        image1ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Choose App"), 1);
            }
        });
    }

    private void image2Controller() {
        image2ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Choose App"), 2);
            }
        });
    }

    private void createSpinner() {
        String[] strings = getResources().getStringArray(R.array.dayLeng);
        final int[] ints = new int[]{1, 2, 3, 4, 5, 6, 7};


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(Menu_farmer_2.this,
                android.R.layout.simple_list_item_1, strings);

        spnITEM.setAdapter(stringArrayAdapter);

        spnITEM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                dayAnTnt = ints[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayAnTnt = ints[0];
            }

        });
    }//createSpinner

    public void clickSaveDataMemuFarm2(View view) {


        tiltleString = tiltleEditText.getText().toString().trim();
        textString = textEditText.getText().toString().trim();

        //Get Valuse From
        userLoginStrings = getIntent().getStringArrayExtra("Login");
        memberString = userLoginStrings[0];

        //ค่าวันที่ จำนวนเหลือวัน Post data End
        Log.d("27novV2", "dayAnInt==>" + dayAnTnt);
        String[] strings = datatimeString.split("-");
        for (int i = 0; i < strings.length; i++) {
            Log.d("27novV2", "strings(" + ") ==>" + strings[i]);
        }
        int intEnd = dayAnTnt + Integer.parseInt(strings[2]);
        endTimeString = strings[0] + "-" + strings[1] + "-" + Integer.toString(intEnd);
        Log.d("27novV2", "endTimeString ==>" + endTimeString);


        if (checkSpace()) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกทุกช่องครับ"); //เมื่อมีช่องว่างให้แสดง ข้อความ);

        } else if (aBoolean || a2Boolean) {
            // Non Choose Image
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ยังไม่ได้เลือกรูป", "กรุณาเลือกรูปภาพสักรูป");

        } else {
            confirmData();
        }

    }//clickSaveData

    private boolean checkSpace() {
        return tiltleString.equals("") ||
                textString.equals("");
    }//checkSpac

    private void confirmData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.dule_icon);
        builder.setTitle("ยืนยันการลงประกาศ");

        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadToServer();
                dialog.dismiss();
            }
        });
        builder.show();
    }//confirmData

    private class MyPostData extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;

        public MyPostData(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient  = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("post_tiltle", tiltleString)
                        .add("post_text", textString)
                        .add("mem_id", memberString)
                        .add("post_data_ster", datatimeString)
                        .add("post_data_end", endTimeString)
                        .add("post_pic", "http://swiftcodingthai.com/gam/Post" +
                                pathImage1String.substring(pathImage1String.lastIndexOf("/")))
                        .add("post_pic_two", "http://swiftcodingthai.com/gam/Post" +
                                pahtImage2String.substring(pahtImage2String.lastIndexOf("/")))
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlPHP).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("27novV1", "e doIn" + e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("27novV1", "Result ==>" + s);
            if (Boolean.parseBoolean(s)) {
                //Reslt OK
                Intent intent = new Intent(context, Menu_farmer_3.class);
                intent.putExtra("Login", userLoginStrings);
                startActivity(intent);
            } else {
                //Result Error
                Toast.makeText(context, "Cannot Update Data", Toast.LENGTH_SHORT).show();
            }

        }
    }//MypsotData


    private void uploadToServer() {


        String qrPathpathImage1String = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myAppDirs/"+"myImagesLocation"+pathImage1String;
        String qrPathpathpahtImage2String = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myAppDirs/"+"myImagesLocation"+pahtImage2String;


        Toast.makeText(this, ""+qrPathpathImage1String, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+qrPathpathImage1String, Toast.LENGTH_SHORT).show();
        try {

            //Upload Image
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "gam@swiftcodingthai.com",
                    "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Post");
            simpleFTP.stor(new File(qrPathpathImage1String));
            simpleFTP.stor(new File(qrPathpathpahtImage2String));
            simpleFTP.disconnect();

            Toast.makeText(Menu_farmer_2.this, "บันทึกรูปภาพ", Toast.LENGTH_SHORT).show();

            //Upload String
            MyPostData myPostData = new MyPostData(Menu_farmer_2.this);
            myPostData.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//uploadToServer
    private boolean storeImage(Bitmap imageData, String filename) {
        //get path to external storage (SD card)
        String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDirs/myImagesLocation/";
        File sdIconStorageDir = new File(iconsStoragePath);
        sdIconStorageDir.mkdirs();

        try {
            String filePath = sdIconStorageDir.toString() + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            imageData.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        }


        return true;
    }
}//Main Methodfarmer