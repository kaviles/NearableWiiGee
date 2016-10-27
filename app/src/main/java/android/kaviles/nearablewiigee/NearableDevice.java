package android.kaviles.nearablewiigee;

import android.content.Context;

import org.wiigee.device.Device;

/**
 * Created by Kelvin on 10/26/16.
 */
public class NearableDevice extends Device {

    private String nid;
    private boolean listen;

    public NearableDevice(String nearableID) {
        super(true);

        nid = nearableID;
        listen = false;
    }

    public String getID() {
        return nid;
    }

    public void setListen(boolean value) {
        listen = value;
    }

    public boolean getListen() {
        return listen;
    }

    public void updateDeviceData(double x, double y, double z) {

        if (listen) {
            fireAccelerationEvent(new double[] {x, y, z});
        }
    }
}
