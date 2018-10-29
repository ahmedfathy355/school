package org.kamsoft.school.school;

import android.app.Dialog;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.kamsoft.school.school.Database.ConnectivityReceiver;
import org.kamsoft.school.school.Session.SessionManager;
import org.kamsoft.school.school.adapter.Classess;
import org.kamsoft.school.school.utils.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddHomwork extends AppCompatActivity {
    Spinner _Spinner_Levels;
    PreparedStatement stmt ;
    ResultSet rs;
    Connection connect;
    String Level_ID ;
    boolean isConnected;
    int[] images = {R.drawable.ic_navigate_next_black_24dp,R.drawable.ic_navigate_before_black_24dp};
    SessionManager session;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homwork);

        initToolbar();

//        session = new SessionManager(getApplicationContext());
//
//        ArrayList<Levels> data = new ArrayList<Levels>();
        //_Spinner_Levels = (Spinner)findViewById(R.id.spinner_level) ;
//        try
//        {
//            session.createServerSession("sql5007.site4now.net", "DB_A27259_school","DB_A27259_school_admin", "KAMSOFT@admindb123");
//
//            HashMap<String, String> server = session.getServerDetails();
//
//            connect = DB.CONN(server.get(SessionManager.DB_User), server.get(SessionManager.DB_Pass), server.get(SessionManager.DB_Name), server.get(SessionManager.Server_ID));
//
//            if(!checkConnection())
//            {
//                return;
//            }
//
//            if(DB.CONN("DB_A27259_school_admin", "KAMSOFT@admindb123", "DB_A27259_school", "sql5007.site4now.net") == null)
//            {
//                Toast.makeText(AddHomwork.this,"Login Error  Server / Auth Error", Toast.LENGTH_LONG);
//                return;
//            }
//            String query = "SELECT   LevelID, LevelName  FROM    Levels";
//            stmt = connect.prepareStatement(query);
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Integer id= Integer.parseInt( rs.getString("LevelID"));
//                String _Name = rs.getString("LevelName");
//
//                data.add(new Levels(_Name,id));
//            }}
//        catch (Exception ex)
//        {
//            Toast.makeText(this , ex.getMessage() , Toast.LENGTH_LONG);
//        }
//

        //final SpinnerAdapter adapter = new SpinnerAdapter(this,R.layout.spinner_layout,R.id.txt,data);
        //_Spinner_Levels.setAdapter(adapter);

//        _Spinner_Levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Level_ID = adapter.getItem(i).getText();
//                GetClassess(Level_ID);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_cancel_grey_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Basic");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }



    private void GetClassess(String level_id) {
        try {
            if(!checkConnection())
            {
                return;
            }

            String query = "Select * From Classes ";


            Statement statement = connect.createStatement();
            rs = statement.executeQuery(query);
            //ArrayList<HashMap<String, String>> data = null;
            //data = new ArrayList<HashMap<String, String>>();
            final List<Classess> _Classess = new ArrayList<Classess>();
            while (rs.next()) {
                //HashMap<String, String> datanum = new HashMap<String, String>();

                Classess object = new Classess();
                object.setId(Integer.parseInt( rs.getString("ClassID") ) );
                String Classid = rs.getString("ClassID");
                if(Classid.contains("1"))
                {
                    object.set_imgClass(images[0]+"");
                }
                if(Classid.contains("2"))
                {
                    object.set_imgClass(images[1]+"");
                }


                object.set_ClassName(rs.getString("ClassName"));

                _Classess.add(object);

            }

//            final ListView listView_1 = (ListView) findViewById(R.id.listclassess);
//            final ClassessAdapter adapter = new ClassessAdapter(AddHomwork.this, _Classess);
//            listView_1.setAdapter(adapter);
//
//            listView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    Classess model = _Classess.get(i);
//
//                    if (model.isSelected())
//                        model.setSelected(false);
//
//                    else
//                        model.setSelected(true);
//
//                    _Classess.set(i, model);
//
//                    //now update adapter
//                    adapter.updateRecords(_Classess);
//                }
//            });

        } catch (SQLException e) {
            showSnack(true);
        }
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
