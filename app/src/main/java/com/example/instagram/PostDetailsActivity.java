package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.instagram.Models.Post;
import com.example.instagram.databinding.ActivityDetailPostBinding;
import com.parse.ParseFile;

//
public class PostDetailsActivity extends AppCompatActivity {

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        ActivityDetailPostBinding binding =  ActivityDetailPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Get the post that was clicked on and use it to set the details of the views
        post = getIntent().getParcelableExtra(Post.class.getSimpleName());

        binding.tvUsername.setText(post.getUser().getUsername());
        binding.tvDescription.setText(post.getDescription());
        binding.tvCaptionUsername.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(binding.ivImage);
        }
        binding.tvTimestamp.setText(post.getCreatedAt().toString());
    }
}