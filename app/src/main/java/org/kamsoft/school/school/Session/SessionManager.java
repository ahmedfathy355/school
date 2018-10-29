package org.kamsoft.school.school.Session;

import java.util.HashMap;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.kamsoft.school.school.Main3Activity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences pref2;

    // Editor for Shared preferences
    Editor editor_user;
    Editor editor_server;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SchoolPref";
    private static final String PREF_NAME2 = "SchoolPref2";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String Auth = "IsAuth";

    public static final String UserName = "name";
    public static final String Password = "password";
    public static final String EmployeeID = "employeeID";
    public static final String Server_ID = "ServerID";
    public static final String DB_Name = "DBName";
    public static final String DB_User = "DBUser";
    public static final String DB_Pass = "DBPass";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor_user = pref.edit();

        pref2 = _context.getSharedPreferences(PREF_NAME2, PRIVATE_MODE);
        editor_server = pref2.edit();

    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String password,String employeeID){
        // Storing login value as TRUE
        editor_user.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor_user.putString(UserName, name);

        // Storing email in pref
        editor_user.putString(Password, password);
        editor_user.putString(EmployeeID, employeeID);
        // commit changes
        editor_user.commit();
    }

    public void createServerSession(String ServerID, String DBName,String DBUser , String DBPass){
        editor_server.putBoolean(Auth, true);

        editor_server.putString(Server_ID, ServerID);
        editor_server.putString(DB_Name, DBName);
        editor_server.putString(DB_User, DBUser);
        editor_server.putString(DB_Pass, DBPass);
        editor_server.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Main3Activity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(UserName, pref.getString(UserName, null));

        // user email id
        user.put(Password, pref.getString(Password, null));
        user.put(EmployeeID, pref.getString(EmployeeID, null));
        // return user
        return user;
    }

    public HashMap<String, String> getServerDetails(){
        HashMap<String, String> server = new HashMap<String, String>();
        server.put(Server_ID, pref2.getString(Server_ID, null));
        server.put(DB_Name, pref2.getString(DB_Name, null));
        server.put(DB_User, pref2.getString(DB_User, null));
        server.put(DB_Pass, pref2.getString(DB_Pass, null));
        return server;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor_user.clear();
        editor_user.commit();

        // After logout redirect user to Loing Activity
        //Intent i = new Intent(_context, Activitylogin.class);
        // Closing all the Activities
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
//       _context.startActivity(i);
        System.exit(0);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
