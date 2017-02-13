package anuson.komkid.permitgeographypro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main_Comment extends AppCompatActivity {

    //Explicit
    private ListView listView;
    private EditText editText;
    private Button button;
    private String commentString;
    private String[] loginStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__comment);

        //Bind Widget
        listView = (ListView) findViewById(R.id.livComment);
        editText = (EditText) findViewById(R.id.editText25);
        button = (Button) findViewById(R.id.buttonSent);

        //Button Controller
        buttonController();

        //Create ListView
        createListView();


    }   // Main Method

    private void createListView() {

        try {

            SynChronizeComment synChronizeComment = new SynChronizeComment(Main_Comment.this);
            synChronizeComment.execute();
            String strJSON = synChronizeComment.get();
            Log.d("2febV2", "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            String[] namePostStrings = new String[jsonArray.length()];
            String[] dateTimeStrings = new String[jsonArray.length()];
            String[] commentStrings = new String[jsonArray.length()];
            String[] date_show_ThaiStrings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                namePostStrings[i] = jsonObject.getString("NamePost");
                dateTimeStrings[i] = jsonObject.getString("DateTime");
                commentStrings[i] = jsonObject.getString("Comment");
                date_show_ThaiStrings[i] = dateThai(dateTimeStrings[i]);

            }   //for

            CommentAdapter commentAdapter = new CommentAdapter(Main_Comment.this,
                    namePostStrings, date_show_ThaiStrings, commentStrings);
            listView.setAdapter(commentAdapter);

        } catch (Exception e) {
            Log.d("2febV2", "e createListView ==> " + e.toString());
        }

    }   // createListView

    private void buttonController() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentString = editText.getText().toString().trim();

                if (commentString.equals("")) {
                    //Have Space
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(Main_Comment.this, "Have Space",
                            "Please Fill all Every Blank");
                } else {
                    //No Space
                    upDateCommentToServer();
                }
            }
        });

    }   // buttonController

    private void upDateCommentToServer() {

        try {

            //Get Current Date & Time
            Calendar calendar = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDateTime = dateFormat.format(calendar.getTime());
            Log.d("2febV1", "strDateTime ==> " + strDateTime);

            //Get Name Post
            loginStrings = getIntent().getStringArrayExtra("Login");
            String strNamePost = loginStrings[3];
            Log.d("2febV1", "NamePost ==> " + strNamePost);

            //Update to Server
            AddNewComment addNewComment = new AddNewComment(Main_Comment.this,
                    strNamePost, strDateTime, commentString);
            addNewComment.execute();
            boolean b = Boolean.parseBoolean(addNewComment.get());
            if (b) {
                //Success Post Comment
                Log.d("2febV1", "Success Post Comment");
                createListView();
                editText.setText("");

            } else {
                //Cannot Post Comment
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(Main_Comment.this, "Post Comment False",
                        "Please Try Again Post Comment False");
            }


        } catch (Exception e) {
            Log.d("2febV1", "e upDate ==> " + e.toString());
        }

    }   // upDate

    public  static String dateThai(String strDate){
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);
            hour = c.get(Calendar.HOUR);
            minute = c.get(Calendar.MINUTE);
            second = c.get(Calendar.SECOND);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.format("%s/%s/%s %s:%s:%s", day, Months[month], year + 543 - 2500, hour, minute, second);
    }

}   // Main Class
