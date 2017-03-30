package com.example.mvp.medialabsapplication.Presenter;

import android.util.Log;

import com.example.mvp.medialabsapplication.GitApplication;
import com.example.mvp.medialabsapplication.Model.Follower;
import com.example.mvp.medialabsapplication.Model.GithubService;
import com.example.mvp.medialabsapplication.R;
import com.example.mvp.medialabsapplication.View.FollowerMvpView;

import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mausamkumari on 2/19/17.
 */

public class FollowerPresenter implements Presenter<FollowerMvpView> {

    public static final String TAG = "FollowerPresenter";
    private FollowerMvpView followerMvpView;
    private Subscription subscription;
    private ArrayList<Follower> followerList;

    @Override
    public void attachView(FollowerMvpView view) {
        this.followerMvpView = view;

    }

    @Override
    public void detachView() {
        this.followerMvpView = null;
        if(this.subscription != null) {
           subscription.unsubscribe();
        }
    }

    public void loadFollowers(String usernameEntered) {
        String username = usernameEntered.trim();
        if (username.isEmpty()) return;

        followerMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        GitApplication application = GitApplication.get(followerMvpView.getContext());
        GithubService githubService = application.getGithubService();
        subscription = githubService.followers(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<ArrayList<Follower>>() {
                    @Override
                    public void onCompleted() {
                        if (!followerList.isEmpty()) {
                            followerMvpView.showFollowers(followerList);
                        } else {
                            followerMvpView.showMessage(R.string.no_follower_text);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading followers ", error);
                        if (isHttp404(error)) {
                            followerMvpView.showMessage(R.string.error_username_not_found);
                        } else {
                            followerMvpView.showMessage(R.string.error_loading_follower);
                        }
                    }

                    @Override
                    public void onNext(ArrayList<Follower> followers) {
                        FollowerPresenter.this.followerList = followers;
                    }
                });
    }

    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
