package anuson.komkid.permitgeographypro;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class Menu_farmer extends TabActivity{

        private String[] userLoginStrings;


        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_farmer);

            TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

            TabHost.TabSpec tab1 = tabHost.newTabSpec("ABA UM");
            TabHost.TabSpec tab2 = tabHost.newTabSpec("ABA DOIS");
            TabHost.TabSpec tab3 = tabHost.newTabSpec("ABA TRES");
            TabHost.TabSpec tab4 = tabHost.newTabSpec("ABA AU");


            //Get Valuse From
                    userLoginStrings = getIntent().getStringArrayExtra("Login");
            //Check userLogin
                    for (int i=0;i<userLoginStrings.length;i++){
                        Log.d("4novV1","userLogin"+ i +"]="+ userLoginStrings[i]);
            }//for

            //tab1.setIndicator("หน้าแรก");
            tab1.setIndicator("",getResources().getDrawable(R.drawable.farmer));
            Intent intent = new Intent(Menu_farmer.this,Menu_farmer_1.class);
            intent.putExtra("Login",userLoginStrings);
            tab1.setContent(intent);

            //tab2.setIndicator("ลงประกาศ");
            tab2.setIndicator("",getResources().getDrawable(R.drawable.megaphone));
            Intent intent1 = new Intent(Menu_farmer.this,Menu_farmer_2.class);
            intent1.putExtra("Login",userLoginStrings);
            tab2.setContent(intent1);

            //tab3.setIndicator("รายการประกาศ");
            tab3.setIndicator("",getResources().getDrawable(R.drawable.clipboard));
            Intent intent2 = new Intent(Menu_farmer.this,Menu_farmer_3.class);
            intent2.putExtra("Login",userLoginStrings);
            tab3.setContent(intent2);

            //tab4.setIndicator("แชค");
            tab4.setIndicator("",getResources().getDrawable(R.drawable.chat));
            Intent intent3 = new Intent(Menu_farmer.this,Main_Comment.class);
            intent3.putExtra("Login",userLoginStrings);
            tab4.setContent(intent3);

            tabHost.addTab(tab1);
            tabHost.addTab(tab2);
            tabHost.addTab(tab3);
            tabHost.addTab(tab4);



        }
}
