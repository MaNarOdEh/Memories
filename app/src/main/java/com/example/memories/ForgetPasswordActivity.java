package com.example.memories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText medit_email;
    private Button mbtn_login;
    private LinearLayout mconfirm_msg,mmain_layout;
    private TextView mTxt_login;
    private ProgressBar mProgress_circular;
    private  TextView mText_wrong;
    private  Button btn_goToLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initializeAllWighet();
        makeNecessaryEvent();
    }

    private void initializeAllWighet() {
        medit_email=findViewById(R.id.edit_email);
        mbtn_login=findViewById(R.id.btn_login);
        mconfirm_msg=findViewById(R.id.confirm_msg);
        mmain_layout=findViewById(R.id.main_layout);
        mTxt_login=findViewById(R.id.txt_login);
        mProgress_circular=findViewById(R.id.progress_circular);
        mText_wrong=findViewById(R.id.text_wrong);
        btn_goToLogIn=findViewById(R.id.btn_goToLogIn);
    }
    private void makeNecessaryEvent(){
        mbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=medit_email.getText().toString();
                if (!Validations.isEmailValid(email)) {
                    mText_wrong.setText("Input Corect Email Please!");

                }else{
                    mProgress_circular.setVisibility(View.VISIBLE);

                    sendFogetPassowrdLink(email);
                }

            }
        });
        mTxt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,MainActivity.class));
            }
        });
        medit_email.addTextChangedListener(new TextWatcher() {
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
        btn_goToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,MainActivity.class));
            }
        });


    }
    private void sendFogetPassowrdLink(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setVisibilityProgressGone();
                        if (task.isSuccessful()) {
                            mconfirm_msg.setVisibility(View.VISIBLE);
                            mmain_layout.setVisibility(View.GONE);
                          //  Log.d(TAG, "Email sent.");
                        }else{
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            setWrongMessage(errorCode);


                        }
                    }
                });
    }
    private void setVisibilityProgressGone(){
        mProgress_circular.setVisibility(View.GONE);
    }
    private void setWrongMessage(String errorCode){
        mText_wrong.setText(Validations.getWrongMessage(errorCode));
    }
}
