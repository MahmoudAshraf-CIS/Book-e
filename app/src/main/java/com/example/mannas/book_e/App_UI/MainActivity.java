package com.example.mannas.book_e.App_UI;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mannas.book_e.App_UI.FragmentHome.FragmentHome;
import com.example.mannas.book_e.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        if(searchBar!=null){
            searchBar.inflateMenu(R.menu.menu_main);
            searchBar.getMenu().setOnMenuItemClickListener(this);
            searchBar.setHint( getString( R.string.search_hint));
            searchBar.setSpeechMode(false);

            searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean b) {
                    Toast.makeText(getBaseContext(),"changed",Toast.LENGTH_LONG).show();
                }
                @Override
                public void onSearchConfirmed(CharSequence charSequence) {
                    Toast.makeText(getBaseContext(), "new Activity result of "+charSequence ,Toast.LENGTH_LONG).show(); //// TODO: 24/11/2016  search result
                }

                @Override
                public void onButtonClicked(int i) {
                    Toast.makeText(getBaseContext(),"button "+Integer.toString(i)+"clicked",Toast.LENGTH_LONG).show();
                }
            });
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if(tabLayout!=null) {
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home) );
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.fav) );
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            if(viewPager!=null){
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {}
                });
            }
        }
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getBaseContext(),"action_settings",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** ***************************** Inner Class *******************
     * Created by Mannas on 24/11/2016.
     */
    public static class PagerAdapter  extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FragmentHome();
                case 1:
                    return new FragmentLocal();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }



}
