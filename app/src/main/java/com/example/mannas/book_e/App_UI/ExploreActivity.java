package com.example.mannas.book_e.App_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mannas.book_e.App_Data.BookView;
import com.example.mannas.book_e.App_Data.BookViewsLoader_online;
import com.example.mannas.book_e.App_UI.FragmentHome.BooksRecyclerAdapter;
import com.example.mannas.book_e.R;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Mannas
 */
public class ExploreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks , PopupMenu.OnMenuItemClickListener {
    int AnySub_loadr = 0;
    int DefinedSub_loadr =1;
    boolean hasSubject;
    String SubjectVal;
    public static final String Subject_Key ="subKey";

    BooksRecyclerAdapter exploreAdapter;
    TextView more_txt;
    AVLoadingIndicatorView loading_icn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_activity);
        setSearchBar();
        setRecycler();
        setMore_btn(this);
        if(getIntent().getExtras() == null) {
            hasSubject = false;
            getSupportLoaderManager().initLoader(AnySub_loadr, null, this).forceLoad();
        }else{
            hasSubject = true;
            SubjectVal = getIntent().getExtras().getString(Subject_Key);

            Bundle b = new Bundle();
            b.putString(Subject_Key,SubjectVal);
            BookViewsLoader_online.offset=0;
            getSupportLoaderManager().initLoader(DefinedSub_loadr, b, this).forceLoad();
        }

    }
    
    void setSearchBar(){
        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        if (searchBar != null) {
            searchBar.inflateMenu(R.menu.menu_main);
            searchBar.getMenu().setOnMenuItemClickListener(this);
            searchBar.setHint(getString(R.string.search_hint));
            searchBar.setSpeechMode(false);

            searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean b) {
                    Toast.makeText(getBaseContext(), "changed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSearchConfirmed(CharSequence charSequence) {
                    Toast.makeText(getBaseContext(), "new Activity result of " + charSequence, Toast.LENGTH_LONG).show(); //// TODO: 24/11/2016  search result
                    String q = charSequence.toString();
                    q = q.replace(' ','+');


                }

                @Override
                public void onButtonClicked(int i) {
                    Toast.makeText(getBaseContext(), "button " + Integer.toString(i) + "clicked", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    void setRecycler(){
        RecyclerView r = (RecyclerView) findViewById(R.id.recycler_explore);
        if(r!=null) {
            exploreAdapter = new BooksRecyclerAdapter();
            r.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
            r.setAdapter(exploreAdapter);
        }
    }
    void setMore_btn(final ExploreActivity callbacks){

        more_txt = (TextView) findViewById(R.id.more_txt);
        loading_icn = (AVLoadingIndicatorView) findViewById(R.id.loading_icn);

        if(more_txt!=null)
            more_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!hasSubject){
                        BookViewsLoader_online.offset=0;
                        getSupportLoaderManager().restartLoader( AnySub_loadr ,null,callbacks ).forceLoad();
                    }else{
                        Bundle b = new Bundle();
                        b.putString(Subject_Key,SubjectVal);
                        getSupportLoaderManager().restartLoader(DefinedSub_loadr, b, callbacks).forceLoad();
                    }
                    more_txt.setVisibility(View.GONE);
                    loading_icn.setVisibility(View.VISIBLE);
                }
            });
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == AnySub_loadr) {
            return new BookViewsLoader_online(getBaseContext(), 10);
        }else if(id == DefinedSub_loadr){
            return new BookViewsLoader_online(getBaseContext(),args.getString(Subject_Key),10);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
            if(loader.getId()== AnySub_loadr){
                if(data!=null) {
                    exploreAdapter.addToDataSet(((ArrayList<BookView>) data));
                    more_txt.setVisibility(View.VISIBLE);
                    loading_icn.setVisibility(View.GONE);
                }
            }else if(loader.getId() == DefinedSub_loadr){
                if(data!=null) {
                    exploreAdapter.addToDataSet(((ArrayList<BookView>) data));
                    more_txt.setVisibility(View.VISIBLE);
                    loading_icn.setVisibility(View.GONE);

                }
            }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


}
