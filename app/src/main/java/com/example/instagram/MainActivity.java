package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.instagram.Fragments.ComposeFragment;
import com.example.instagram.Fragments.PostsFragment;
import com.example.instagram.Models.Post;
import com.example.instagram.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        bottomNavigationView = findViewById(R.id.bottomNavigation);


        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            // Todo: Update fragment
                            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            fragment = new PostsFragment();
                            break;
                        case R.id.action_compose:
                            Toast.makeText(MainActivity.this, "compose", Toast.LENGTH_SHORT).show();
                            fragment = new ComposeFragment();
                            break;
                        case R.id.action_profile:
                            //Todo: update profile
                            Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                            fragment = new ComposeFragment();
                        default:
                            fragment = new ComposeFragment();
                            break;
                    }
                // Switch out the frame layout with the specified fragment
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit(); // commit means make it happen
                    return true;
                }
            });

            // Set default selection so when the app loads for the first time, it will have a fragment loaded
            bottomNavigationView.setSelectedItemId(R.id.action_home);

    }
}