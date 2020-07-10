package com.example.instagram.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.instagram.LoginActivity;
import com.example.instagram.Models.Post;
import com.example.instagram.R;
import com.google.android.material.button.MaterialButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.zip.Inflater;

public class ProfileFragment extends PostsFragment {

    private static final String TAG = "ProfileFragment";
    private MaterialButton btnLogOut;

    @Override
    protected void queryPosts() {
        // specify what type of data to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);

        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

        // order posts by creation date (newest first) (TODO: figure this out)
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

                // Remove old data
                allPosts.clear();
                // save received posts to the list and notify the adapter of this new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                // Notify swipeContainer the refresh is over
                swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    protected void logOutButtonVisibility(View view) {
        MaterialButton btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setVisibility(View.VISIBLE);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
