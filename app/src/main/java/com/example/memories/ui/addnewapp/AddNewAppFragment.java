package com.example.memories.ui.addnewapp;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.memories.R;
import com.example.memories.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewAppFragment extends Fragment {

    private AddNewAppViewModel mViewModel;
    private EditText edit_app_name,edit_app_password,edit_app_user_name;
    private Button mBtn_save;
    private TextView mText_wrong;
    private FirebaseFirestore db;
    private CheckBox mcheckboxFavApp;
    private View view;

    public static AddNewAppFragment newInstance() {
        return new AddNewAppFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.add_new_app_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddNewAppViewModel.class);
        initializeAllwighet();
        makeNecessaryEvent();
        // TODO: Use the ViewModel
    }

    private void initializeAllwighet() {
        mcheckboxFavApp=view.findViewById(R.id.checkboxFavApp);
        edit_app_name=view.findViewById(R.id.edit_app_name);
        edit_app_password=view.findViewById(R.id.edit_app_password);
        mBtn_save=view.findViewById(R.id.btn_save);
        mText_wrong=view.findViewById(R.id.text_wrong);
        edit_app_user_name=view.findViewById(R.id.edit_app_user_name);
        db = FirebaseFirestore.getInstance();
        mBtn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app_name=edit_app_name.getText().toString(),
                        app_password=edit_app_password.getText().toString(),
                user_anem=edit_app_user_name.getText().toString();
                if(!Validations.isValidName(app_name)){
                    mText_wrong.setText("Input Your App Name");
                }else if(!Validations.isValidName(user_anem)){
                    mText_wrong.setText("Input Your User Name  !");
                }else if(!Validations.isValidName(app_password)){
                    mText_wrong.setText("Input Your App Password !");
                } else{
                    addNewApp(app_name,user_anem,app_password,mcheckboxFavApp.isChecked()?1:0);
                }

            }
        });
    }
    private void makeNecessaryEvent(){
        edit_app_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mText_wrong.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_app_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mText_wrong.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_app_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mText_wrong.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void addNewApp(String appName, String user,String password,int fav) {
        // Create a new user with a first and last name
        Map<String, Object> app = new HashMap<>();
        app.put("appName", appName);
        app.put("username", user);
        app.put("password",password);
        app.put("user",FirebaseAuth.getInstance().getUid());
        app.put("fav",fav);
        /*db.collection("apps").
                document(FirebaseAuth.getInstance().getUid()).
                set(app).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //   setVisibilityProgressGone();
                        if (task.isSuccessful()) {
                            mText_wrong.setText("Added Successfully !");
                            removeContent();
                        } else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            // setWrongMessage(errorCode);
                            mText_wrong.setText(Validations.getWrongMessage(errorCode));
                        }
                        // startActivity(new Intent(getActivity().this, FirstActivtiy.class));
                    }
                });*/
        db.collection("apps").add(app).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    mText_wrong.setText("Added Successfully !");
                    removeContent();
                }else if(!task.isSuccessful()){
                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                    // setWrongMessage(errorCode);
                    mText_wrong.setText(Validations.getWrongMessage(errorCode));
                }

            }
        });

    }
    private void removeContent(){
        edit_app_password.setText("");
        edit_app_name.setText("");
        edit_app_user_name.setText("");
    }

}
