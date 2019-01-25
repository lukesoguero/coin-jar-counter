package com.example.android.coinjarcounter;


import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class JarListFragment extends ListFragment {


    private ArrayList<Jar> jars;
    private JarAdapter jarAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        JarDbAdapter dbAdapter = new JarDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        jars = dbAdapter.getAllJars();
        dbAdapter.close();


        jarAdapter = new JarAdapter(getActivity(), jars);

        setListAdapter(jarAdapter);

        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.darker_gray));
        getListView().setDividerHeight(2);

        registerForContextMenu(getListView());

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        launchJarActivity(position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Jar jar = (Jar) getListAdapter().getItem(position);
        switch(item.getItemId()) {
            case R.id.rename:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Rename");

// Set an EditText view to get user input
                final EditText input = new EditText(getActivity());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {



                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
                return true;
            case R.id.delete:
                JarDbAdapter dbAdapter = new JarDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteJar(jar.getJarId());
                jars.clear();
                jars.addAll(dbAdapter.getAllJars());
                jarAdapter.notifyDataSetChanged();
                dbAdapter.close();
        }

        return super.onContextItemSelected(item);
    }

    public void launchJarActivity(int position) {
        Jar jar = (Jar) jarAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), JarActivity.class);
        intent.putExtra(MainActivity.JAR_NAME_EXTRA, jar.getJarName());
        intent.putExtra(MainActivity.JAR_ID_EXTRA, jar.getJarId());
        intent.putExtra(MainActivity.JAR_TOTAL_EXTRA, jar.getJarTotal());
        intent.putExtra(MainActivity.JAR_CODE_EXTRA, 1);

        startActivity(intent);
    }


}
