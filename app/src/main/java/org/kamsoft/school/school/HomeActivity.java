package org.kamsoft.school.school;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.kamsoft.school.school.fragments.NavigationDashboardActivity;
import org.kamsoft.school.school.fragments.NavigationHomeActivity;
import org.kamsoft.school.school.fragments.NavigationHomeWorkActivity;
import org.kamsoft.school.school.fragments.NavigationNotificationActivity;


public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;

    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_homework);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 3:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_homework:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

    private void setupViewPager(ViewPager viewPager) {
        BottomNavPagerAdapter adapter = new BottomNavPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NavigationHomeActivity());
//        adapter.addFragment(new NavigationHomeWorkActivity());
//        adapter.addFragment(new NavigationDashboardActivity());
//        adapter.addFragment(new NavigationNotificationActivity());
        viewPager.setAdapter(adapter);
    }

}







//Manually displaying the first fragment - one time only
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, NavigationHomeActivity.newInstance());
//        transaction.commit();

        //Used to select an item programmatically
        //navigation.getMenu().getItem(0).setChecked(true);
