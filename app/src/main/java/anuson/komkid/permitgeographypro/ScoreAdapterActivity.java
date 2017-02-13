package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ScoreAdapterActivity extends BaseAdapter {

    private Context context;
    private TextView soreTextView, mem_farm_nameTextView;
    private String[] score_Strings,mem_farm_nameStrings,mem_picturesStrings;
    private ImageView imageView;

    public ScoreAdapterActivity(Context context,
                         String[] score_Strings,
                         String[] mem_farm_nameString,
                         String[] mem_picturesStrings) {
        this.context = context;
        this.score_Strings = score_Strings;
        this.mem_farm_nameStrings = mem_farm_nameString;
        this.mem_picturesStrings = mem_picturesStrings;
    }
    @Override
    public int getCount() { return mem_farm_nameStrings.length; }

    @Override
    public Object getItem(int i) {return null;}

    @Override
    public long getItemId(int i) {return 0;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.score_adapter, viewGroup,false);


        mem_farm_nameTextView = (TextView) view1.findViewById(R.id.textView110);
        soreTextView = (TextView) view1.findViewById(R.id.textView111);
        imageView = (ImageView) view1.findViewById(R.id.imageView112);

        soreTextView.setText(score_Strings[i]);
        mem_farm_nameTextView.setText(mem_farm_nameStrings[i]);
        Picasso.with(context).load(mem_picturesStrings[i]).into(imageView);

        return view1;
    }
}
