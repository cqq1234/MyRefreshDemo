package com.qq.administrator.myrefreshdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.qq.administrator.myrefreshdemo.ui.fragments.RefreshPracticeFragment;
import com.qq.administrator.myrefreshdemo.ui.fragments.RefreshStylesFragment;
import com.qq.administrator.myrefreshdemo.ui.fragments.RefreshUsingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.navigation_practice);
    }

    private enum TabFragment{
        practice(R.id.navigation_practice,RefreshPracticeFragment.class),
        style(R.id.navigation_style,RefreshStylesFragment.class),
        using(R.id.navigation_using,RefreshUsingFragment.class)
        ;
        private final int menuId;
        private final Class<? extends Fragment> clazz;
        private Fragment fragment;

        TabFragment(int menuId, Class<? extends Fragment> clazz) {
            this.menuId = menuId;
            this.clazz = clazz;
        }

        public Fragment fragment(){
            if (fragment == null){
                try {
                    fragment = clazz.newInstance();
                }catch (Exception e){
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public static TabFragment from(int itemId){
            for (TabFragment fragment : values()){
                if (fragment.menuId == itemId){
                    return fragment;
                }
            }
            return practice;
        }

        public static void onDestroy(){
            for (TabFragment fragment : values()){
                fragment.fragment = null;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,TabFragment.from(item.getItemId()).fragment())
                .commit();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
    }
}
