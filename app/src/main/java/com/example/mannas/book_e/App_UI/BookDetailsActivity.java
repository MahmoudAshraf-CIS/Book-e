package com.example.mannas.book_e.App_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mannas.book_e.App_UI.FragmentHome.BookDetailFragment;
import com.example.mannas.book_e.R;

/**
 * Created by Mannas
 */
public class BookDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_activity);

        BookDetailFragment detailFragment = new BookDetailFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_activity,detailFragment )
                .commit();
    }


}
