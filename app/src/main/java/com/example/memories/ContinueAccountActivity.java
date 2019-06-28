package com.example.memories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class ContinueAccountActivity extends AppCompatActivity {
    private TextInputEditText mInputEdit_name, mInputBirthDate_name;
    private TextView mText_wrong;
    private Button mBtn_submit;
    private ProgressBar mProgress_circular;
    private FirebaseFirestore db;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_account);
        initializeAllWighet();
        makeNecessaryEvent();
    }

    private void initializeAllWighet() {
        mInputEdit_name = findViewById(R.id.inputEdit_name);
        mText_wrong = findViewById(R.id.text_wrong);
        mInputBirthDate_name = findViewById(R.id.inputBirthDate_name);
        mBtn_submit = findViewById(R.id.btn_submit);
        mProgress_circular = findViewById(R.id.progress_circular);
        db = FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
    }

    private void makeNecessaryEvent() {
        mInputEdit_name.addTextChangedListener(new TextWatcher() {
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
        mInputBirthDate_name.addTextChangedListener(new TextWatcher() {
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
        mInputBirthDate_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ContinueAccountActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mInputBirthDate_name.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, 1995, 1, 1);
                datePickerDialog.show();
            }
        });
        mBtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mInputEdit_name.getText().toString(),
                        date = mInputBirthDate_name.getText().toString();
                if (!Validations.isValidName(name)) {
                    mText_wrong.setText("Your Name is Invalid !");

                } else if (!Validations.isValidDate(date)) {
                    mText_wrong.setText("Your Date Birth is Wrong !");

                } else {
                    mProgress_circular.setVisibility(View.VISIBLE);
                    setUserInfo(name, date);

                }
            }
        });
    }

    private void setVisibilityProgressGone() {
        mProgress_circular.setVisibility(View.GONE);
    }

    private void setUserInfo(String name, String birthDate) {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("birthDate", birthDate);
        db.collection("users").
                document(FirebaseAuth.getInstance().getUid()).
                set(user).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setVisibilityProgressGone();
                        if (task.isSuccessful()) {
                            mText_wrong.setText("Added Successfully !");
                        } else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            setWrongMessage(errorCode);
                            // mText_wrong.setText(errorCode);
                        }
                        startActivity(new Intent(ContinueAccountActivity.this,FirstActivtiy.class));
                    }
                })
        ;
    }

    private void setWrongMessage(String errorCode){
        mText_wrong.setText(Validations.getWrongMessage(errorCode));
    }
}
