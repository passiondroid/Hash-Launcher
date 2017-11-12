package com.app.launcher.hash.ui.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.launcher.hash.R;
import com.app.launcher.hash.data.model.App;
import com.app.launcher.hash.data.model.Article;
import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.ui.interfaces.OnListFragmentInteractionListener;
import com.app.launcher.hash.util.AppsDiffUtilCallback;
import com.app.launcher.hash.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.realm.RealmResults;

/**
 * Created by arif on 07/11/17.
 */

public class SearchRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<App> apps;
    private OnListFragmentInteractionListener listFragmentInteractionListener;

    public SearchRVAdapter(List<App> apps) {
        this.apps = apps;
    }

    private static final String TAG = "SearchRVAdapter";

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.app_rv_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setListFragmentInteractionListener(OnListFragmentInteractionListener listFragmentInteractionListener) {
        this.listFragmentInteractionListener = listFragmentInteractionListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        final App app = apps.get(position);
        viewHolder.nameTV.setText(app.getName());

//        Glide.with(viewHolder.itemView.getContext())
//                .load(article.getUrlToImage())
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .crossFade()
//                .into(viewHolder.articleIV);
        viewHolder.iconIV.setImageDrawable(loadIcon(viewHolder.itemView.getContext(),app.getPckgName()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listFragmentInteractionListener.onListFragmentInteraction(view,app,position);
            }
        });
    }

    public void refreshItems(List<App> appsNew){
//        List<App> newAppList = new ArrayList<>();
//        newAppList.addAll(appsNew);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AppsDiffUtilCallback(this.apps, appsNew));
        this.apps = appsNew;
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    private Drawable loadIcon(Context context, String packageName) {
        try {
            if (packageName != null) {
                Drawable icon = context.getPackageManager().getApplicationIcon(packageName);
                return icon;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception", e);
        }
        return ContextCompat.getDrawable(context, R.mipmap.ic_launcher_round);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView iconIV;
        public final TextView nameTV;

        public ViewHolder(View itemView) {
            super(itemView);
            iconIV = itemView.findViewById(R.id.iconIV);
            nameTV = itemView.findViewById(R.id.appNameTV);

        }
    }
}
