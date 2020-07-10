package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.example.instagram.Fragments.ComposeFragment;
import com.example.instagram.Fragments.PostsFragment;
import com.example.instagram.Fragments.ProfileFragment;
import com.example.instagram.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            fragment = new PostsFragment();
                            break;
                        case R.id.action_compose:
                            fragment = new ComposeFragment();
                            break;
                        case R.id.action_profile:
                        default:
                            fragment = new ProfileFragment();
                            break;
                    }
                // Switch out the frame layout with the specified fragment
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit(); // commit means make it happen
                    return true;
                }
            });

            bottomNavigationView = findViewById(R.id.bottomNavigation);

            // Set default selection so when the app loads for the first time, it will have a fragment loaded
            bottomNavigationView.setSelectedItemId(R.id.action_home);

    }

}