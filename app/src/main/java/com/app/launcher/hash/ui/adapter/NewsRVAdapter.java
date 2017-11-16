package com.app.launcher.hash.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.launcher.hash.R;
import com.app.launcher.hash.data.model.Article;
import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.ui.interfaces.OnListFragmentInteractionListener;
import com.app.launcher.hash.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by arif on 07/11/17.
 */

public class NewsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private NewsArticles newsArticles;
    private OnListFragmentInteractionListener listFragmentInteractionListener;

    public NewsRVAdapter(NewsArticles newsArticles, OnListFragmentInteractionListener listFragmentInteractionListener) {
        this.newsArticles = newsArticles;
        this.listFragmentInteractionListener = listFragmentInteractionListener;
    }

    private static final String TAG = "NewsRVAdapter";

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_news,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        final Article article = newsArticles.getArticles().get(position);
        viewHolder.agencyTV.setText(article.getAuthor());
        viewHolder.descTV.setText(article.getDescription());

        Glide.with(viewHolder.itemView.getContext())
                .load(article.getUrlToImage())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .into(viewHolder.articleIV);

        if(newsArticles.getSource().equalsIgnoreCase(Constants.TECHCRUNCH)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.techcrunch_logo)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.sourceIV);
        }else if(newsArticles.getSource().equalsIgnoreCase(Constants.ALJAZEERA)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.al_jazeera)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.sourceIV);
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp published = getPublishedTimeStamp(article.getPublishedAt());
        if(published != null) {
            CharSequence timeSpan = DateUtils.getRelativeTimeSpanString(published.getTime(), now.getTime(), 0);
            viewHolder.timeTV.setText(timeSpan);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listFragmentInteractionListener.onListFragmentInteraction(view,article,position);
            }
        });
    }

    private Timestamp getPublishedTimeStamp(String date) {
        Timestamp timestamp = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parsedDate = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            Log.e(TAG,e.getMessage(),e);
            // look the origin of excption
        }
        return timestamp;
    }

    @Override
    public int getItemCount() {
        return newsArticles.getArticles().size();
    }

    public void clear() {
        newsArticles.getArticles().clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void setArtciles(NewsArticles newsArticles) {
        this.newsArticles = newsArticles;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView articleIV, sourceIV;
        public final TextView timeTV, agencyTV, descTV;

        public ViewHolder(View itemView) {
            super(itemView);
            articleIV = itemView.findViewById(R.id.articleIV);
            sourceIV = itemView.findViewById(R.id.sourceIV);
            timeTV = itemView.findViewById(R.id.timeTV);
            agencyTV = itemView.findViewById(R.id.newsAgencyTV);
            descTV = itemView.findViewById(R.id.descTV);

        }
    }
}
