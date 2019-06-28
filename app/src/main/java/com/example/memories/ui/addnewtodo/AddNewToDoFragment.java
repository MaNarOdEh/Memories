package com.example.memories.ui.addnewtodo;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.memories.ContinueAccountActivity;
import com.example.memories.FirstActivtiy;
import com.example.memories.R;
import com.example.memories.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewToDoFragment extends Fragment {

    private AddNewToDoViewModel mViewModel;
    private Button mAdd_to_do_items,mAdd_app_pass,mAdd_some_memories;
    private View view;




    public static AddNewToDoFragment newInstance() {
        return new AddNewToDoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return view= inflater.inflate(R.layout.add_new_to_do_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddNewToDoViewModel.class);
        initilizeAllWighet();
        makeNecessaryEvent();
        // TODO: Use the ViewModel
    }
    private void initilizeAllWighet() {
        mAdd_to_do_items=view.findViewById(R.id.add_to_do_items);
        mAdd_app_pass=view.findViewById(R.id.add_app_pass);

        mAdd_some_memories=view.findViewById(R.id.add_some_memories);


    }
    private void makeNecessaryEvent() {
        mAdd_app_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(getActivity(),));
                ((FirstActivtiy)getActivity()).add_apps();
            }
        });
        mAdd_to_do_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAdd_some_memories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
