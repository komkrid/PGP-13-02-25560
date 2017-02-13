package anuson.komkid.permitgeographypro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Menu_user_1 extends FragmentActivity {
//สไลท์ภาพ
    MyPageAdapter adapter;
    ViewPager pager;

    private ImageView imageView;
    private String[] userLoginStrings;
    private TextView userTextView,nameTextView,addTextView,emailTextView,keyTextView,telTextView;

    private String[] mem_far_String;
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




        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_user_1);

//สไลท์ภาพ
            adapter = new MyPageAdapter(getSupportFragmentManager());
            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);


            //ฺฺBind Widget
            userTextView = (TextView) findViewById(R.id.textView8);
            nameTextView = (TextView) findViewById(R.id.textView9);
            addTextView = (TextView) findViewById(R.id.textView38);
            emailTextView = (TextView) findViewById(R.id.textView39);
            keyTextView = (TextView) findViewById(R.id.textView40);
            telTextView = (TextView) findViewById(R.id.textView10);

            imageView = (ImageView) findViewById(R.id.imageView1);

            //Get Valuse From
            userLoginStrings = getIntent().getStringArrayExtra("Login");

            //Check userLogin
            for (int i = 0; i < userLoginStrings.length; i++) {
                Log.d("4novV2", "userLogin" + i + "]=" + userLoginStrings[i]);
            }//for
            //Show Text
            userTextView.setText("" + userLoginStrings[1]);
            nameTextView.setText("" + userLoginStrings[3]);
            addTextView.setText("" + userLoginStrings[4]);
            emailTextView.setText("" + userLoginStrings[5]);
            keyTextView.setText("" + userLoginStrings[7]);
            telTextView.setText(""+ userLoginStrings[6]);

            Picasso.with(Menu_user_1.this).load(userLoginStrings[8]).into(imageView);


        try{
            Syn_new_post syn_new_post = new Syn_new_post(this);
            syn_new_post.execute();
            String s = syn_new_post.get();
            Log.d("30JanV2","JSON Syn_Score ==>" + s);

            JSONArray jsonArray = new JSONArray(s);
            final String[] post_idStrings = new String[jsonArray.length()];
            final String[] mem_idStrings = new String[jsonArray.length()];
            final String[] post_tiltleStrings = new String[jsonArray.length()];
            final String[] post_textStrings = new String[jsonArray.length()];
            final String[] post_data_sterStrings = new String[jsonArray.length()];
            final String[] post_data_endStrings = new String[jsonArray.length()];
            final String[] post_viewStrings = new String[jsonArray.length()];
            final String[] post_picStrings = new String[jsonArray.length()];
            final String[] post_pic_twoStrings = new String[jsonArray.length()];
            final String[] status_reserv_idStrings = new String[jsonArray.length()];
            final String[] mem_farm_nameStrings = new String[jsonArray.length()];

            final String[] date_Show_star_Strings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                post_idStrings[i] = jsonObject.getString("post_id");
                mem_idStrings[i] = jsonObject.getString("mem_id");
                post_tiltleStrings[i] = jsonObject.getString("post_tiltle");
                post_textStrings[i] = jsonObject.getString("post_text");
                post_data_sterStrings[i] = jsonObject.getString("post_data_ster");
                post_data_endStrings[i] = jsonObject.getString("post_data_end");
                post_viewStrings[i] = jsonObject.getString("post_view");
                post_picStrings[i] = jsonObject.getString("post_pic");
                post_pic_twoStrings[i] = jsonObject.getString("post_pic_two");
                status_reserv_idStrings[i] = jsonObject.getString("status_reserv_id");
                mem_farm_nameStrings[i] = jsonObject.getString("mem_farm_name");

                date_Show_star_Strings[i] = dateThai(post_data_sterStrings[i]);


                ListView listView = (ListView) findViewById(R.id.lisv_post_new_byuser);

                PostNewByUserActivity postNewByUserActivity = new PostNewByUserActivity(Menu_user_1.this,
                        post_tiltleStrings,mem_farm_nameStrings,date_Show_star_Strings);
                listView.setAdapter(postNewByUserActivity);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(Menu_user_1.this, ListPostByUser.class);

                        intent.putExtra("mem_id",mem_idStrings[i]);
                        intent.putExtra("Login", userLoginStrings);
                        startActivity(intent);

                    }
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static String dateThai(String strDate){
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int year = 0, month = 0, day = 0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.format("%s %s %s", day, Months[month], year + 543);
    }

}
