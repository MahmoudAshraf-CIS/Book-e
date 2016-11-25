package com.example.mannas.book_e.App_Data;

import java.util.ArrayList;

/**
 * Created by Mannas
 */
public class BookDetail {
   public String OL_key_M;
    public ArrayList<String> publishers;

    public String title;
    public String url;
    public String notes;
    public Integer number_of_pages;
    public ArrayList<String> subjects;
    public String publish_date;
    public ArrayList<Author> authors;
    public ArrayList<String> publish_places;
    public Ebook ebook;

    public BookDetail() {
        publishers = new ArrayList<>();
        subjects = new ArrayList<>();
        publish_places = new ArrayList<>();
        authors = new ArrayList<>();
        ebook = new Ebook();
    }

}
