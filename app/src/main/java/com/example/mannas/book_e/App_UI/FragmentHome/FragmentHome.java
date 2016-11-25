package com.example.mannas.book_e.App_UI.FragmentHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mannas.book_e.App_Data.BookView;
import com.example.mannas.book_e.App_Data.BookViewsLoader_online;
import com.example.mannas.book_e.App_Data.Utility;
import com.example.mannas.book_e.App_UI.ExploreActivity;
import com.example.mannas.book_e.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by Mannas
 */
public class FragmentHome extends Fragment implements LoaderManager.LoaderCallbacks  {
    public static final int ToReadLoader_id =0;
    BooksRecyclerAdapter ToRead_RecAdapter;
    ArrayList<String> Subjects;
    TagContainerLayout tagsContainer;

    LinearLayout More;
    AVLoadingIndicatorView loading_toread_icn;

    public FragmentHome() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Subjects = new ArrayList<>();
        ToRead_RecAdapter = new BooksRecyclerAdapter();
        getLoaderManager().initLoader( ToReadLoader_id ,null,this).forceLoad();

       // startActivity(new Intent(getContext(), SearchResultsActivity.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment,container,false);
        RecyclerView ToRead_recycler = (RecyclerView) rootView.findViewById(R.id.recyclerView_toread_home);
        loading_toread_icn = (AVLoadingIndicatorView) rootView.findViewById(R.id.loading_toread);
        tagsContainer = (TagContainerLayout) rootView.findViewById(R.id.tags_home);
        tagsContainer.setTags(Utility.Subjects);
        tagsContainer.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Toast.makeText(getContext(),"New Explorer "+text,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity().getBaseContext(),ExploreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ExploreActivity.Subject_Key,text);
                intent.putExtras(bundle);
                startActivity( intent );
            }
            @Override
            public void onTagLongClick(int position, String text) {}
            @Override
            public void onTagCrossClick(int position) {}
        });
        More = (LinearLayout) rootView.findViewById(R.id.more_to_read);
        More.setVisibility(View.GONE);
        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getBaseContext(), ExploreActivity.class));
            }
        });
        ToRead_recycler.setAdapter(ToRead_RecAdapter);
        ToRead_recycler.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        return rootView;
    }


    /*
    ****************   onStart Data Loader
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if(id==ToReadLoader_id)
            return new BookViewsLoader_online(getContext(),4);
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Utility.Subjects.size()>20){
            tagsContainer.setTags(Utility.Subjects.subList(0,20));
        }else{
            tagsContainer.setTags(Utility.Subjects);
        }
        //// TODO: 25/11/2016  update the recent
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(loader.getId() == FragmentHome.ToReadLoader_id){
            // notifyDataSetChanged
            ToRead_RecAdapter.NotifyDataSetChanged(((ArrayList<BookView>) data));
            Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
            More.setVisibility(View.VISIBLE);
            loading_toread_icn.setVisibility(View.GONE);
            if(Utility.Subjects.size()>20){
                tagsContainer.setTags(Utility.Subjects.subList(0,20));
            }else{
                tagsContainer.setTags(Utility.Subjects);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
