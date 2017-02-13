package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by masterUNG on 12/21/2016 AD.
 */

public class AddServer extends AsyncTask<Void, Void, String>{

    //Explicit
    private static final String urlPHP = "http://swiftcodingthai.com/gam/add_reserv.php";
    private Context context;
    private String post_idString, mem_u_idString;

    public AddServer(Context context,
                     String post_idString,
                     String mem_u_idString) {
        this.context = context;
        this.post_idString = post_idString;
        this.mem_u_idString = mem_u_idString;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("post_id", post_idString)
                    .add("mem_u_id", mem_u_idString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}   // Main Class
