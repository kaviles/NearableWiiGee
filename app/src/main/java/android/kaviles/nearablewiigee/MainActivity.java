package android.kaviles.nearablewiigee;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Nearable;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private TabFrag_Data tfd;

    private PagerAdapter adapter;
    private ViewPager mViewPager;

    private BeaconManager beaconManager;
    private String scanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EstimoteSDK.initialize(this, "kelvin-s-app-7i2", "3c1c62fa88e57ad3e1a4b8a392a08a7e");

        tfd = new TabFrag_Data();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Nearables"));
        tabLayout.addTab(tabLayout.newTab().setText("Train"));
        tabLayout.addTab(tabLayout.newTab().setText("Detect"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        for (int i = 0; i < adapter.getCount(); i++) {
            TabFrag_Interface tfi = (TabFrag_Interface) adapter.getItem(i);
            tfi.setTabFragData(tfd);
        }

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                TabFrag_Interface tfi = (TabFrag_Interface) adapter.getItem(pos);

                if (!tfi.tabLocked()) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        beaconManager = new BeaconManager(this);
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> nearables) {
                Log.d(TAG, "Discovered nearables: " + nearables);

                handleNearables(nearables);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startNearableDiscovery();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        beaconManager.stopNearableDiscovery(scanId);
        beaconManager.disconnect();
    }

    public void handleNearables(List<Nearable> nearables) {

        TabFrag_Interface tfa = (TabFrag_Interface) adapter.getItem(mViewPager.getCurrentItem());
        tfa.handleNearables(nearables);
    }
}