package org.kamsoft.school.school;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.kamsoft.school.school.adapter.ScanPagerAdapter;
import org.kamsoft.school.school.utils.AppUtils;

import java.util.ArrayList;


public class ScanPagerActivity extends AppCompatActivity {

    private Activity mActivity;
    private Context mContext;

    private ViewPager mViewPager;
    private ArrayList<String> mFragmentItems;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initViews();
        initFunctionality();
        initListeners();

    }

    private void initVars() {
        mActivity = ScanPagerActivity.this;
        mContext = mActivity.getApplicationContext();
        mFragmentItems = new ArrayList<>();
    }

    private void initViews() {
        setContentView(R.layout.activity_home);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

    }

    private void initFunctionality() {
        if ((ContextCompat.checkSelfPermission( mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission( mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                    mActivity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 445);
        } else {
            setUpViewPager();
        }

        // TODO Sample banner Ad implementation
        //AdManager.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));

    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_scan:
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.nav_Skip:
                        mViewPager.setCurrentItem(1, true);
//                        Intent _SendTicket = new Intent(ScanPagerActivity.this  , SendTicket.class);
//                        startActivity(_SendTicket);
                        finish();
                        break;
                }
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.nav_scan);
                }
//                else if(position == 1) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_Skip);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpViewPager() {

        mFragmentItems.add(getString(R.string.menu_scan));
        //mFragmentItems.add(getString(R.string.menu_skip));

        ScanPagerAdapter scanPagerAdapter = new ScanPagerAdapter(getSupportFragmentManager(), mFragmentItems);
        mViewPager.setAdapter(scanPagerAdapter);
        scanPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 445) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpViewPager();
            } else {
                AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
            }

        }
    }



    @Override
    public void onBackPressed() {
        AppUtils.tapToExit(mActivity);
    }
}
