package com.example.instagram.Fragments;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.instagram.LoginActivity;
import com.example.instagram.Models.Post;
import com.example.instagram.R;
import com.google.android.material.button.MaterialButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    private static final String TAG = "ProfileFragment";
    private MaterialButton btnLogOut;

    @Override
    protected void queryPosts(final int page) {
        // specify what type of data to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        query.setSkip(PostsFragment.POST_LIMIT*page);

        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

        // order posts by creation date (newest first)
        query.addDescendingOrder(Post.KEY_CREATED_AT);

        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts: " + e.getMessage(), e);
                    return;
                }

                if (page == 0) {
                    // Remove old data only if loading posts from the beginning (not EndlessScrollListener)
                    allPosts.clear();
                }

                // save received posts to the list and notify the adapter of this new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                // Notify swipeContainer the refresh is over
                swipeContainer.setRefreshing(false);
            }
        });
    }


    // Called in onViewCreated, shows & sets onClickListener on the log out button
    @Override
    protected void logOutButtonVisibility(View view) {
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setVisibility(View.VISIBLE);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                // Stop the user from tapping back and seeing the old screen/activity
                getActivity().finish();
            }
        });
    }
}
