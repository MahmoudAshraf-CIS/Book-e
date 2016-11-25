package com.example.mannas.book_e.App_Data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by Mannas
 */
public class Query extends AsyncTaskLoader<ArrayList<BookView>> {
    String query;

    public Query(Context context,String QueryString) {
        super(context);
        query = QueryString;
    }

    @Override
    public ArrayList<BookView> loadInBackground() {
        return null;
    }
}
