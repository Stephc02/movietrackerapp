package com.example.nav_drawer;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;

import com.example.nav_drawer.ui.discover.DiscoverFragment;
import com.example.nav_drawer.ui.login.LoginFragment;
import com.example.nav_drawer.ui.movies.MoviesFragment;
import com.example.nav_drawer.ui.profile.EditProfileFragment;
import com.example.nav_drawer.ui.profile.ProfileFragment;
import com.example.nav_drawer.ui.signup.SignUpFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nav_drawer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener  {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private String username;
    SignUpFragment fragment_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
       NavigationView navigationView = binding.navView;
        //NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView navBottomView = findViewById(R.id.bottom_nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_login, R.id.nav_signup,
                R.id.navigation_dashboard, R.id.navigation_profile).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(navBottomView, navController);

        navBottomView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                popFragment();
                if  (item.getItemId()== R.id.navigation_profile){
                    replaceFragment(new ProfileFragment());
                    Bundle bundle = new Bundle();
                    bundle.putString("username", getUsername());
                    Log.d(TAG, "getUsername inside menu click: "+  username);
                    getSupportFragmentManager().setFragmentResult("userdata",bundle);
                }
                else if (item.getItemId()== R.id.navigation_dashboard){
                    replaceFragment(new DiscoverFragment());

                }
                else if (item.getItemId()== R.id.nav_home){
                    replaceFragment(new MoviesFragment());

                }
               // else if (item.getItemId()== R.id.navigation_editprofile){
               //     replaceFragment(new EditProfileFragment());

                //}

                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void sendUsername(String username) {

        Log.d(TAG, "sendUsername: "+  username);
        this.username=username;

    }
    public String getUsername() {
        return this.username;
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment,null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    public void popFragment(){
        if (getSupportFragmentManager()==null)
            return;
        getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount()>1)
        {
            popFragment();
        }
    }
}

