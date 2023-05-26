package com.example.nav_drawer.ui.login;

import android.content.Context;
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
import androidx.lifecycle.ViewModelProvider;

import com.example.nav_drawer.DBHelper;
import com.example.nav_drawer.R;
import com.example.nav_drawer.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    EditText username, password;
    Button login;
    DBHelper db;
    private FragmentLoginBinding binding;
    LoginListener  mListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel galleryViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        username=root.findViewById(R.id.username1);
        password=root.findViewById(R.id.password1);
        login=root.findViewById(R.id.login1);
        db=new DBHelper(getActivity());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = db.checkUsernamePassword(user,pass);
                    if (checkuserpass==false){
                        Toast.makeText(getActivity(),"Login was unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(),"Login was successful", Toast.LENGTH_SHORT).show();
                        mListener.sendUsername(user);
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mListener = (LoginListener) context;
    }

    //define interface in the fragment
    public interface LoginListener{
        void sendUsername(String username);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}