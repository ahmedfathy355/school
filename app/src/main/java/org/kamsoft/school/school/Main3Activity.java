package org.kamsoft.school.school;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.kamsoft.school.school.Database.ConnectivityReceiver;
import org.kamsoft.school.school.Database.DB;
import org.kamsoft.school.school.adapter.homeworkAdapter;
import org.kamsoft.school.school.adapter.subjectsItems;
import org.kamsoft.school.school.Session.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private List<subjectsItems> subjectsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private homeworkAdapter mAdapter;

    int hour, min;
    java.sql.Time timeValue;
    SimpleDateFormat format;
    Calendar c;
    String DayName;
    int year, month, day,_DAY_OF_WEEK;
    Date date;
    TextView SelectedDate;
    Button NextDay,PrevDay;
    boolean isConnected;
    Connection connect;
    PreparedStatement stmt , stmt2;
    ResultSet rs,rs2;
    SessionManager session;

    int[] images = {R.drawable.ic_navigate_before_black_24dp,R.drawable.ic_navigate_next_black_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

       try
       {
           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);

           session = new SessionManager(getApplicationContext());
           session.createServerSession("sql5007.site4now.net", "DB_A27259_school","DB_A27259_school_admin", "KAMSOFT@admindb123");
           session = new SessionManager(getApplicationContext());
           HashMap<String, String> server = session.getServerDetails();
           connect = DB.CONN(server.get(SessionManager.DB_User), server.get(SessionManager.DB_Pass), server.get(SessionManager.DB_Name), server.get(SessionManager.Server_ID));

           NextDay = (Button) findViewById(R.id.nexD);
           PrevDay = (Button) findViewById(R.id.prevD);
           SelectedDate = (TextView) findViewById(R.id.mDate);
           recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

           if(!checkConnection())
           {
               return;
           }

           if(DB.CONN("DB_A27259_school_admin", "KAMSOFT@admindb123", "DB_A27259_school", "sql5007.site4now.net") == null)
           {
               Toast.makeText(Main3Activity.this,"Login Error  Server / Auth Error", Toast.LENGTH_LONG);
               return;
           }



           c = Calendar.getInstance();
           hour = c.get(Calendar.HOUR_OF_DAY);
           min = c.get(Calendar.MINUTE);
           year = c.get(Calendar.YEAR);
           month = c.get(Calendar.MONTH);
           day = c.get(Calendar.DAY_OF_MONTH);
           _DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK);
           setDate();
           GetHomeWork(c.getTime());


        }
       catch (Exception ex)
       {
           Toast.makeText(this , ex.getMessage()  ,Toast.LENGTH_LONG);}



        NextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    c.add(Calendar.DATE, 1);
                    setDate();
                    GetHomeWork(c.getTime());

                } catch (Exception ex) {
                }
            }
        });

        PrevDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    c.add(Calendar.DATE, -1);
                    setDate();
                    GetHomeWork(c.getTime());
                } catch (Exception ex) {
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.men_homwork)
        {

        }
        if(id == R.id.men_Date)
        {
            DatePickerDialog dd = new DatePickerDialog(Main3Activity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view,int year,
                                              int monthOfYear, int dayOfMonth ) {
                            try {

                                SimpleDateFormat formatter = new SimpleDateFormat("EEE d-MM");
                                _DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK);
                                DayName = new DateFormatSymbols().getShortWeekdays()[_DAY_OF_WEEK];
                                String dateInString =  DayName + " " +dayOfMonth + "-"+ (monthOfYear + 1) ;
                                date = formatter.parse(dateInString);
                                SelectedDate.setText(formatter.format(date).toString());


                            } catch (Exception ex) {

                            }

                        }
                    }, year, month,day);
            dd.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDate(){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE d-MM");
            _DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK);
            DayName = new DateFormatSymbols().getShortWeekdays()[_DAY_OF_WEEK];
            String dateInString =  DayName + " " +c.get(Calendar.DAY_OF_MONTH) + "-"+ (c.get(Calendar.MONTH) + 1) ;
            date = formatter.parse(dateInString);
            SelectedDate.setText(formatter.format(date).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void GetHomeWork(Date _date){
        try {
            String query = "Select * From Homwork Where (CONVERT(varchar, CreatedDate, 110) = CONVERT(datetime,'"+_date+"', 110 ) ) ";
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(query);
            List<subjectsItems> _subjects = new ArrayList<subjectsItems>();
            while (rs.next()) {
                subjectsItems object = new subjectsItems();
                object.setSubject_id(Integer.parseInt( rs.getString("") ) );

                String IsCalling = rs.getString("");
                if(IsCalling.contains("0"))
                {
                    object.setImg(images[0]+"");
                }
                else
                {
                    object.setImg(images[1]+"");
                }
                object.setHomework(rs.getString(""));
                object.setSubjectName(rs.getString(""));
                object.setTeacher(rs.getString(""));
                _subjects.add(object);
            }
            mAdapter = new homeworkAdapter(_subjects);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL ));
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        catch (Exception ex)
        {}

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
