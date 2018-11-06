package org.kamsoft.school.school;

import android.app.Dialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.kamsoft.school.school.Database.ConnectivityReceiver;
import org.kamsoft.school.school.Session.SessionManager;
import org.kamsoft.school.school.utils.Tools;
import org.kamsoft.school.school.utils.ViewAnimation;

import java.util.ArrayList;
import java.util.List;

public class MainHomWork extends AppCompatActivity {

    private View parent_view;
    private boolean rotate = false;
    boolean isConnected;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hom_work);

        initToolbar();

        session = new SessionManager(getApplicationContext());

        // parent layout must coordinator layout
        parent_view = findViewById(R.id.coordinator_lyt);

        if(!checkConnection())
            {
                return;
            }else{
        }

        ((FloatingActionButton) findViewById(R.id.fab_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate = ViewAnimation.rotateFab(v, !rotate);
                if (rotate) {

                } else {

                }

                showCustomDialog();
            }
        });

        initComponent();
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Custom");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


    }

    String popUpContents[];
    PopupWindow popupWindowDogs;
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_homwork);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



        final List<String> dogsList = new ArrayList<String>();
        dogsList.add("Class 1D::1");
        dogsList.add("Class 2D::2");
        dogsList.add("KG 1::3");
        dogsList.add("KG 2::4");



        final ImageButton buttonShowDropDown = (ImageButton) dialog.findViewById(R.id.classes);
        buttonShowDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.classes:
                        popUpContents = new String[dogsList.size()];
                        dogsList.toArray(popUpContents);
                        popupWindowDogs = popupWindowDogs();
                        // show the list view as dropdown
                        popupWindowDogs.showAsDropDown(view, -5, 0);
                        break;
                }
            }
        });



        final List<String> dogsList2 = new ArrayList<String>();
        dogsList2.add("Math::1");
        dogsList2.add("English::2");
        dogsList2.add("Arabic::3");
        dogsList2.add("Art::4");


        final ImageButton buttonShowDropDown2 = (ImageButton) dialog.findViewById(R.id.classes2);
        buttonShowDropDown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.classes2:
                        popUpContents = new String[dogsList2.size()];
                        dogsList2.toArray(popUpContents);
                        popupWindowDogs = popupWindowDogs2();
                        // show the list view as dropdown
                        popupWindowDogs.showAsDropDown(view, -5, 0);
                        break;
                }
            }
        });


        txt_Classes = (EditText) dialog.findViewById(R.id.txt_Classes);
        subject = (EditText) dialog.findViewById(R.id.subject);
        final EditText subject3 = (EditText) dialog.findViewById(R.id.subject3);


        String[] test = {"AA","BB","CC"};
        ArrayAdapter<String> array = new ArrayAdapter<>(this, R.layout.simple_spinner_item, test);
        array.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    EditText txt_Classes,subject;
    public PopupWindow popupWindowDogs() {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewDogs = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewDogs.setAdapter(dogsAdapter(popUpContents));

        // set the item click listener
        listViewDogs.setOnItemClickListener(new DogsDropdownOnItemClickListener());

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(250);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs);

        return popupWindow;
    }

    public PopupWindow popupWindowDogs2() {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewDogs = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewDogs.setAdapter(dogsAdapter(popUpContents));

        // set the item click listener
        listViewDogs.setOnItemClickListener(new DogsDropdownOnItemClickListener2());

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(250);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs);

        return popupWindow;
    }

    private ArrayAdapter<String> dogsAdapter(String dogsArray[]) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, dogsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[1];

                // visual settings for the list item
                TextView listItem = new TextView(MainHomWork.this);

                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(22);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.WHITE);

                return listItem;
            }
        };

        return adapter;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkConnection() {
        if(ConnectivityReceiver.isConnected()) {
            isConnected = true;
            showSnack(isConnected);
            return true;
        }
        else
        {
            isConnected = false;
            showSnack(isConnected);
            return false;
        }
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (!isConnected) {
            message = "Sorry.. No internet connection!";
            color = Color.WHITE;

            Snackbar snackbar = Snackbar.make(findViewById(R.id.recycler_view), message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            textView.setBackgroundColor(Color.BLACK);
            snackbar.show();
        }
    }

    private  class DogsDropdownOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // get the context and main activity to access variables
            Context mContext = view.getContext();
            MainHomWork mainActivity = ((MainHomWork) mContext);

            // add some animation when a list item was clicked
            Animation fadeInAnimation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in);
            fadeInAnimation.setDuration(10);
            view.startAnimation(fadeInAnimation);

            // dismiss the pop up
            mainActivity.popupWindowDogs.dismiss();

            // get the text and set it as the button text
            String selectedItemText = ((TextView) view).getText().toString();
            if(txt_Classes.getText().equals(String.valueOf("")))
            {
                txt_Classes.setText(selectedItemText);
            }
            else
            {
                txt_Classes.setText(txt_Classes.getText() + ", " + selectedItemText);
            }

            // get the id
            String selectedItemTag = ((TextView) view).getTag().toString();
            //Toast.makeText(mContext, "Dog ID is: " + selectedItemTag, Toast.LENGTH_SHORT).show();

        }
    }


    private  class DogsDropdownOnItemClickListener2 implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // get the context and main activity to access variables
            Context mContext = view.getContext();
            MainHomWork mainActivity = ((MainHomWork) mContext);

            // add some animation when a list item was clicked
            Animation fadeInAnimation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in);
            fadeInAnimation.setDuration(10);
            view.startAnimation(fadeInAnimation);

            // dismiss the pop up
            mainActivity.popupWindowDogs.dismiss();

                // get the text and set it as the button text
            String selectedItemText = ((TextView) view).getText().toString();
            if(subject.getText().equals(String.valueOf("")))
            {

            }
            else
            {
                //subject.setText(subject.getText() + ", " + selectedItemText);
            }
            subject.setText(selectedItemText);
            // get the id
            String selectedItemTag = ((TextView) view).getTag().toString();
            //Toast.makeText(mContext, "Dog ID is: " + selectedItemTag, Toast.LENGTH_SHORT).show();

        }
    }
}

