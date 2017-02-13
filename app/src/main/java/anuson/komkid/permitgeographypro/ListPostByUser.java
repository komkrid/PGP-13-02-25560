package anuson.komkid.permitgeographypro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ListPostByUser extends AppCompatActivity {

    //Explicit
    private String mem_idString;
    private String[] loginStrings;
    private String[] mem_far_String;
    private String[] columnfarmerStrings = new String[]{
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

    private TextView farm_nameTextView, name_far_TextView, add_farmerTextView, garden_farmTextView, tel_farmTextView;
    private ImageView pic_farmTextView;

    private static final String urlPHP = "http://swiftcodingthai.com/gam/php_add_score.php";

    private Button button_out, button_comment;

    private String score_post_id, score_mem_id, score_mem_u_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post_by_user);

        //Setting
        mem_idString = getIntent().getStringExtra("mem_id");
        Log.d("21decV2", "mem_id ==> " + mem_idString);
        loginStrings = getIntent().getStringArrayExtra("Login");

        bin();

        button_out = (Button) findViewById(R.id.button_out_by_post);
        button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_comment = (Button) findViewById(R.id.button_comment);
        button_comment.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  Intent newActivity = new Intent(ListPostByUser.this, Main_Comment.class);
                                                  newActivity.putExtra("Login", loginStrings);
                                                  startActivity(newActivity);
                                              }
                                          }
        );


        //Cre Lis

        createLisView();

    }   // Main Method

    @Override
    protected void onResume() {
        super.onResume();

        createLisView();
    }

    private void createLisView() {
        try {
            SynFarmer synFarmer = new SynFarmer(ListPostByUser.this);
            synFarmer.execute();
            String a = synFarmer.get();
            Log.d("14JanV1", "JSoN ==> " + a);

            JSONArray jsonArray = new JSONArray(a);

            mem_far_String = new String[columnfarmerStrings.length];

            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (mem_idString.equals(jsonObject.getString(columnfarmerStrings[0]))) {
                    for (int j = 0; j < columnfarmerStrings.length; j += 1) {
                        mem_far_String[j] = jsonObject.getString(columnfarmerStrings[j]);
                        Log.d("14JenV3", "jsonObject : " + j + " : " + mem_far_String[j]);

                        farm_nameTextView.setText("" + mem_far_String[7]);
                        name_far_TextView.setText("" + mem_far_String[3]);
                        add_farmerTextView.setText("" + mem_far_String[13]);
                        garden_farmTextView.setText("" + mem_far_String[8]);
                        tel_farmTextView.setText("" + mem_far_String[5]);

                        Picasso.with(ListPostByUser.this).load(mem_far_String[14]).into(pic_farmTextView);

                    }
                }


            }//for


        } catch (Exception e) {
            Log.d("14JanV1", "e ==> " + e.toString());
        }

        try {

            SynPost synPost = new SynPost(ListPostByUser.this, mem_idString);
            synPost.execute();
            String s = synPost.get();
            Log.d("21decV2", "JSoN ==> " + s);

            JSONArray jsonArray = new JSONArray(s);

            final String[] titleStrings = new String[jsonArray.length()];
            final String[] endStrings = new String[jsonArray.length()];
            final String[] data_endStrings = new String[jsonArray.length()];
            final String[] statusStrings = new String[jsonArray.length()];
            final String[] statusShowStrings = new String[jsonArray.length()];
            final String[] textStrings = new String[jsonArray.length()];
            final String[] startStrings = new String[jsonArray.length()];
            final String[] pic1Strings = new String[jsonArray.length()];
            final String[] pic2Strings = new String[jsonArray.length()];
            final String[] idStrings = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                titleStrings[i] = jsonObject.getString("post_tiltle");
                endStrings[i] = jsonObject.getString("post_data_end");
                statusStrings[i] = jsonObject.getString("status_reserv_id");
                statusShowStrings[i] = showStatus(statusStrings[i]);
                textStrings[i] = jsonObject.getString("post_text");
                startStrings[i] = jsonObject.getString("post_data_ster");
                data_endStrings[i] = dateThai(startStrings[i]);
                pic1Strings[i] = jsonObject.getString("post_pic");
                pic2Strings[i] = jsonObject.getString("post_pic_two");
                idStrings[i] = jsonObject.getString("post_id");
                score_post_id = idStrings[i];

            }   // for

            ListView listView = (ListView) findViewById(R.id.livPostByUser);

            final MyPostAdapter myPostAdapter = new MyPostAdapter(ListPostByUser.this,
                    titleStrings, data_endStrings, statusShowStrings);
            listView.setAdapter(myPostAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String post_id = idStrings[i];
                    String mem_u_id = loginStrings[0];
                    Log.d("2febV4","poid_id ==>" + post_id);
                    Log.d("2febV4","mem_u_id ==>" + mem_u_id);

                    Calendar calendar = Calendar.getInstance();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String clickDate = dateFormat.format(calendar.getTime());
                    Log.d("2febV4","clickDate ==>" + clickDate);

                    addScore(clickDate,post_id,mem_u_id);

                    Intent intent = new Intent(ListPostByUser.this, ShowDetailByUser.class);
                    intent.putExtra("post_tiltle", titleStrings[i]);
                    intent.putExtra("post_data_end", endStrings[i]);
                    intent.putExtra("status_reserv_id", statusStrings[i]);
                    intent.putExtra("post_text", textStrings[i]);
                    intent.putExtra("post_data_ster", startStrings[i]);
                    intent.putExtra("post_pic", pic1Strings[i]);
                    intent.putExtra("post_pic_two", pic2Strings[i]);

                    intent.putExtra("Login", loginStrings);
                    intent.putExtra("idPost", idStrings[i]);
                    startActivity(intent);

                }
            });
        } catch (Exception e) {
            Log.d("21decV2", "e ==> " + e.toString());
        }

    }//createView

    private void addScore(String clickDate, String post_id, String mem_u_id) {
        try {
            AddScoreToServer addScoreToServer = new AddScoreToServer(ListPostByUser.this,
                    clickDate, post_id, mem_u_id);
            addScoreToServer.execute();
            Log.d("2febV4","Result Server ==>" + addScoreToServer.get());

        }catch (Exception e){
            Log.d("2febV4","e add score" + e.toString());
        }


    }


    private String showStatus(String statusString) {

        String[] strings = new String[]{"กำลังขาย", "จอง", "สิ้นสุด"};
        int i = Integer.parseInt(statusString);

        return strings[i];
    }

    private void bin() {
        pic_farmTextView = (ImageView) findViewById(R.id.imageViewbyPost);

        farm_nameTextView = (TextView) findViewById(R.id.textView65);
        name_far_TextView = (TextView) findViewById(R.id.textView70);
        add_farmerTextView = (TextView) findViewById(R.id.textView71);
        garden_farmTextView = (TextView) findViewById(R.id.textView73);
        tel_farmTextView = (TextView) findViewById(R.id.textView74);
    }

    public static String dateThai(String strDate) {
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

}   // Main Class
