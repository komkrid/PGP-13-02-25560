package anuson.komkid.permitgeographypro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;


public class Menu_user_3 extends Activity{

    private String[] loginStrings;

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_user_3);

        loginStrings = getIntent().getStringArrayExtra("Login");

            try{
                Syn_score syn_score = new Syn_score(this);
                syn_score.execute();
                String s = syn_score.get();
                Log.d("30JanV2","JSON Syn_Score ==>" + s);

                JSONArray jsonArray = new JSONArray(s);
                final String[] scoreStrings = new String[jsonArray.length()];
                final String[] mem_idStrings = new String[jsonArray.length()];
                final String[] mem_farm_nameStrings = new String[jsonArray.length()];
                final String[] mem_picturesStrings = new String[jsonArray.length()];


                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    scoreStrings[i] = jsonObject.getString("score");
                    mem_idStrings[i] = jsonObject.getString("mem_id");
                    mem_farm_nameStrings[i] = jsonObject.getString("mem_farm_name");
                    mem_picturesStrings[i] = jsonObject.getString("mem_pictures");


                    ListView listView = (ListView) findViewById(R.id.liv_score);

                    ScoreAdapterActivity scoreAdapterActivity = new ScoreAdapterActivity(Menu_user_3.this,
                            scoreStrings, mem_farm_nameStrings, mem_picturesStrings);
                    listView.setAdapter(scoreAdapterActivity);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(Menu_user_3.this, ListPostByUser.class);

                            intent.putExtra("mem_id",mem_idStrings[i]);
                            intent.putExtra("Login", loginStrings);
                            startActivity(intent);

                        }
                    });


                }

            }catch (Exception e){
                e.printStackTrace();
            }

    }
}
