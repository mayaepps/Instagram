package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = "FeedActivity";
    private static final int POST_LIMIT = 20;
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        rvPosts = findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();

        // initialize the array that will hold posts and create a PostsAdapter
        adapter = new PostsAdapter(this, allPosts);

        // Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // Set a Layout Manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        // Get posts from Parse
        queryPosts();

    }

    // Get posts from Parse
    private void queryPosts() {
        // specify what type of data to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);

        // limit query to latest 20 items
        query.setLimit(POST_LIMIT);

        // order posts by creation date (newest first) (TODO: figure this out)
        // query.addDescendingOrder(Post.KEY_CREATED_KEY);

        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // For now to help with debugging: print every post description to logcat
                //TODO: remove when not needed anymore
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                // save received posts to the list and notify the adapter of this new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}