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

public class EditStatus extends AsyncTask<Void, Void, String>{

    private static final String urlPHP = "http://swiftcodingthai.com/gam/edit_status.php";
    private Context context;
    private String idString, statusString;

    public EditStatus(Context context, String idString, String statusString) {
        this.context = context;
        this.idString = idString;
        this.statusString = statusString;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("post_id", idString)
                    .add("status_reserv_id", statusString)
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
