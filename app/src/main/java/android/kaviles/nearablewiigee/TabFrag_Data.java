package android.kaviles.nearablewiigee;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kelvin on 10/26/16.
 */
public class TabFrag_Data {

    LinkedHashMap<String, NearableDevice> nearableLHM;

    public TabFrag_Data() {
        nearableLHM = new LinkedHashMap<>();
    }

    public void addNearable(String nearableID) {
        if (!nearableLHM.containsKey(nearableID)) {
            NearableDevice nd = new NearableDevice(nearableID);
            nd.setTrainButton(1);
            nd.setCloseGestureButton(2);
            nd.setRecognitionButton(3);

            nearableLHM.put(nearableID, nd);
        }
    }

    public NearableDevice getNearableDevice(String nearableID) {
        if (nearableLHM.containsKey(nearableID)) {
            return nearableLHM.get(nearableID);
        }
        else {
            return null;
        }
    }

    public boolean isSelected(String nearableID) {
        if (nearableLHM.containsKey(nearableID) && nearableLHM.get(nearableID).getListen()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setSelected(String nearableID, boolean value) {
        if (nearableLHM.containsKey(nearableID)) {
            nearableLHM.get(nearableID).setListen(value);
        }
    }

    public LinkedList<String> getSelected() {
        LinkedList<String> ll = new LinkedList<>();

        Set set = nearableLHM.entrySet();
        Iterator iter = set.iterator();
        while(iter.hasNext()) {

            Map.Entry me = (Map.Entry)iter.next();
            if (((NearableDevice) me.getValue()).getListen()) {
                ll.add(((NearableDevice) me.getValue()).getID());
            }
        }

        return ll;
    }

    public void reset() {
        nearableLHM.clear();
    }
}
