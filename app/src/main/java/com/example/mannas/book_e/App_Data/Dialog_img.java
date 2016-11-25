package com.example.mannas.book_e.App_Data;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mannas.book_e.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Mannas
 */
public class Dialog_img extends DialogFragment {
    ImageView cover_img;
    public static String OL_key_M;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                .setIcon(android.R.drawable.stat_notify_error)
                // set Dialog Title
                .setTitle("Alert dialog fragment example")
                // Set Dialog Message
                .setMessage("This is a message").create();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.cover,container,false);
        cover_img = (ImageView) root.findViewById(R.id.soe_id);
        Picasso.with( getContext())
                .load("http://covers.openlibrary.org/b/olid/"+  OL_key_M +"-M.jpg")
                .placeholder(R.drawable.book)
                .into(cover_img);
        return  root;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}
