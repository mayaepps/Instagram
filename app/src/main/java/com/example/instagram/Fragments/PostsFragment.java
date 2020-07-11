package com.example.instagram.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.instagram.Adapters.PostsAdapter;
import com.example.instagram.EndlessRecyclerViewScrollListener;
import com.example.instagram.Models.Post;
import com.example.instagram.PostDetailsActivity;
import com.example.instagram.R;
import com.google.android.material.button.MaterialButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {

    private static final String TAG = "PostsFragment";
    protected SwipeRefreshLayout swipeContainer;
    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;

    public static final int POST_LIMIT = 20;
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private EndlessRecyclerViewScrollListener scrollListener;

    public PostsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutButtonVisibility(view);

        // Show loading progress bar
        view.findViewById(R.id.pbLoading).setVisibility(ProgressBar.VISIBLE);

        rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();


        //Instantiate my OnClickListener from the interface in TweetsAdapter
        PostsAdapter.OnClickListener clickListener = new PostsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {

                Intent i = new Intent(getContext(), PostDetailsActivity.class);
                i.putExtra(Post.class.getSimpleName(), allPosts.get(position));
                startActivity(i);
            }
        };

        // initialize the array that will hold posts and create a PostsAdapter
        adapter = new PostsAdapter(getContext(), allPosts, clickListener);

        // Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        // Set a Layout Manager on the recycler view
        rvPosts.setLayoutManager(layoutManager);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts(0);
            }
        });

        // Get posts from Parse
        queryPosts(0);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                queryPosts(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);

    }

    // Get posts from Parse
    protected void queryPosts(final int page) {
        // specify what type of data to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        query.setSkip(POST_LIMIT*page);

        // limit query to latest 20 items
        query.setLimit(POST_LIMIT);

        // order posts by creation date (newest first)
        query.addDescendingOrder(Post.KEY_CREATED_AT);

        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                if (page == 0) {
                    // Remove old data
                    allPosts.clear();
                    adapter.clear();
                }

                // save received posts to the list and notify the adapter of this new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                // Notify swipeContainer the refresh is over
                swipeContainer.setRefreshing(false);
                // Show loading progress bar
                getView().findViewById(R.id.pbLoading).setVisibility(ProgressBar.VISIBLE);
            }
        });
    }

    protected void logOutButtonVisibility(View view) {
        MaterialButton btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setVisibility(View.GONE);
    }
}