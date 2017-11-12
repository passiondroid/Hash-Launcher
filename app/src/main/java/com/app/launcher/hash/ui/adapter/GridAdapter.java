package com.app.launcher.hash.ui.adapter;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.launcher.hash.R;
import com.app.launcher.hash.data.model.App;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by arif on 04/11/17.
 */

public class GridAdapter extends BaseAdapter {

    private List<App> apps;

    public GridAdapter(List<App> apps) {
        this.apps = apps;
    }

    private ViewHolder holder;

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int i) {
        return apps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        holder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            convertView  = inflater.inflate(R.layout.item,null);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.icon);
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Drawable drawable = null;
        try {
            drawable = viewGroup.getContext().getPackageManager().getApplicationIcon(apps.get(i).getPckgName());
            holder.icon.setImageDrawable(drawable);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MainFragment","Exception", e);
        }finally {
            if(drawable == null) {
                Drawable defaultDrawable = ContextCompat.getDrawable(viewGroup.getContext(), R.mipmap.ic_launcher_round);
                holder.icon.setImageDrawable(defaultDrawable);
            }
        }

        holder.name.setText(apps.get(i).getName());
        return convertView;
    }

    class ViewHolder{
        ImageView icon;
        TextView name;
    }
}
