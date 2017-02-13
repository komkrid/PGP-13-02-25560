package anuson.komkid.permitgeographypro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by komkrid on 3/9/2559.
 */
public class MyAlert {
    public  void myDialog(Context context,
                          String strTitle,
                          String strMessage) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.dule_icon);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
