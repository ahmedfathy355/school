package org.kamsoft.school.school.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v7.widget.GridLayoutManager;

import org.kamsoft.school.school.Database.DB;
import org.kamsoft.school.school.HomeActivity;
import org.kamsoft.school.school.R;
import org.kamsoft.school.school.Session.SessionManager;
import org.kamsoft.school.school.adapter.Classess;
import org.kamsoft.school.school.adapter.ClassessAdapter;
import org.kamsoft.school.school.adapter.HomeCardViewAdapter;
import org.kamsoft.school.school.adapter.HomeCardViewItems;
import org.kamsoft.school.school.eventshome.helper.DividerItemDecoration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ?Ahmed Fathy on 10/12/2017.
 */

public class NavigationHomeActivity  extends Fragment  {

    private RecyclerView recyclerView3;

    private ClassessAdapter cAdapter;
    private List<Classess> classList =new ArrayList<>();
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;


    List<HomeCardViewItems> lstHomeButton ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstHomeButton = new ArrayList<>();
        lstHomeButton.add(new HomeCardViewItems("Home Work","Categorie Book","Description book",R.drawable.studying1));
        lstHomeButton.add(new HomeCardViewItems("Chat","Categorie Book","Description book",R.drawable.conversation));
        lstHomeButton.add(new HomeCardViewItems("Absence","Categorie Book","Description book",R.drawable.absence));
        lstHomeButton.add(new HomeCardViewItems("Fees","Categorie Book","Description book",R.drawable.debitcard));
        lstHomeButton.add(new HomeCardViewItems("Soon","Categorie Book","Description book",R.drawable.babyfinger));
        lstHomeButton.add(new HomeCardViewItems("Soon","Categorie Book","Description book",R.drawable.babyfinger));



    }

    public NavigationHomeActivity(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView =  inflater.inflate(R.layout.fragment_navigation_home, container, false);

        RecyclerView myrv = (RecyclerView) rootView.findViewById(R.id.recyclerview_id);
        HomeCardViewAdapter myAdapter = new HomeCardViewAdapter(rootView.getContext(),lstHomeButton);
        myrv.setLayoutManager(new GridLayoutManager(rootView.getContext(),3));
        myrv.setAdapter(myAdapter);


        //cAdapter = new ClassessAdapter( rootView.getContext(),classList);

//        Classess a = new Classess(1,false,R.drawable.classroom,"SSSS");
//        classList.add(a);
//
//        a = new               lz Classess(0,true,R.drawable.classroom,"LKJH");
//        classList.add(a);


//        cAdapter.notifyDataSetChanged();
//        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.recycler_view3);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        recyclerView3.setLayoutManager(mLayoutManager);
//        recyclerView3.setItemAnimator(new DefaultItemAnimator());
//        recyclerView3.addItemDecoration(new DividerItemDecoration(rootView.getContext(), LinearLayoutManager.HORIZONTAL));
//        recyclerView3.setAdapter(cAdapter);

        actionModeCallback = new ActionModeCallback();
        //getInbox2();
        return rootView;

    }

    Connection connect;
    PreparedStatement stmt ;
    ResultSet rs;
    SessionManager session;

    private void getInbox2() {

        try{

            //HashMap<String, String> user = session.getUserDetails();

            connect = DB.CONN("DB_A27259_school_admin", "KAMSOFT@admindb123", "DB_A27259_school", "sql5007.site4now.net");
            //Where id = '"+ user.get(SessionManager.NationalID) +"'
            String query = "SELECT  id, isImportant, picture, from_name, subject, message, timestamp, isRead  FROM     Inbox  ";
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            //List<Message> messages = new LinkedList<Message>();
            classList.clear();
            while (rs.next()) {
                //List<String> datanum = null;
                Classess datanum = new Classess ();
                datanum._ClassName =  "Class A";

                classList.add(datanum);
            }



            //messages.addAll(data);
//            for (Map<String, String> message :  data  ) {
//                // generate a random color
//                //message.setColor(getRandomMaterialColor("400"));
//                messages.add((Message) message);
//            }

            cAdapter.notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity().getApplicationContext(),  ex.getMessage(), Toast.LENGTH_LONG).show();
        }




    }

    /**
     * chooses a random color from array.xml
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array",  getActivity().getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(getActivity().getApplicationContext(), "Search...", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if action mode is enabled
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // delete all the selected messages
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            //cAdapter.getItemId();
            actionMode = null;
            recyclerView3.post(new Runnable() {
                @Override
                public void run() {
                    //cAdapter.resetAnimationIndex();
                    // mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
