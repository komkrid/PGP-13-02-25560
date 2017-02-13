package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by masterUNG on 2/2/2017 AD.
 */

public class SynChronizeComment extends AsyncTask<Void, Void, String>{

    //Explicit
    private Context context;
    private static final String urlJSON = "http://swiftcodingthai.com/gam/get_comment_DESC.php";

    public SynChronizeComment(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("2febV2", "e doIn ==> " + e.toString());
            return null;
        }


    }
}   // SynChronizeComment
