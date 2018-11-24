package org.kamsoft.school.school.fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

import org.kamsoft.school.school.R;
import org.kamsoft.school.school.Session.SessionManager;
import org.kamsoft.school.school.eventshome.adapter.MessagesAdapter;
import org.kamsoft.school.school.eventshome.helper.DividerItemDecoration;
import org.kamsoft.school.school.eventshome.model.Message;
import org.kamsoft.school.school.eventshome.network.ApiClient;
import org.kamsoft.school.school.eventshome.network.ApiInterface;
import org.kamsoft.school.school.Database.DB;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavigationHomeWorkActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MessagesAdapter.MessageAdapterListener{


private TabLayout tabLayout;
private ViewPager firstViewPager;

private List<Message> messages = new ArrayList<>();
private RecyclerView recyclerView;
private MessagesAdapter mAdapter;
private SwipeRefreshLayout swipeRefreshLayout;
private ActionModeCallback actionModeCallback;
private ActionMode actionMode;

        Connection connect;
        PreparedStatement stmt ;
        ResultSet rs;
        SessionManager session;


@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

public NavigationHomeWorkActivity(){}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
final View rootView =  inflater.inflate(R.layout.fragment_navigation_homework, container, false);

        session = new SessionManager(getActivity().getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add2);
        fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_View2);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
@Override
public void onRefresh()
        {
        // do nothing
        getInbox2();
        }
        });

        mAdapter = new MessagesAdapter(rootView.getContext(), messages, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        actionModeCallback = new ActionModeCallback();

        // show loader and fetch messages
        swipeRefreshLayout.post(
        new Runnable() {
@Override
public void run() {
        getInbox2();
        }
        }
        );

        return rootView;

        }


private void getInbox2() {

        try{
        swipeRefreshLayout.setRefreshing(true);

        //HashMap<String, String> user = session.getUserDetails();

            connect = DB.CONN("DB_A27259_school_admin", "KAMSOFT@admindb123", "DB_A27259_school", "sql5007.site4now.net");
        //Where id = '"+ user.get(SessionManager.NationalID) +"'
        String query = "SELECT  id, isImportant, picture, from_name, subject, message, timestamp, isRead  FROM     Inbox  ";
        stmt = connect.prepareStatement(query);
        rs = stmt.executeQuery();

        //List<Message> messages = new LinkedList<Message>();
        messages.clear();
        while (rs.next()) {
        //List<String> datanum = null;
        Message datanum = new Message ();
        datanum.id =  rs.getInt("id");
        datanum.isImportant = rs.getBoolean("isImportant");
        //datanum.picture = rs.getString("picture");
        datanum.from_name = rs.getString("from_name");
        datanum.subject = rs.getString("subject");
        datanum.message = rs.getString("message");
        datanum.timestamp = rs.getString("timestamp");
        datanum.isRead = rs.getBoolean("isRead");
        messages.add(datanum);
        }



        //messages.addAll(data);
//            for (Map<String, String> message :  data  ) {
//                // generate a random color
//                //message.setColor(getRandomMaterialColor("400"));
//                messages.add((Message) message);
//            }

        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        }
        catch (Exception ex)
        {
        Toast.makeText(getActivity().getApplicationContext(),  ex.getMessage(), Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
        }




        }




/**
 * Fetches mail messages by making HTTP request
 * url: http://api.androidhive.info/json/inbox.json
 */
private void getInbox() {
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiService =
        ApiClient.getClient().create(ApiInterface.class);

        Call<List<Message>> call = apiService.getInbox();
        call.enqueue(new Callback<List<Message>>() {
@Override
public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
        // clear the inbox
        messages.clear();

        // add all the messages
        //messages.addAll(response.body());

        // TODO - avoid looping
        // the loop was performed to add colors to each message
        for (Message message : response.body()) {
        // generate a random color
        message.setColor(getRandomMaterialColor("400"));
        messages.add(message);
        }

        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        }

@Override
public void onFailure(Call<List<Message>> call, Throwable t) {
        Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
        }
        });
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

@Override
public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
        getInbox();
        }

@Override
public void onIconClicked(int position) {
        if (actionMode == null) {
        actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }

        toggleSelection(position);
        }

@Override
public void onIconImportantClicked(int position) {
        // Star icon is clicked,
        // mark the message as important
        Message message = messages.get(position);
        message.setImportant(!message.isImportant());
        messages.set(position, message);
        mAdapter.notifyDataSetChanged();
        }

@Override
public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter.getSelectedItemCount() > 0) {
        enableActionMode(position);
        } else {
        // read the message which removes bold from the row
        Message message = messages.get(position);
        message.setRead(true);
        messages.set(position, message);
        mAdapter.notifyDataSetChanged();

        Toast.makeText(getActivity().getApplicationContext(), "Read: " + message.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }

@Override
public void onRowLongClicked(int position) {
        // long press is performed, enable action mode
        enableActionMode(position);
        }

private void enableActionMode(int position) {
        if (actionMode == null) {
        actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
        }

private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
        actionMode.finish();
        } else {
        actionMode.setTitle(String.valueOf(count));
        actionMode.invalidate();
        }
        }


private class ActionModeCallback implements ActionMode.Callback {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

        // disable swipe refresh if action mode is enabled
        swipeRefreshLayout.setEnabled(false);
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
                deleteMessages();
                mode.finish();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mAdapter.clearSelections();
        swipeRefreshLayout.setEnabled(true);
        actionMode = null;
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.resetAnimationIndex();
                // mAdapter.notifyDataSetChanged();
            }
        });
    }
}

    // deleting the messages from recycler view
    private void deleteMessages() {
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions =
                mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }






}
