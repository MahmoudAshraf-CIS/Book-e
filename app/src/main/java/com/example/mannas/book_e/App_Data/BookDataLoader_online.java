package com.example.mannas.book_e.App_Data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Mannas
 */
public class BookDataLoader_online extends AsyncTaskLoader<BookDetail> {
    BookDetail bookDetail;

    public BookDataLoader_online(Context context, String OL_key_M) {
        super(context);
        bookDetail = new BookDetail();
        bookDetail.OL_key_M = OL_key_M;
    }

    public static String getDataURL(String OL_key_M){
        return "https://openlibrary.org/api/books?bibkeys=OLID:"+OL_key_M + "&format=json&jscmd=data";
    }
    @Override
    public BookDetail loadInBackground() {

        JsonObject jo = new JsonObject();
        String joKey = "OLID:"+bookDetail.OL_key_M;
        try {
            jo = Ion.with(getContext()).load(getDataURL(bookDetail.OL_key_M)).asJsonObject().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(jo!=null){
            if(jo.has(joKey)){
                JsonObject id = (JsonObject) jo.get(joKey);
                if(id.has("publishers")){
                    JsonArray publishers =   id.get("publishers").getAsJsonArray();
                    for(int i=0,j=publishers.size();i<j;i++){
                        bookDetail.publishers.add(   publishers.get(i).getAsJsonObject().get("name").getAsString()  );
                    }
                }

                if(id.has("title")){
                    bookDetail.title = (id.get("title")).getAsString();
                }
                if(id.has("url")){
                    bookDetail.url = (id.get("url")).getAsString();
                }
                if(id.has("notes")){
                    bookDetail.notes = (id.get("notes")).getAsString();
                }
                if(id.has("number_of_pages")){
                    bookDetail.number_of_pages = (id.get("number_of_pages")).getAsInt();
                }
                if(id.has("subjects")){
                    JsonArray subjects = id.get("subjects").getAsJsonArray();
                    for(int i=0,j=subjects.size();i<j ; i++){
                        bookDetail.subjects.add( subjects.get(i).getAsJsonObject().get("name").getAsString() );
                    }
                }
                if(id.has("publish_date")){
                    bookDetail.publish_date = (id.get("publish_date")).getAsString();
                }
                if(id.has("authors")){
                    JsonArray authors = id.get("authors").getAsJsonArray();
                    for(int i=0,j=authors.size();i<j ; i++){
                        JsonObject author = authors.get(i).getAsJsonObject();
                        Author a = new Author();
                        a.name = author.get("name").getAsString();
                        a.url = author.get("url").getAsString();
                        bookDetail.authors.add(a);
                    }
                }
                if(id.has("publish_places")){
                    JsonArray publish_places = id.get("publish_places").getAsJsonArray();
                    for(int i=0,j=publish_places.size();i<j ; i++){
                        bookDetail.publish_places.add( publish_places.get(i).getAsJsonObject().get("name").getAsString() );
                    }
                }
                if(id.has("ebooks")){
                    Ebook ebook = new Ebook();
                    JsonArray ebooks = id.get("ebooks").getAsJsonArray();
                    for(int i=0,j=ebooks.size();i<j;i++){
                        int x =9;
                        JsonObject object = ebooks.get(i).getAsJsonObject();
                        if(object.has("formats")){
                            JsonObject formats = object.get("formats").getAsJsonObject();
                            if(formats.has("pdf")){
                                bookDetail.ebook.pdf_url = formats.get("pdf").getAsJsonObject().get("url").getAsString();
                             x=0;
                            }
                            if(formats.has("epub")){
                                bookDetail.ebook.epub_url = formats.get("epub").getAsJsonObject().get("url").getAsString();
                                x=0;
                            }
                            if(formats.has("text")){
                                bookDetail.ebook.text_urrl = formats.get("text").getAsJsonObject().get("url").getAsString();
                                x=0;
                            }
                        }
                        if(object.has("preview_url")){
                            bookDetail.ebook.preview_url = object.get("preview_url").getAsString();
                        }
                        if(object.has("read_url")){
                            bookDetail.ebook.read_url = object.get("read_url").getAsString();
                        }
                        if(object.has("availability")){
                            bookDetail.ebook.full = object.get("availability").getAsString().equals("full");
                        }
                    }
                }


            }
            return bookDetail;
        }
        return null;
    }
}
