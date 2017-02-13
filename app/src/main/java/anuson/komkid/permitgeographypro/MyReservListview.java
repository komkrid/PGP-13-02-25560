package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyReservListview extends BaseAdapter {

    private Context context;
    private String[] titleStrings, timeStrings, statusStrings;
    private TextView titleTextView, timeTextView, statusTextView;

    public MyReservListview(Context context,
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
        View view1 = layoutInflater.inflate(R.layout.activity_my_reserv_listview, viewGroup, false);

        titleTextView = (TextView) view1.findViewById(R.id.textView90);
        timeTextView = (TextView) view1.findViewById(R.id.textView89);
        statusTextView = (TextView) view1.findViewById(R.id.textView88);

        titleTextView.setText(titleStrings[i]);
        timeTextView.setText(timeStrings[i]);
        statusTextView.setText(statusStrings[i]);

        return view1;

    }
}
