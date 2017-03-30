package com.example.mvp.medialabsapplication.View;

import com.example.mvp.medialabsapplication.Model.Follower;

import java.util.ArrayList;

/**
 * Created by mausamkumari on 2/19/17.
 */

public interface FollowerMvpView extends MvpView {

    void showFollowers(ArrayList<Follower> followerList);

    void showMessage(int stringId);

    void showProgressIndicator();
}
