package com.example.mvp.medialabsapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mvp.medialabsapplication.Model.Follower;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mausamkumari on 2/19/17.
 */

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {

    private ArrayList<Follower> followers;
    private Callback callback;

    public FollowerAdapter() {
        followers = new ArrayList<>();
    }

    public void setFollowers(ArrayList<Follower> followers) {
        this.followers = followers;
    }

    public void setCallback(FollowerAdapter.Callback callback) {
        this.callback = callback;
    }

    @Override
    public FollowerAdapter.FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_follower, parent, false);
        final FollowerAdapter.FollowerViewHolder viewHolder = new FollowerAdapter.FollowerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FollowerAdapter.FollowerViewHolder holder, int position) {
        final Follower follower = followers.get(position);
        Context context = holder.imageView.getContext();
        holder.follower = follower;
        holder.textView.setText(follower.name);
        if (!TextUtils.isEmpty(follower.avatarUrl)) {
            Picasso.with(context).load(follower.avatarUrl).placeholder(R.drawable.placeholder).into(holder.imageView);
        }

        holder.follower_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick(holder.follower, follower.url);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public static class FollowerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout follower_layout;
        public Follower follower;
        public FollowerViewHolder(View view) {
            super(view);
            follower_layout = (RelativeLayout)view.findViewById(R.id.follower_layout);
            imageView = (ImageView) follower_layout.findViewById(R.id.follower_image);
            textView = (TextView) follower_layout.findViewById(R.id.follower_name);
        }
    }

    public interface Callback {
        void onItemClick(Follower follower, String userURL);
    }
}
