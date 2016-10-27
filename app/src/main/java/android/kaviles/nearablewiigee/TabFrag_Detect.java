package android.kaviles.nearablewiigee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.estimote.sdk.Nearable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kelvin on 10/24/16.
 */
public class TabFrag_Detect extends Fragment implements TabFrag_Interface, View.OnClickListener{
    private final static String TAG = TabFrag_Detect.class.getSimpleName();

    private boolean tabLock;

    private TabFrag_Data tfd;
    private LinkedList<String> listenList;
    private int detectState;

    private Button btn_detect;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tabLock = false;

        View view = inflater.inflate(R.layout.tabfrag_detect, container, false);

        btn_detect = (Button) view.findViewById(R.id.btn_detect);
        if (btn_detect != null) {
            btn_detect.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void handleNearables(List<Nearable> nearables) {

        if (detectState == 1) {
            for (Nearable nearable : nearables) {
                if (tfd.isSelected(nearable.identifier)) {

                    double x = nearable.xAcceleration;
                    double y = nearable.yAcceleration;
                    double z = nearable.zAcceleration;

                    tfd.getNearableDevice(nearable.identifier).updateDeviceData(x, y, z);
                }
            }
        }
    }

    @Override
    public void setTabFragData(TabFrag_Data data) {
        tfd = data;
    }

    @Override
    public boolean tabLocked() {
        return tabLock;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        listenList = tfd.getSelected();

        switch(id) {
            case R.id.btn_detect:

                if (detectState == 0) {
                    detectState = 1;
                    tabLock = true;
                    btn_detect.setText("Stop Detecting");

                    for (String s : listenList) {
                        tfd.getNearableDevice(s).fireButtonPressedEvent(3);
                    }
                }
                else {
                    detectState = 0;
                    tabLock = false;
                    btn_detect.setText("Start Detecting");

                    for (String s : listenList) {
                        tfd.getNearableDevice(s).fireButtonReleasedEvent(3);
                    }
                }


        }
    }
}
