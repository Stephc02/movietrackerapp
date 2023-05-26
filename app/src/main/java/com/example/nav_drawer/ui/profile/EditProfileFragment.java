package com.example.nav_drawer.ui.profile;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

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

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel mViewModel;
    TextView name;
    EditText gender,country,num_movies;
    Button submit;
    DBHelper db;
    String user;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        db=new DBHelper(getActivity());
        submit=root.findViewById(R.id.btnSubmit);
        name=root.findViewById(R.id.nameedit);
        gender=root.findViewById(R.id.gender2);
        country=root.findViewById(R.id.country2);
        num_movies=root.findViewById(R.id.nummovieswatched2);

        String fullname= (String) name.getText();
        Toast.makeText(getActivity(),fullname,Toast.LENGTH_SHORT).show();


        //Bundle bundle=getArguments();
        //if (bundle !=null) {
        //    String name_imported= bundle.getString("name");
        //    Toast.makeText(getContext(),name_imported, Toast.LENGTH_SHORT).show();
         //   name.setText(name_imported);
       // }

        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                  user=result.getString("username");
                    Log.d(TAG, "user is  : " + user);
                  String fname=result.getString("name");
                  String gend=result.getString("gender");
                  String place=result.getString("country");
                  String num_moviesw=result.getString("num_movies");
                  name.setText(fname);
                  gender.setText(gend);
                  country.setText(place);
                  num_movies.setText(num_moviesw);
            } });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "username is : " + user);
                String num_moviesw=num_movies.getText().toString();
                String gend= gender.getText().toString();
                String place=country.getText().toString();
                Log.d(TAG, "number movies : " + num_moviesw);
                if (num_moviesw.equals("") || gend.equals("") || place.equals("")) {
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();

                }
                else {
                    Boolean checkupdate=db.updateRecord(user,num_moviesw,gend,place);
                    if (checkupdate==false){
                        Toast.makeText(getActivity(),"Profile update  was unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(),"Profile was successfully updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}