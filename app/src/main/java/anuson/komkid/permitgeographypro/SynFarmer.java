package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;



public class SynFarmer extends AsyncTask<Void, Void , String>{
    private  static  final  String urlJSON = "http://swiftcodingthai.com/gam/php_get_member_farmer.php";
    private Context context;

    public SynFarmer(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(Void... params) {

        try{
            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).build();
            Response response = okHttpClient.newCall(request).execute();

            return  response.body().string();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}//Main Class