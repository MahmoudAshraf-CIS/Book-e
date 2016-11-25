package com.example.mannas.book_e.App_UI.FragmentHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mannas.book_e.App_Data.BookView;
import com.example.mannas.book_e.App_UI.BookDetailsActivity;
import com.example.mannas.book_e.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mannas
 */



public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.BookHolder> {
    ArrayList<BookView> bookViews;


    public BooksRecyclerAdapter() {
        this.bookViews = new ArrayList<>();
    }

    public BooksRecyclerAdapter(ArrayList<BookView> bookViews) {
        this.bookViews = bookViews;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.book_card2, parent, false);
        return new BookHolder(view);
    }

    @Override
    public int getItemCount() {
        return bookViews==null?0:bookViews.size();
    }

    @Override
    public void onBindViewHolder(final BookHolder holder,final int position) {
        Picasso.with(holder.itemView.getContext())
                .load("http://covers.openlibrary.org/b/olid/"+ bookViews.get(position).getOL_key_M() +"-M.jpg")
                 .placeholder(R.drawable.book)
                .into(holder.ibook);
        holder.title.setText(bookViews.get(position).getTitle());
        holder.author.setText( "By :- "+ bookViews.get(position).getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"Details ...",Toast.LENGTH_SHORT).show(); //// TODO: 25/11/2016
                Intent i =new Intent(holder.itemView.getContext(), BookDetailsActivity.class);
                Bundle b = new Bundle();
                b.putString("olid",bookViews.get(position).getOL_key_M());

                i.putExtras(b);

                holder.itemView.getContext().startActivity(i);
            }
        });
        if(!bookViews.get(position).getHas_fulltext()){
            holder.has_fullText.setVisibility(View.GONE);
        }else{
            holder.has_fullText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(holder.itemView.getContext(),"Downloading ...",Toast.LENGTH_SHORT).show(); //// TODO: 25/11/2016
                }
            });
        }
    }

    public class BookHolder extends RecyclerView.ViewHolder{
        ImageView ibook;
        TextView title;
        TextView author;
        LinearLayout has_fullText;
        public BookHolder(View itemView) {
            super(itemView);
            ibook = (ImageView) itemView.findViewById(R.id.book_card);
            title = (TextView) itemView.findViewById(R.id.title_book_card);
            author= (TextView) itemView.findViewById(R.id.author_book_card);
            has_fullText = (LinearLayout) itemView.findViewById(R.id.has_full_txt);

        }
    }
    public void NotifyDataSetChanged(ArrayList<BookView> bookViews){
        super.notifyDataSetChanged();
        this.bookViews = bookViews;
    }
    public void addToDataSet(ArrayList<BookView> bookViews){
        super.notifyDataSetChanged();
        this.bookViews.addAll(bookViews);
    }
    public Integer getDataSetSize(){
        return bookViews==null?0:bookViews.size();
    }

}
