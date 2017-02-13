package anuson.komkid.permitgeographypro;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class Menu_user extends TabActivity {

    private String[] userLoginStrings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_user);

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("ABA UM");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("ABA DOIS");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("ABA TRES");
        TabHost.TabSpec tab4 = tabHost.newTabSpec("ABA MAPS");

        //Get Valuse From
        userLoginStrings = getIntent().getStringArrayExtra("Login");

        //Check userLogin
        for (int i=0;i<userLoginStrings.length;i++){
            Log.d("4novV1","userLogin"+ i +"]="+ userLoginStrings[i]);
        }//for


        tab1.setIndicator("หน้าแรก");
        Intent intent= new Intent(Menu_user.this,Menu_user_1.class);
        intent.putExtra("Login",userLoginStrings);
        tab1.setContent(intent);

        tab2.setIndicator("ค้นหาสวน");
        Intent intent1 = new Intent(Menu_user.this, MapsActivity.class);
        intent1.putExtra("Login", userLoginStrings);
        tab2.setContent(intent1);


        tab3.setIndicator("การจอง");
        Intent intent2 = new Intent(Menu_user.this, Menu_user_2.class);
        intent2.putExtra("Login", userLoginStrings);
        tab3.setContent(intent2);

        tab4.setIndicator("ยอดนิยม");
        //tab3.setIndicator("",getResources().getDrawable(R.mipmap.ic_launcher));
        tab4.setContent(new Intent(this, Menu_user_3.class));


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);


    }
}
