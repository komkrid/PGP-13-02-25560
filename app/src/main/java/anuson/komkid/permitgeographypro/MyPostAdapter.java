package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyPostAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] titleStrings, timeStrings, statusStrings;
    private TextView titleTextView, timeTextView, statusTextView;

    public MyPostAdapter(Context context,
                         String[] titleStrings,
                         String[] timeStrings,
                         String[] statusStrings) {
        this.context = context;
        this.titleStrings = titleStrings;
        this.timeStrings = timeStrings;
        this.statusStrings = statusStrings;
    }

    @Override
    public int getCount() {
        return titleStrings.length;
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
        View view1 = layoutInflater.inflate(R.layout.my_post_listview, viewGroup, false);

        //Bind Widget
        titleTextView = (TextView) view1.findViewById(R.id.textView7);
        timeTextView = (TextView) view1.findViewById(R.id.textView17);
        statusTextView = (TextView) view1.findViewById(R.id.textView18);

        //Show Text
        titleTextView.setText(titleStrings[i]);
        timeTextView.setText(timeStrings[i]);
        statusTextView.setText(statusStrings[i]);

        return view1;
    }
}   // Main Class