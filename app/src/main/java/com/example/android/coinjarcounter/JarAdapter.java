package com.example.android.coinjarcounter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by lukes_000 on 8/2/2016.
 */
public class JarAdapter extends ArrayAdapter<Jar> {

    public JarAdapter(Activity context, ArrayList<Jar> jars) {

        super(context, 0, jars);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.jar_list_item, parent, false);
        }
        Jar jar = getItem(position);

        TextView jarName = (TextView) listItemView.findViewById(R.id.jarName);
        jarName.setText(jar.getJarName());

        TextView total = (TextView) listItemView.findViewById(R.id.total_list_item);
        total.setText(NumberFormat.getCurrencyInstance().format(jar.getJarTotal()));

        return listItemView;

    }
}

