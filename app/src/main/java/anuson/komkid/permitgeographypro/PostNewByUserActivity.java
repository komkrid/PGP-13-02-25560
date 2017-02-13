package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PostNewByUserActivity extends BaseAdapter {

    private final Context context;
    private String[] post_tiltleStrings,mem_idStrings,date_showStrings;
    private TextView post_liltleTextView,mem_idTextView,date_showTextView;

    public PostNewByUserActivity(Context context,
                                 String[] post_tiltleStrings,
                                 String[] mem_idStrings,
                                 String[] date_showStrings){
        this.context = context;
        this.post_tiltleStrings = post_tiltleStrings;
        this.mem_idStrings = mem_idStrings;
        this.date_showStrings = date_showStrings;
    }


    @Override
    public int getCount() {
        return post_tiltleStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.post_new_by_user, viewGroup,false);

        post_liltleTextView = (TextView) view1.findViewById(R.id.textView94);
        mem_idTextView = (TextView) view1.findViewById(R.id.textView93);
        date_showTextView = (TextView) view1.findViewById(R.id.textView101);

        post_liltleTextView.setText(post_tiltleStrings[i]);
        mem_idTextView.setText(mem_idStrings[i]);
        date_showTextView.setText(date_showStrings[i]);

        return view1;
    }
}
