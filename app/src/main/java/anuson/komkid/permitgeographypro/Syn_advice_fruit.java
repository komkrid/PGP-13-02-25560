package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Syn_advice_fruit extends AsyncTask<Void,Void,String> {

    private Context context;
    private static final String urlJSON = "http://swiftcodingthai.com/gam/php_get_advice.php";

    public Syn_advice_fruit(Context context) {
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
            Log.d("27novV3", "e doIn ==> " + e.toString());
            return null;
        }
    }
}