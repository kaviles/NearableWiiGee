package android.kaviles.nearablewiigee;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kelvin on 10/24/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private int mNumOfTabs;
    private TabFrag_SelectNearable tf1;
    private TabFrag_Train tf2;
    private TabFrag_Detect tf3;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);

        this.mNumOfTabs = NumOfTabs;

        tf1 = new TabFrag_SelectNearable();
        tf2 = new TabFrag_Train();
        tf3 = new TabFrag_Detect();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return tf1;
            case 1:
                return tf2;
            case 2:
                return tf3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
