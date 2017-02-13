package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] urlStrings = new String[]{
            "http://swiftcodingthai.com/gam/php_get_member_user.php",
            "http://swiftcodingthai.com/gam/php_get_member_farmer.php"};
    private RadioGroup radioGroup;
    private int index = 0;
    private String[] columnUserStrings = new String[]{
            "mem_u_id",
            "mem_u_user",
            "mem_u_pass",
            "mem_u_name",
            "mem_u_add",
            "mem_u_mail",
            "mem_u_tel",
            "mem_u_key",
            "mem_u_pic"};
    private  String[] columnfarmerStrings = new String[]{
            "mem_id",
            "mem_user",
            "mem_pass",
            "mem_name",
            "mem_add",
            "mem_tel",
            "mem_key",
            "mem_farm_name",
            "mem_farm_type",
            "mem_farm_area",
            "mem_farm_pic",
            "mem_farm_latitude",
            "mem_farm_longtitude",
            "mem_farm_add",
            "mem_pictures"};
    private String[] loginStrings;

    // dookdik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
        radioGroup = (RadioGroup) findViewById(R.id.ragUser);

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton:  // for User
                        index = 0;
                        break;
                    case R.id.radioButton2: // for Farmer
                        index = 1;
                        break;
                }
            }
        });

        //textview สมัครสมาชิก
        final TextView tvw4 = (TextView) findViewById(R.id.textView4);
        final TextView tw5 = (TextView) findViewById(R.id.textView44);


        tvw4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        Intent newActivity = new Intent(MainActivity.this, Register_user.class);
                                                  startActivity(newActivity);
                                              }
                                          }
        );
        tw5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                                        Intent newActivity = new Intent(MainActivity.this, Main_location.class);
                                        startActivity(newActivity);
                                    }
                                }
        );
    }//main method

    public void clickMylogin(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "คำเตือน"
                    , "กรุงณากรอกให้ครบ");
        } else {

            Log.d("2octV2", "index ==> " + index);
            SynMember synMember = new SynMember(this);
            synMember.execute();

        }
    }//clickMylogin

    private class SynMember extends AsyncTask<Void, Void, String> {

        private Context context;
        private boolean aBoolean = true;

        public SynMember(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlStrings[index]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("2octV2", "e doInBack ==> " + e.toString());
                return null;
            }

        }//doInBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("2octV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                //Explicit for Array
                switch (index) {
                    case 0:
                        loginStrings = new String[columnUserStrings.length];
                        break;
                    case 1:
                        loginStrings = new String[columnfarmerStrings.length];
                        break;
                }

                for (int i=0;i<jsonArray.length();i+=1) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    switch (index) {

                        case 0: // for User

                            if (userString.equals(jsonObject.getString(columnUserStrings[1]))) {

                                aBoolean = false;
                                for (int j=0;j<columnUserStrings.length;j+=1) {
                                    loginStrings[j] = jsonObject.getString(columnUserStrings[j]);
                                    Log.d("2octV3", "logStrings(" + j + ") = " + loginStrings[j]);
                                } //for

                            }   // if

                            break;
                        case 1: // for Farmer

                            if (userString.equals(jsonObject.getString(columnfarmerStrings[1]))) {
                                aBoolean = false;
                                for (int i1=0;i1<columnfarmerStrings.length;i1+=1) {
                                    loginStrings[i1] = jsonObject.getString(columnfarmerStrings[i1]);
                                    Log.d("2octV3", "logStrings(" + i1 + ") = " + loginStrings[i1]);
                                }//for
                            }//if

                            break;

                    }   //switch
                }   // for
                if (aBoolean) {
                    //User false of Index False
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "User of Status False",
                            "User หรือ สถานะผิด");
                } else if (!passwordString.equals(loginStrings[2])) {
                    //password false
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context,"Password False",
                            "Please Try Again Password False");
                } else {
                    //password Trie
                    switch (index) {
                        case 0:  // for User
                            Intent newActivity = new Intent(MainActivity.this, Menu_user.class);

                            //ส่งค่าไป ใน Login
                            newActivity.putExtra("Login", loginStrings);

                            startActivity(newActivity);
                            Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                            break;
                        case 1: // for Farmeradmin
                            Intent newActivity2 = new Intent(MainActivity.this, Menu_farmer.class);

                            //ส่งค่าไป ใน Login
                            newActivity2.putExtra("Login", loginStrings);
                            startActivity(newActivity2);
                            Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

             } catch (Exception e) {
                Log.d("2octV2", "e onPost ==> " + e.toString());
            }

        }   // onPost

    }   // SynMember

}   // Main Class