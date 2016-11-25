package com.example.mannas.book_e.App_Data;

import java.util.ArrayList;

/**
 * Created by Mannas
 */
public class Author {
    public String name;
    public String url;

    public Author(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public Author(  ) {

    }

    public static ArrayList<String> getNames(ArrayList<Author> authors){
        ArrayList<String> names = new ArrayList<>();
        for(int i=0,j=authors.size();i<j;i++){
             names.add(authors.get(i).name);
        }
        return names;
    }
}
