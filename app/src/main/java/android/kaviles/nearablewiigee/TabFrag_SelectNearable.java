package android.kaviles.nearablewiigee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.sdk.Nearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelvin on 10/24/16.
 */
public class TabFrag_SelectNearable extends Fragment implements TabFrag_Interface,
        AdapterView.OnItemClickListener, View.OnClickListener {
    private final static String TAG = TabFrag_SelectNearable.class.getSimpleName();

    private boolean tabLock;

    private TabFrag_Data tfd;

    private ArrayList<Nearable> nearableList;
    private EstimoteListAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tabLock = false;

        View view = inflater.inflate(R.layout.tabfrag_selectnearable, container, false);

        nearableList = new ArrayList<>();
        adapter = new EstimoteListAdapter(nearableList);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        if (listView != null) {
            listView.setItemsCanFocus(true);
            listView.setOnItemClickListener(this);
            listView.setAdapter(adapter);
        }

        Button btn_reset = (Button) view.findViewById(R.id.btn_reset);
        if (btn_reset != null) {
            btn_reset.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void handleNearables(List<Nearable> nearables) {

        nearableList.clear();

        for (Nearable nearable : nearables) {
            nearableList.add(nearable);
            tfd.addNearable(nearable.identifier);
        }

        adapter.notifyDataSetChanged();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Send update to main activity

        Nearable nearable = nearableList.get(position);
        String nid = nearable.identifier;

        CheckBox cb_selected = (CheckBox) view.findViewById(R.id.cb_selected);
        if (tfd.isSelected(nid)) {
            cb_selected.setChecked(false);
            tfd.setSelected(nid, false);
        }
        else {
            cb_selected.setChecked(true);
            tfd.setSelected(nid, true);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_reset) {
            nearableList.clear();
            tfd.reset();
        }
    }

    private class EstimoteListAdapter extends BaseAdapter {

        ArrayList<Nearable> el;

        public EstimoteListAdapter(ArrayList<Nearable> estimoteList) {
            el = estimoteList;
        }

        @Override
        public int getCount() {
            return el.size();
        }

        @Override
        public Object getItem(int position) {
            return el.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Nearable nearable = el.get(position);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.estimote_data_row, parent, false);

            CheckBox cb_selected = (CheckBox) row.findViewById(R.id.cb_selected);
            if (tfd.isSelected(nearable.identifier)) {
                cb_selected.setChecked(true);
            }

            TextView tv_identifier, tv_color, tv_type;
            tv_identifier = (TextView) row.findViewById(R.id.tv_identifier);
            tv_color = (TextView) row.findViewById(R.id.tv_color);
            tv_type = (TextView) row.findViewById(R.id.tv_type);

            tv_identifier.setText(nearable.identifier);
            tv_color.setText(nearable.color.text);
            tv_type.setText(nearable.type.text);

            return row;
        }
    }
}
