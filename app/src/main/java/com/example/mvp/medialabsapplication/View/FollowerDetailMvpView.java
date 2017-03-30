package com.example.mvp.medialabsapplication.View;

import com.example.mvp.medialabsapplication.Model.FollowerDetail;


/**
 * Created by mausamkumari on 2/19/17.
 */

public interface FollowerDetailMvpView extends MvpView {
    void showProgressIndicator();
    void showFollowerDetail(FollowerDetail followerDetails);

}