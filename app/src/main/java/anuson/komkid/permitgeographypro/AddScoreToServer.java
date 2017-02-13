package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


public class AddScoreToServer extends AsyncTask<Void,Void,String>{
    private Context context;
    private static final String urlPHP="http://swiftcodingthai.com/gam/php_add_score.php";
    private String score_date,post_id,mem_u_id;

    public AddScoreToServer(Context context, String score_date, String post_id, String mem_u_id) {
        this.context = context;
        this.score_date = score_date;
        this.post_id = post_id;
        this.mem_u_id = mem_u_id;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd","true")
                    .add("score_date",score_date)
                    .add("post_id",post_id)
                    .add("mem_u_id",mem_u_id)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();



        }catch (Exception e){
            Log.d("2febV4","e doin" + e.toString());

        }
        return null;
    }
}
