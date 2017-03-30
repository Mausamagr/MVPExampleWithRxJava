package com.example.mvp.medialabsapplication.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvp.medialabsapplication.Model.FollowerDetail;
import com.example.mvp.medialabsapplication.Presenter.FollowerDetailPresenter;
import com.example.mvp.medialabsapplication.R;
import com.squareup.picasso.Picasso;


public class FollowerDetailActivity extends AppCompatActivity implements FollowerDetailMvpView{

    private TextView title, subtitle;
    private String username;
    private ProgressBar mProgressbar;
    private ImageView mImageView;
    private ImageView imageBg;
    private TextView location, email;
    private TextView repoCount, follower, following;
    private FollowerDetailPresenter mFollowerDetailPresenter;
    private FollowerDetail followerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_detail);
        initializeViews();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            username = bundle.getString("userName");
        }

        mFollowerDetailPresenter = new FollowerDetailPresenter();
        mFollowerDetailPresenter.attachView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        if(savedInstanceState != null) {
            followerDetail = savedInstanceState.getParcelable("followerData");
            showFollowerDetail(followerDetail);
        } else {
            mFollowerDetailPresenter.loadFollowerDetail(username);
        }
    }

    public void initializeViews() {
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);
        mImageView = (ImageView) findViewById(R.id.img_logo);
        imageBg = (ImageView)findViewById(R.id.img);
        location = (TextView) findViewById(R.id.location);
        email = (TextView) findViewById(R.id.email);
        repoCount = (TextView)findViewById(R.id.repo_number);
        follower = (TextView)findViewById(R.id.follower_number);
        following = (TextView)findViewById(R.id.following_number);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("followerData", followerDetail);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void showProgressIndicator() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFollowerDetail(FollowerDetail followerDetails) {
        this.followerDetail = followerDetails;
        mProgressbar.setVisibility(View.GONE);
        Picasso.with(getContext()).load(followerDetails.avatar_url).placeholder(R.drawable.placeholder).into(imageBg);
        Picasso.with(getContext()).load(followerDetails.avatar_url).placeholder(R.drawable.placeholder).into(mImageView);
        title.setText(followerDetails.login);
        subtitle.setText(followerDetails.name);
        if(followerDetails.location != null && !TextUtils.isEmpty(followerDetails.location)) {
            location.setText(followerDetails.location);
            location.setVisibility(View.VISIBLE);
        }
        if(followerDetails.email != null && !TextUtils.isEmpty(followerDetails.email)) {
            email.setText(followerDetails.email);
            email.setVisibility(View.VISIBLE);
        }
        repoCount.setText(String.valueOf(followerDetails.public_repos));
        follower.setText(String.valueOf(followerDetails.followers));
        following.setText(String.valueOf(followerDetails.following));
    }

    @Override
    public Context getContext() {
        return this;
    }
}
