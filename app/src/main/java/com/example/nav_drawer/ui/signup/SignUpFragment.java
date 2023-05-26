package com.example.nav_drawer.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.nav_drawer.DBHelper;
import com.example.nav_drawer.MainActivity;
import com.example.nav_drawer.R;
import com.example.nav_drawer.databinding.FragmentSignupBinding;
import com.example.nav_drawer.ui.profile.EditProfileFragment;
import com.example.nav_drawer.ui.profile.ProfileFragment;

public class SignUpFragment extends Fragment {

    private FragmentSignupBinding binding;
    EditText username, password, retypepassword, fullname, gender, country, nummovies_watched;
    Button signup;
    DBHelper db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SignUpViewModel signupViewModel =
                new ViewModelProvider(this).get(SignUpViewModel.class);

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        username=root.findViewById(R.id.username);
        password=root.findViewById(R.id.password);
        retypepassword=root.findViewById(R.id.retypepassword);
        fullname=root.findViewById(R.id.username);
        gender=root.findViewById(R.id.gender);
        country=root.findViewById(R.id.country);
        nummovies_watched=root.findViewById(R.id.num_movies_watched);
        signup=root.findViewById(R.id.btnsignup);
        db = new DBHelper(getActivity());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                String repass=retypepassword.getText().toString();
                String fname=fullname.getText().toString();
                String gen=gender.getText().toString();
                String place=country.getText().toString();
                String num_watched=nummovies_watched.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("") || fullname.equals("") || gender.equals("") || country.equals("") || num_watched.equals(""))
                    Toast.makeText(getActivity(),"Please enter all fields",Toast.LENGTH_SHORT).show();
                else {
                    if(pass.equals(repass)) {
                        Boolean checkuser = db.checkUsername(user);
                        if (checkuser==false){
                            Boolean insert=db.insertRecord(user,pass,fname,gen,place,num_watched);
                            if (insert ==true){
                                Toast.makeText(getActivity(), "Signup was successful",Toast.LENGTH_SHORT).show();
                                //FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                //Fragment profileFragment = new ProfileFragment();
                                //editprofileFragment.setArguments(bundle);
                                //fragmentTransaction.replace(R.id.navigation_profile, profileFragment);
                                //fragmentTransaction.commit();

                            }
                            else{
                                Toast.makeText(getActivity(), "Signup was unsuccessful", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(getActivity(),"User already exists", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getActivity(), "Typed passwords are not the same ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}