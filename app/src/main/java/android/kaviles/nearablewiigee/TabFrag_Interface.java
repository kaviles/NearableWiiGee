package android.kaviles.nearablewiigee;

import com.estimote.sdk.Nearable;

import java.util.List;

/**
 * Created by Kelvin on 10/25/16.
 */
public interface TabFrag_Interface {

    void handleNearables(List<Nearable> nearables);
    void setTabFragData(TabFrag_Data data);
    boolean tabLocked();
//    TabFrag_Data getTabData();

}
