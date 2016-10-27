package android.kaviles.nearablewiigee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
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
public class TabFrag_Train extends Fragment implements TabFrag_Interface, View.OnClickListener {
    private final static String TAG = TabFrag_Train.class.getSimpleName();

    private boolean tabLock;

    private TabFrag_Data tfd;
    private LinkedList<String> listenList;
    private int recordState;
    private int trainState;

    private Button btn_record;
    private Button btn_train;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tabLock = false;

        recordState = 0;
        trainState = 0;

        View view = inflater.inflate(R.layout.tabfrag_train, container, false);

        btn_record = (Button) view.findViewById(R.id.btn_record);
        btn_train = (Button) view.findViewById(R.id.btn_train);
        if (btn_record != null && btn_train != null) {
            btn_record.setOnClickListener(this);
            btn_train.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void handleNearables(List<Nearable> nearables) {

        if (recordState == 1) {
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
            case R.id.btn_record:

                if (recordState == 0) {
                    recordState = 1;
                    trainState = 0;
                    tabLock = true;

                    btn_record.setText("Stop Recording");
                    btn_train.setClickable(false);

                    for (String s : listenList) {
                        tfd.getNearableDevice(s).fireButtonPressedEvent(1);
                    }
                }
                else {
                    recordState = 0;
                    trainState = 1;
                    tabLock = false;

                    btn_record.setText("Start Recording");
                    btn_train.setClickable(true);

                    for (String s : listenList) {
                        tfd.getNearableDevice(s).fireButtonReleasedEvent(1);
                    }
                }

                break;
            case R.id.btn_train:

                if (trainState == 1) {

                    for (String s : listenList) {
                        tfd.getNearableDevice(s).fireButtonPressedEvent(2);
                    }
                }

                break;
            default:
                break;
        }

    }
}
