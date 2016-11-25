package com.example.mannas.book_e.App_Data;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.mannas.book_e.App_Data.BookView;
import com.example.mannas.book_e.App_Data.Utility;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by Mannas
 */
public class BookViewsLoader_online extends AsyncTaskLoader<ArrayList<BookView>> {
    ArrayList<BookView> bookViews;
    ArrayList<String> LS_Subjects;          //subject of the book

    Integer limit;                       //count of books to load
    public static Integer offset = 0 ;   //used for paging
    String subject;



    public BookViewsLoader_online(Context context) {
        super(context);
        bookViews = new ArrayList<>();
        LS_Subjects = new ArrayList<>( Utility.Subjects ) ;

        limit = 4;
        subject="";
    }

    public BookViewsLoader_online(Context context, Integer limit) {
        super(context);
        bookViews = new ArrayList<>();
        LS_Subjects = new ArrayList<>(Utility.Subjects) ;

        this.limit = limit;
        subject ="";

    }

    public BookViewsLoader_online(Context context, String subject, Integer limit) {
        super(context);
        bookViews = new ArrayList<>();
        LS_Subjects = new ArrayList<>() ;
        this.subject = subject;
        this.limit = limit;
    }

    protected void Update_UtilitySubjects(final JsonArray works){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                if(works!=null){
                    ArrayList<String> temp = new ArrayList<>();

                    for(int i=0,k=works.size(); i<k ;i++){
                        JsonObject work = (JsonObject) works.get(i);
                        JsonArray subjects = (JsonArray) work.get("subject");
                        for(int s=0,ss=subjects.size(); s < ss ; s++){
                            JsonElement sub = subjects.get(s);
                            String sub_str = sub.getAsString();
                            sub_str = sub_str.toLowerCase();
                            sub_str = sub_str.replace(' ','_');
                            temp.add(sub_str);
                            if(temp.size()>=40){
                                break;
                            }
                        }
                        if(temp.size()>=40){
                            Utility.Subjects = temp;
                            break;
                        }
                    }
                }
                return null;
            }
        }.execute();
    }

    @Override
    public ArrayList<BookView> loadInBackground() {
        BookView bookView ;
        int index = Math.abs( new Random().nextInt()%(LS_Subjects.size()-1) );
        String uri = "http://openlibrary.org/subjects/" +
                    (subject.equals("")? LS_Subjects.get( index ) : subject)
                    +".json?details=false&ebooks=true&limit="+ limit.toString()
                    +"&offset="+offset.toString();

        offset+=limit;
            //uri = "http://openlibrary.org/subjects/love.json?details=false&ebooks=true&limit=4&offset=40";

        JsonObject jo = new JsonObject() ;
        try {
            jo = Ion.with(getContext()).load(uri).asJsonObject().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(jo!=null) {
            JsonArray works = jo.getAsJsonArray("works");

            if(works!=null){
                Update_UtilitySubjects(works);
                for(int i=0,j=works.size();i<j;i++){
                    bookView = new BookView();

                    /***     OL_key_M    ***/
                    JsonObject book = (JsonObject) works.get(i);
                    if(book.has("cover_edition_key")){
                        JsonElement OL_k_M = book.get("cover_edition_key");
                        bookView.setOL_key_M( OL_k_M.getAsString() );
                    }else {
                        JsonElement key = book.get("key");
                        String str =key.toString();
                        str = str.substring(str.lastIndexOf('/')+1 , str.length()-1);  // here the OL_key_W in    str

                        //get the OL_key_M
                        JsonArray JA = new JsonArray();
                        try {
                            final String url = "http://openlibrary.org/query.json?type=/type/edition&works=/works/"+str;
                            JA = Ion.with(getContext()).load(url).asJsonArray().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        if(JA.size()>0){
                            jo = (JsonObject) JA.get(0);
                            str = jo.get("key").toString();
                            str = str.substring(str.lastIndexOf('/')+1 , str.length()-1);
                        }
                        bookView.setOL_key_M(str);
                    }
                    /***  has_fulltext **/
                    bookView.setHas_fulltext(book.get("has_fulltext").getAsBoolean());
                    /****    title    **/
                    bookView.setTitle( book.get("title").getAsString() );
                    /*** author **/
                    JsonArray autorsArray = (JsonArray) book.get("authors");
                    if(autorsArray.size()>0){
                        bookView.setAuthor( (((JsonObject) autorsArray.get(0)).get("name").getAsString()) );
                    }

                    bookViews.add(bookView);
                }
            }
        }
        Log.i("Loader : ","finished Loading BookViews  !");
        return bookViews;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<BookView> data) {
        super.onCanceled(data);
    }
}
