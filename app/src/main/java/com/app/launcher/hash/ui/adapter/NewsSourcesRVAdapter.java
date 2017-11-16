package com.app.launcher.hash.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.launcher.hash.R;
import com.app.launcher.hash.data.model.NewsSource;
import com.app.launcher.hash.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by arif on 07/11/17.
 */

public class NewsSourcesRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsSource> newsSources;

    public NewsSourcesRVAdapter(List<NewsSource> newsSources) {
        this.newsSources = newsSources;
    }

    private static final String TAG = "NewsSourcesRVAdapter";

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_topic_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.titleTV.setText(newsSources.get(position).getName());
        System.out.println("NewsSource: "+newsSources.get(position));

        if(newsSources.get(position).getName().equalsIgnoreCase(Constants.ALJAZEERA_ENGLISH)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.al_jazeera)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        } else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.ASSOCIATED_PRESS)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.ap)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.BBC_NEWS)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.bbc)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.BLOOMBERG)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.bloomberg)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.CNN)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.cnn)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.GOOGLE_NEWS)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.gn)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.REUTERS)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.reuters)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.REDDIT_ALL)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.reddit)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.THE_HINDU)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.th)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.THE_HUFFINGTON_POST)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.hp)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.THE_NEW_YORK_TIMES)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.nyt)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.THE_WASHINGTON_POST)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.wp)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.THE_WALL_STREET_JOURNAL)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.wsj)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.TIME)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.time)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.USA_TODAY)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.usatoday)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.ENTERTAINMENT_WEEKLY)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.ew)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.MTV_NEWS)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.mtv)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.BUZZFEED)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.buzfeed)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.ARS_TECHNICA)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.arstech)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.HACKER_NEWS)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.hn)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.MASHABLE)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.mashable)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.TECH_CRUNCH)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.tc)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.RECODE)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.recode)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.THE_NEXT_WEB)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.tnw)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }else if(newsSources.get(position).getName().equalsIgnoreCase(Constants.THE_VERGE)){
            Glide.with(viewHolder.itemView.getContext())
                    .load(R.drawable.verge)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(viewHolder.newsIV);
        }
    }

    @Override
    public int getItemCount() {
        return newsSources.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView newsIV;
        public final TextView titleTV;

        public ViewHolder(View itemView) {
            super(itemView);
            newsIV = itemView.findViewById(R.id.newsIV);
            titleTV = itemView.findViewById(R.id.newsTV);

        }
    }
}
