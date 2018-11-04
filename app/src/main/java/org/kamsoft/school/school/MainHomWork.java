package org.kamsoft.school.school;

import android.app.Dialog;
import android.app.LauncherActivity;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.kamsoft.school.school.Database.ConnectivityReceiver;
import org.kamsoft.school.school.Session.SessionManager;
import org.kamsoft.school.school.utils.Tools;
import org.kamsoft.school.school.utils.ViewAnimation;

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

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_homwork);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;





        final ImageButton bb = (ImageButton) dialog.findViewById(R.id.classes);
        (bb).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PopupMenu _pop = new PopupMenu(MainHomWork.this , bb);
                _pop.getMenuInflater().inflate(R.menu.poupup_menu , _pop.getMenu());
                _pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        EditText tt = (EditText)findViewById(R.id.txt_Classes);
                        tt.setText(item.getTitle());
                        return true;
                    }
                });
            }
        });



        final EditText txt_Classes = (EditText) dialog.findViewById(R.id.txt_Classes);
        final EditText subject = (EditText) dialog.findViewById(R.id.subject);
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
//                Event event = new Event();
//                event.email = tv_email.getText().toString();
//                event.name = et_name.getText().toString();
//                event.location = et_location.getText().toString();
//                event.from = spn_from_date.getText().toString() + " (" + spn_from_time.getText().toString() + ")";
//                event.to = spn_to_date.getText().toString() + " (" + spn_to_time.getText().toString() + ")";
//                event.is_allday = cb_allday.isChecked();
//                event.timezone = spn_timezone.getSelectedItem().toString();
//                displayDataResult(event);

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
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
}
