package com.example.nav_drawer.ui.profile;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav_drawer.DBHelper;
import com.example.nav_drawer.R;

import com.example.nav_drawer.databinding.FragmentProfileBinding;
import com.example.nav_drawer.ui.login.LoginFragment;

import java.util.ArrayList;

public class ProfileFragment extends Fragment  {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;
    private TextView fullname, gender,country, num_watched;
    EditText  username;
    DBHelper db;
    Button btne;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        username=root.findViewById(R.id.username1);
        fullname=root.findViewById(R.id.fullname1);
        gender=root.findViewById(R.id.gender1);
        country=root.findViewById(R.id.country1);
        num_watched=root.findViewById(R.id.nummovieswatched1);
        String user = (String) username.getText().toString();
        String fname = (String) fullname.getText().toString();
        String gend= (String) gender.getText().toString();
        String place= (String) country.getText().toString();
        String num_movies = (String) num_watched.getText().toString();
        Toast.makeText(getActivity(),fname,Toast.LENGTH_SHORT).show();

        getParentFragmentManager().setFragmentResultListener("userdata", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String user=result.getString("username");
                db=new DBHelper(getActivity());
                Cursor cursor;
                cursor=db.getRecord(user);

                Log.d(TAG, "cursor number: " + cursor.getCount());
                if (cursor.getCount()==0){
                    Toast.makeText(getActivity(),"failed to find profile",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "cursor did not return anything: ");
                }
                ArrayList<String> arr_list = new ArrayList<String>();

                cursor.moveToFirst();
                if (!cursor.isAfterLast()) {
                    @SuppressLint("Range") String fname = cursor.getString(cursor.getColumnIndex("fullname"));
                    Log.d(TAG, "fullname : " + fname);
                    arr_list.add(fname);
                    @SuppressLint("Range") String gend = cursor.getString(cursor.getColumnIndex("gender"));
                    Log.d(TAG, "gender : " + gend);
                    //@SuppressLint("Range") String place = cursor.getString(cursor.getColumnIndex("country"));
                    Log.d(TAG, "country : " + place);
                    @SuppressLint("Range") String num_moviesw = cursor.getString(cursor.getColumnIndex("Movies_watched"));
                    fullname.setText(fname);
                    gender.setText(gend);
                    //country.setText(place);
                    num_watched.setText(num_moviesw);
                    username.setText(user);
                }
                cursor.close();
            } });

        btne=root.findViewById(R.id.btnEdit);
        btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname.setVisibility(View.GONE);
                btne.setVisibility(View.GONE);

                Fragment editprofileFragment = new EditProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", user);
                bundle.putString("name", fname);
                bundle.putString("gender", gend);
                bundle.putString("country", place);
                bundle.putString("num_movies", num_movies);
                popFragment();
                //editprofileFragment.setArguments(bundle);
                getParentFragmentManager().setFragmentResult("data",bundle);
                replaceFragment(editprofileFragment);


                //Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_editprofile);
            }
        });
        return root;

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment,null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
    public void popFragment(){
        if (getParentFragmentManager()==null)
            return;
        getParentFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void onBackPressed(){
        if (getParentFragmentManager().getBackStackEntryCount()>1)
        {
            popFragment();
        }
    }
}