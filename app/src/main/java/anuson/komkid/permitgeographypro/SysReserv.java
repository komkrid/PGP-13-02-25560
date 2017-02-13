package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class SysReserv extends AsyncTask<Void,Void,String>{

    private Context context;
    private static final String urlJSON = "http://swiftcodingthai.com/gam/get_reserv_where_id_join.php";
    private String men_idString;

    public SysReserv(Context context,
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
                    .add("mem_u_id", men_idString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("27novV3", "e doIn ==> " + e.toString());
            return null;
        }


    }
}
