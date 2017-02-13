package anuson.komkid.permitgeographypro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Main_location extends Activity {

    private TextView latitudeView, longitudeView;
    private Button getLocationBtn;
    GetCurrentLocation currentLoc;
    private String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_location);

        currentLoc = new GetCurrentLocation(this);
        initControls();

    }

    private void initControls() {

        latitudeView = (TextView) findViewById(R.id.lat);
        longitudeView = (TextView) findViewById(R.id.lng);
        getLocationBtn = (Button) findViewById(R.id.getLocation);

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                latitude = currentLoc.latitude;
                longitude = currentLoc.longitude;

                if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
                    latitude = "0.00";
                    longitude = "0.00";
                }

                latitudeView.setText("ละติจูด  : " + latitude);
                longitudeView.setText("ลองจิจูด  : " + longitude);

                    if(checkSpace()){
                        MyAlert myAlert = new MyAlert();
                        myAlert.myDialog(Main_location.this, "รอสักครู่", "กรุณารอสักครู่ให้ระบบ แสดงตำแหน่ง สวน ของท่าน"); //เมื่อมีช่องว่างให้แสดง ข้อความ

                    }else {
                        Intent intent = new Intent(getApplication(), Register_farmer.class);
                        intent.putExtra("lat", latitude);
                        intent.putExtra("long", longitude);
                        startActivity(intent);
                        finish();
                    }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentLoc.connectGoogleApi();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentLoc.disConnectGoogleApi();
    }

    private boolean checkSpace() {
        return latitude.equals("0.00") ||
                longitude.equals("0.00"); //เมื่อมีช่องว่าง
    }//checkSpace
}
