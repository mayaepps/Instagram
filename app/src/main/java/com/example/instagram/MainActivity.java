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

    private ActivityMainBinding binding;

    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        // Main logic for which screen appears
        // Switches the fragment showing in the activity when the respective menu item is tapped in the bottom navigation bar
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
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit(); // commit means "make it happen now"
                    return true;
                }
            });

            // Set default selection so when the app loads for the first time, it will have a fragment loaded
            binding.bottomNavigation.setSelectedItemId(R.id.action_home);
    }
}