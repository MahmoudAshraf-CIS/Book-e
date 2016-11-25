package com.example.mannas.book_e.App_UI.FragmentHome;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mannas.book_e.App_Data.Author;
import com.example.mannas.book_e.App_Data.BookDataLoader_online;
import com.example.mannas.book_e.App_Data.BookDetail;
import com.example.mannas.book_e.App_Data.Dialog_img;
import com.example.mannas.book_e.App_Data.Utility;
import com.example.mannas.book_e.App_UI.ExploreActivity;
import com.example.mannas.book_e.App_UI.TagView_pro;
import com.example.mannas.book_e.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by Mannas
 */
public class BookDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<BookDetail> {

    String OLID_test = "OL25605634M";
    public static final String OLID_key = "olid";
    int BookDataLoader_ID =0;

    BookDetail bookDetail;


    TextView book_title,number_of_pages,publish_details;
    ImageView pdf,epub,txt,cover;
    TagContainerLayout authors_tagView;
    CardView Preview,ReadOnline;
    RelatedTagsAdapter tagsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagsAdapter = new RelatedTagsAdapter();

        Bundle b = getActivity().getIntent().getExtras();
        getLoaderManager().initLoader(BookDataLoader_ID,b,this).forceLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.book_detail_frag,container,false);

        cover = (ImageView) rootView.findViewById(R.id.cover_img);
        book_title = (TextView) rootView.findViewById(R.id.book_title);
        number_of_pages = (TextView) rootView.findViewById(R.id.number_of_pages);
        publish_details = (TextView) rootView.findViewById(R.id.publish_details);


        //authours
        authors_tagView = (TagContainerLayout) rootView.findViewById(R.id. detail_authors_tagview);
        authors_tagView.setBackgroundColor(Color.TRANSPARENT);
        authors_tagView.setBorderColor(Color.TRANSPARENT);
        authors_tagView.setTagBackgroundColor(R.color.colorPrimary);
        authors_tagView.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                String aut_url = bookDetail.authors.get(position).url;
                String name = bookDetail.authors.get(position).name;
                Toast.makeText(getContext(),Integer.toHexString(position)+"  "+ text +aut_url ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTagLongClick(int position, String text) {}
            @Override
            public void onTagCrossClick(int position) {}
        });

        //related tags
        RecyclerView tags_Recy = (RecyclerView) rootView.findViewById(R.id.related_tags);
        tags_Recy.setAdapter(tagsAdapter);
        tags_Recy.setLayoutManager(new StaggeredGridLayoutManager(1,0));
        //preview -- readOnline
        Preview = (CardView) rootView.findViewById(R.id.preview_link);
        ReadOnline = (CardView) rootView.findViewById(R.id.read_online_link);

        //download
        pdf = (ImageView) rootView.findViewById(R.id.pdf_download_btn);
        epub = (ImageView) rootView.findViewById(R.id.epub_download_btn);
        txt = (ImageView) rootView.findViewById(R.id.txt_download_btn);

        return rootView;
    }

    public void updateUI(){
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager manager = getFragmentManager();
//                Dialog_img idDialog = new Dialog_img();
//                Dialog_img.OL_key_M = bookDetail.OL_key_M;
//                idDialog.show(manager, "tag");
            }
        });
        Picasso.with( getContext())
                .load("http://covers.openlibrary.org/b/olid/"+  bookDetail.OL_key_M +"-M.jpg")
                .placeholder(R.drawable.book)
                .into(cover);
        book_title.setText(bookDetail.title);
        number_of_pages.setText(bookDetail.number_of_pages + " ~ time ");
        authors_tagView.setTags(Author.getNames(bookDetail.authors));
        String p_details = "Published "+bookDetail.publish_date +" By:- <font color=#777777>"+ Utility.separateWithComa(bookDetail.publishers) +"</font> "
                +" In "+Utility.separateWithComa(bookDetail.publish_places);
        publish_details.setText( Html.fromHtml(p_details) );
        tagsAdapter.ChangeDataSet(bookDetail.subjects);
        if(bookDetail.ebook.pdf_url!=""){
            pdf.setVisibility(View.VISIBLE);
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https
                    String url = bookDetail.ebook.pdf_url.substring(0,4)+bookDetail.ebook.pdf_url.substring(5);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }

        if(bookDetail.ebook.epub_url!=""){
            epub.setVisibility(View.VISIBLE);
            epub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https
                    String url = bookDetail.ebook.epub_url.substring(0,4)+bookDetail.ebook.epub_url.substring(5);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }
        if(bookDetail.ebook.text_urrl!=""){
            txt.setVisibility(View.VISIBLE);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https
                    String url = bookDetail.ebook.text_urrl.substring(0,4)+bookDetail.ebook.text_urrl.substring(5);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }
        if(bookDetail.ebook.preview_url!="") {
            Preview.setVisibility(View.VISIBLE);
            Preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = bookDetail.ebook.preview_url.substring(0, 4) + bookDetail.ebook.preview_url.substring(5);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            });
        }

        if(bookDetail.ebook.read_url!="") {
            ReadOnline.setVisibility(View.VISIBLE);
            ReadOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = bookDetail.ebook.read_url.substring(0, 4) + bookDetail.ebook.read_url.substring(5);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            });
        }

    }
















    @Override
    public Loader<BookDetail> onCreateLoader(int id, Bundle args) {
        if(id == BookDataLoader_ID){
            return new BookDataLoader_online(getContext(),args.getString(OLID_key));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<BookDetail> loader, BookDetail data) {
        if(loader.getId()==BookDataLoader_ID){
            if(data!=null) {
                bookDetail = data;
                updateUI();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<BookDetail> loader) {

    }




    /**************  Inner Classes *********/
    public class RelatedTagsAdapter extends RecyclerView.Adapter<RelatedTagsAdapter.TagHolder> {
        ArrayList<String> tags;

        public RelatedTagsAdapter( ) {
            this.tags = new ArrayList<>();
        }

        public RelatedTagsAdapter(ArrayList<String> tags) {
            this.tags = tags;
        }


        @Override
        public TagHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag,parent,false);

            return new TagHolder(view) ;
        }

        @Override
        public void onBindViewHolder(TagHolder holder,final int position) {
            holder.tagView.setText(tags.get(position));
            holder.tagView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getBaseContext(),ExploreActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExploreActivity.Subject_Key, Utility.reTag(tags.get(position)) );
                    intent.putExtras(bundle);
                    startActivity( intent );
                }
            });
        }

        @Override
        public int getItemCount() {
            return tags==null?0:tags.size();
        }

        public class TagHolder extends RecyclerView.ViewHolder{
            TextView tagView;
            public TagHolder(View itemView) {
                super(itemView);
                tagView = (TextView) itemView.findViewById(R.id.tag_view);
            }
        }

        public void ChangeDataSet(ArrayList<String> newData){
            super.notifyDataSetChanged();
            this.tags = newData;
        }
    }
}
