package com.example.mannas.book_e.App_Data;

/**
 * Created by Mannas
 */
public class BookView {
    public String title ="";
    public String OL_key_M="";
    public String Author="";
    public Boolean has_fulltext;

    public BookView() {
        title ="";
        OL_key_M="";
        Author="";
        has_fulltext = false;
    }



    public Boolean getHas_fulltext() {
        return has_fulltext;
    }

    public void setHas_fulltext(Boolean has_fulltext) {
        this.has_fulltext = has_fulltext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOL_key_M() {
        return OL_key_M;
    }

    public void setOL_key_M(String OL_key_M) {
        this.OL_key_M = OL_key_M;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}
