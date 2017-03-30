package com.example.mvp.medialabsapplication.View;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp.medialabsapplication.FollowerAdapter;
import com.example.mvp.medialabsapplication.Model.Follower;
import com.example.mvp.medialabsapplication.Presenter.FollowerPresenter;
import com.example.mvp.medialabsapplication.R;

import java.util.ArrayList;

public class FollowerActivity extends AppCompatActivity implements FollowerMvpView {

    private FollowerPresenter mFollowerPresenter;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SearchView searchView;
    private TextView infoTextView;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Follower> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);

        mFollowerPresenter = new FollowerPresenter();
        mFollowerPresenter.attachView(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.follower_recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        infoTextView = (TextView) findViewById(R.id.error_text);
        infoTextView.setText(getString(R.string.welcome_text));
        setUpRecyclerView(mRecyclerView);
        if(savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList("data");
            if(list != null && !list.isEmpty()) {
                showFollowers(list);
            }
        }

    }

    private void setUpRecyclerView(RecyclerView mRecyclerView) {
        FollowerAdapter adapter = new FollowerAdapter();
        adapter.setCallback(new FollowerAdapter.Callback() {
            @Override
            public void onItemClick(Follower follower, String userURL) {
                Toast.makeText(FollowerActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FollowerActivity.this, FollowerDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", userURL);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpaceRecyclerView(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("data", list);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    infoTextView.setText(getString(R.string.welcome_text));
                    return false;
                }
            });

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    infoTextView.setText(getString(R.string.welcome_text));
                }
            });

            EditText searchText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchText.setHint("User Name");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mFollowerPresenter.loadFollowers(query);
                    hideSoftKeyboard();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    infoTextView.setText(getString(R.string.welcome_text));
                    return true;
                }
            });

            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        }


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void showFollowers(ArrayList<Follower> followerList) {
        FollowerAdapter adapter = (FollowerAdapter) mRecyclerView.getAdapter();
        list = followerList;
        adapter.setFollowers(followerList);
        adapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        infoTextView.setText(getString(R.string.follower_list_title));
    }

    @Override
    public void showMessage(int stringId) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        infoTextView.setText(getString(stringId));
    }

    @Override
    public void showProgressIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    class GridSpaceRecyclerView extends RecyclerView.ItemDecoration {
        private int mGridSpace;

        public GridSpaceRecyclerView(Context context) {
            mGridSpace = (int) context.getResources().getDimensionPixelSize(R.dimen.horizontal_margin_grid);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mGridSpace, mGridSpace, mGridSpace, mGridSpace);
        }
    }
}
