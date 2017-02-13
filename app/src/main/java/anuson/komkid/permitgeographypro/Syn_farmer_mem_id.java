package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Syn_farmer_mem_id extends AsyncTask<Void,Void,String>{
    private Context context;
    private static final String urlJSON = "http://swiftcodingthai.com/gam/get_farmer_where_mem_id.php";
    private String men_idString;

    public Syn_farmer_mem_id(Context context,
                           String men_idString) {
        this.context = context;
        this.men_idString = men_idString;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("post_id", men_idString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("31JanV1", "e doIn ==> " + e.toString());
            return null;
        }
    }
}
