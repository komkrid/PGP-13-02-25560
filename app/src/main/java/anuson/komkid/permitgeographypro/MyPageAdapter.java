package anuson.komkid.permitgeographypro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
//        if (views != null) {
//            return Integer.MAX_VALUE;
//        } else {
//            return 0;
//        }
        return 3;
    }

    public Fragment getItem(int position) {
        if (position == 0)
            return new Fragment_1();
        else if (position == 1)
            return new Fragment_2();
        else if (position == 2)
            return new Fragment_3();
        return null;
    }

}