package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.instagram.Adapters.PostsAdapter;
import com.example.instagram.Models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = "FeedActivity";
    private SwipeRefreshLayout swipeContainer;
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


        //Instantiate my OnClickListener from the interface in TweetsAdapter
        PostsAdapter.OnClickListener clickListener = new PostsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {

                Intent i = new Intent(FeedActivity.this, PostDetailsActivity.class);
                i.putExtra(Post.class.getSimpleName(), allPosts.get(position));
                startActivity(i);
            }
        };

        // initialize the array that will hold posts and create a PostsAdapter
        adapter = new PostsAdapter(this, allPosts, clickListener);

        // Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // Set a Layout Manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                queryPosts();
            }
        });

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
}