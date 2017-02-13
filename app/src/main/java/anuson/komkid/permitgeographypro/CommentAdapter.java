package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by masterUNG on 2/2/2017 AD.
 */

public class CommentAdapter extends BaseAdapter{

    private Context context;
    private String[] namePostStrings, dateTimeStrings, commentStrings;
    private TextView namePostTextView, dateTimeTextView, commentTextView;

    public CommentAdapter(Context context,
                          String[] namePostStrings,
                          String[] dateTimeStrings,
                          String[] commentStrings) {
        this.context = context;
        this.namePostStrings = namePostStrings;
        this.dateTimeStrings = dateTimeStrings;
        this.commentStrings = commentStrings;
    }

    @Override
    public int getCount() {
        return namePostStrings.length;
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
        View view1 = layoutInflater.inflate(R.layout.my_comment_layout, viewGroup, false);

        //Bind Widget
        namePostTextView = (TextView) view1.findViewById(R.id.textView97);
        dateTimeTextView = (TextView) view1.findViewById(R.id.textView98);
        commentTextView = (TextView) view1.findViewById(R.id.textView99);

        //Show View
        namePostTextView.setText(namePostStrings[i]);
        dateTimeTextView.setText(dateTimeStrings[i]);
        commentTextView.setText(commentStrings[i]);

        return view1;
    }
}   // Main Class
