package anuson.komkid.permitgeographypro;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Syn_new_post extends AsyncTask<Void,Void,String>{
    private static final String urlJSON = "http://swiftcodingthai.com/gam/get_new_post.php";
    private Context context;

    public Syn_new_post(Context context){this.context = context;}

    @Override
    protected String doInBackground(Void... params) {
        try{

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
