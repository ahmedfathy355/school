package org.kamsoft.school.school.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;


import net.sourceforge.jtds.jdbc.DateTime;

import org.kamsoft.school.school.Database.DB;
import org.kamsoft.school.school.HomeActivity;
import org.kamsoft.school.school.R;
import org.kamsoft.school.school.adapter.Classess;
import org.kamsoft.school.school.data.preference.AppPreference;
import org.kamsoft.school.school.data.preference.PrefKey;
import org.kamsoft.school.school.utils.ActivityUtils;
import org.kamsoft.school.school.utils.AppUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;

    private ViewGroup contentFrame;
    private ZXingScannerView zXingScannerView;
    private ArrayList<Integer> mSelectedIndices;

    private FloatingActionButton flash, focus, camera;
    private boolean isFlash, isAutoFocus;
    private int camId, frontCamId, rearCamId;

    TextView scaned_name;
    PreparedStatement stmt ;
    ResultSet rs;
    Connection connect;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();

        zXingScannerView = new ZXingScannerView(mActivity);
        setupFormats();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);

        scaned_name = (TextView) rootView.findViewById(R.id.txt_ScanedName);
        initView(rootView);
        initListener();

        return rootView;

    }

    private void initVar() {
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();

        isFlash = AppPreference.getInstance(mContext).getBoolean(PrefKey.FLASH, false); // flash off by default
        isAutoFocus = AppPreference.getInstance(mContext).getBoolean(PrefKey.FOCUS, true); // auto focus on by default
        camId = AppPreference.getInstance(mContext).getInteger(PrefKey.CAM_ID); // back camera by default
        if(camId == -1) {
            camId = rearCamId;
        }

        loadCams();
    }

    private void initView(View rootView) {
        contentFrame = (ViewGroup) rootView.findViewById(R.id.content_frame);

        flash = (FloatingActionButton) rootView.findViewById(R.id.flash);
        focus = (FloatingActionButton) rootView.findViewById(R.id.focus);
        camera = (FloatingActionButton) rootView.findViewById(R.id.camera);
        initConfigs();

    }


    private void initListener() {

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFlash();
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFocus();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCamera();
            }
        });

        zXingScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {

                String resultStr = result.getText();
                ArrayList<String> previousResult = AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST);
                previousResult.add(resultStr);
                AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST, previousResult);

                zXingScannerView.resumeCameraPreview(this);


                Save_Absence();
                //ActivityUtils.getInstance().invokeActivity(mActivity, HomeActivity.class, true);

            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private void Save_Absence() {


        String BabyNum = "";
        ArrayList<String> arrayList = AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST);
        String lastResult = "";
        try{
            lastResult = arrayList.get(arrayList.size()-1);
        }
        catch (Exception e)
        {}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            BabyNum = Html.fromHtml(lastResult, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            BabyNum = Html.fromHtml(lastResult).toString();
        }

        try {



            connect = DB.CONN("oryx", "oryx", "Nursery", "192.168.1.100");
            String dates = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Calendar.getInstance().getTime());
            String query = "INSERT INTO Absent  ( BabyNum, AbsentDate, UserID) VALUES   ('" + BabyNum + "','"+ dates +"','" + 1 + "')      ";
            Statement statement = connect.createStatement();
            statement.executeUpdate(query);



            String query2 = "SELECT     BabyName FROM         BabyInfo  WHERE     (BabyNum = '"+BabyNum+"'  ) ";
            stmt = connect.prepareStatement(query2);
            rs = stmt.executeQuery();
            List<String> datanum = null;
           while (rs.next()) {
               datanum.add(rs.getString("BabyName"));
            }
            scaned_name.setText(datanum.get(0).toString() + "Scaned Successfully");

//            Typeface typeface = ResourcesCompat.getFont(mContext, R.font.aldrich);
//            scaned_name.setTypeface(typeface);

            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = getResources().getFont(R.font.changa_medium);
            }
            scaned_name.setTypeface(typeface);

            //Toast.makeText(mContext, "Done!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            //alert.showAlertDialog(SendTicket.this, "Server Error ..", e.getMessage(), false);
            scaned_name.setText("Scan Error");
            e.printStackTrace();
        }
    }

    private void activateScanner() {
        if(zXingScannerView != null) {

            if(zXingScannerView.getParent()!=null) {
                ((ViewGroup) zXingScannerView.getParent()).removeView(zXingScannerView); // to prevent crush on re adding view
            }
            contentFrame.addView(zXingScannerView);

            if(zXingScannerView.isActivated()) {
                zXingScannerView.stopCamera();
            }

            zXingScannerView.startCamera(camId);
            zXingScannerView.setFlash(isFlash);
            zXingScannerView.setAutoFocus(isAutoFocus);
        }
    }


    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<>();
            for(int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if(zXingScannerView != null) {
            zXingScannerView.setFormats(formats);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activateScanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(zXingScannerView != null) {
            zXingScannerView.stopCamera();
        }
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if(zXingScannerView != null) {
            if (visible) {
                zXingScannerView.setFlash(isFlash);
            } else {
                zXingScannerView.setFlash(false);
            }
        }
    }


    private void toggleFlash() {
        if (isFlash) {
            isFlash = false;
            flash.setImageResource(R.drawable.ic_flash_on);
        } else {
            isFlash = true;
            flash.setImageResource(R.drawable.ic_flash_off);
        }
        AppPreference.getInstance(mContext).setBoolean(PrefKey.FLASH, isFlash);
        zXingScannerView.setFlash(isFlash);
    }

    private void toggleFocus() {
        if (isAutoFocus) {
            isAutoFocus = false;
            focus.setImageResource(R.drawable.ic_focus_on);
            AppUtils.showToast(mContext, getString(R.string.autofocus_off));
        } else {
            isAutoFocus = true;
            focus.setImageResource(R.drawable.ic_focus_off);
            AppUtils.showToast(mContext, getString(R.string.autofocus_on));
        }
        AppPreference.getInstance(mContext).setBoolean(PrefKey.FOCUS, isAutoFocus);
        zXingScannerView.setFocusable(isAutoFocus);
    }

    private void toggleCamera() {

        if (camId == rearCamId) {
            camId = frontCamId;
        } else {
            camId = rearCamId;
        }
        AppPreference.getInstance(mContext).setInteger(PrefKey.CAM_ID, camId);
        zXingScannerView.stopCamera();
        zXingScannerView.startCamera(camId);
    }

    private void initConfigs() {
        if (isFlash) {
            flash.setImageResource(R.drawable.ic_flash_off);
        } else {
            flash.setImageResource(R.drawable.ic_flash_on);
        }
        if (isAutoFocus) {
            focus.setImageResource(R.drawable.ic_focus_off);
        } else {
            focus.setImageResource(R.drawable.ic_focus_on);
        }
    }

    private void loadCams() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontCamId = i;
            } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                rearCamId = i;
            }
        }
        AppPreference.getInstance(mContext).setInteger(PrefKey.CAM_ID, rearCamId);

    }


}
