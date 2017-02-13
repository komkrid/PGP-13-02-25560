package anuson.komkid.permitgeographypro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Menu_user_2 extends Activity {

    private String[] userLoginStrings, idReservStrings, mem_idStrings;
    private ListView listView;
    private String[] columnReservStrings = new String[]{
            "reserv_id",
            "post_id",
            "mem_u_id"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_user_2);

        listView = (ListView) findViewById(R.id.livReserv_by_user);

        userLoginStrings = getIntent().getStringArrayExtra("Login");
        for (int i = 0; i < userLoginStrings.length; i++) {
            Log.d("18JanV1", "userLogin" + i + "]=" + userLoginStrings[i]);
        }//for

        try {
            SysReserv sysReserv = new SysReserv(Menu_user_2.this, userLoginStrings[0]);
            sysReserv.execute();
            String stringreserv = sysReserv.get();
            Log.d("18JanV5", "JSON==> " + stringreserv);

            JSONArray jsonArray = new JSONArray(stringreserv);

            final String[] titleStrings = new String[jsonArray.length()];
            final String[] endStrings = new String[jsonArray.length()];
            final String[] data_endStrings = new String[jsonArray.length()];
            final String[] statusShowStrings = new String[jsonArray.length()];
            final String[] statusStrings = new String[jsonArray.length()];
            final String[] textStrings = new String[jsonArray.length()];
            final String[] startStrings = new String[jsonArray.length()];
            final String[] pic1Strings = new String[jsonArray.length()];
            final String[] pic2Strings = new String[jsonArray.length()];

            final String[] idStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                titleStrings[i] = jsonObject1.getString("post_tiltle");
                endStrings[i] = jsonObject1.getString("post_data_end");
                data_endStrings[i] = dateThai(endStrings[i]);
                statusStrings[i] = jsonObject1.getString("status_reserv_id");
                statusShowStrings[i] = showStatus(jsonObject1.getString("status_reserv_id"));
                textStrings[i] = jsonObject1.getString("post_text");
                startStrings[i] = jsonObject1.getString("post_data_ster");
                pic1Strings[i] = jsonObject1.getString("post_pic");
                pic2Strings[i] = jsonObject1.getString("post_pic_two");

                idStrings[i] = jsonObject1.getString("post_id");

            }//for
            MyReservListview myReservListview = new MyReservListview(Menu_user_2.this,
                    titleStrings, data_endStrings, statusShowStrings);
            listView.setAdapter(myReservListview);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(Menu_user_2.this, ShowDetailByUser.class);
                    intent.putExtra("post_tiltle", titleStrings[i]);
                    intent.putExtra("post_data_end", endStrings[i]);
                    intent.putExtra("status_reserv_id", statusStrings[i]);
                    intent.putExtra("post_text", textStrings[i]);
                    intent.putExtra("post_data_ster", startStrings[i]);
                    intent.putExtra("post_pic", pic1Strings[i]);
                    intent.putExtra("post_pic_two", pic2Strings[i]);

                    intent.putExtra("Login",userLoginStrings);
                    intent.putExtra("idPost",idStrings[i]);

                    startActivity(intent);
                }
            });



        } catch (Exception e) {
            Log.d("27novV3", "e menu3 ==> " + e.toString());
        }

    }//onCreate

    private String showStatus(String statusString) {
        String[] strings = new String[]{"กำลังขาย", "จอง", "สิ้นสุด"};
        return strings[Integer.parseInt(statusString)];
    }
    public static String dateThai(String strDate){
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0;
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
        return String.format("%s %s %s", day,Months[month],year+543);
    }
}
