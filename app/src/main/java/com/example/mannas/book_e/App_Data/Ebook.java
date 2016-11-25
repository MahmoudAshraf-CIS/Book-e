package com.example.mannas.book_e.App_Data;

/**
 * Created by Mannas
 */
public class Ebook {

    public Boolean full;
    public String preview_url;
    public String read_url;
    public String pdf_url;
    public String epub_url;
    public String text_urrl;
    public Ebook(){
        full = false;
    }



    public Ebook(Boolean full, String preview_url, String read_url, String pdf_url, String epub_url, String text_urrl) {
        this.full = full;
        this.preview_url = preview_url;
        this.read_url = read_url;
        this.pdf_url = pdf_url;
        this.epub_url = epub_url;
        this.text_urrl = text_urrl;
    }
}
