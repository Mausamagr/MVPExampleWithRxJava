package com.example.mvp.medialabsapplication.Presenter;

import android.util.Log;

import com.example.mvp.medialabsapplication.GitApplication;
import com.example.mvp.medialabsapplication.Model.FollowerDetail;
import com.example.mvp.medialabsapplication.Model.GithubService;
import com.example.mvp.medialabsapplication.View.FollowerDetailMvpView;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by mausamkumari on 2/19/17.
 */

public class FollowerDetailPresenter implements Presenter<FollowerDetailMvpView> {

    public static final String TAG = "FollowerDetailPresenter";
    private FollowerDetailMvpView mFollowerDetailMvpView;
    private Subscription subscription;

    @Override
    public void attachView(FollowerDetailMvpView view) {
        this.mFollowerDetailMvpView = view;
    }

    @Override
    public void detachView() {
        if(subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void loadFollowerDetail(String userUrl) {
        GitApplication application = GitApplication.get(mFollowerDetailMvpView.getContext());
        GithubService githubService = application.getGithubService();

        mFollowerDetailMvpView.showProgressIndicator();
        subscription = githubService.followerDetail(userUrl)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(application.defaultSubscribeScheduler())
                            .subscribe(new Action1<FollowerDetail>() {
                                @Override
                                public void call(FollowerDetail followerDetail) {
                                    mFollowerDetailMvpView.showFollowerDetail(followerDetail);
                                }
                            });
    }
}
