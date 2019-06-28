package com.example.memories;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignActivity extends AppCompatActivity {



    private EditText medit_email,medit_pass;
    private FloatingActionButton mbtn_gmail_login;
    private TextView mtxt_go_sign_act,mText_wrong;
    private  Button mbtn_login;
    private FirebaseAuth mAuth;
    private ProgressBar mProgress_circular;
    private static final int RC_SIGN_IN=1;
    private CheckBox mChaec_remmerberMe;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initliazeAllwighet();
        makeNecessaryEvent();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void initliazeAllwighet() {
        medit_email=findViewById(R.id.edit_email);
        medit_pass=findViewById(R.id.edit_pass);
        mbtn_gmail_login=findViewById(R.id.btn_gmail_login);
        mtxt_go_sign_act=findViewById(R.id.txt_go_sign_act);
        mText_wrong=findViewById(R.id.text_wrong);
        mbtn_login=findViewById(R.id.btn_login);
        mProgress_circular=findViewById(R.id.progress_circular);
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);

    }
    private  void makeNecessaryEvent(){
        mbtn_gmail_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
        medit_pass.addTextChangedListener(new TextWatcher() {
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


        mtxt_go_sign_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivity.this,MainActivity.class));

            }
        });
        mbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress_circular.setVisibility(View.VISIBLE);
                String email,pass;
                email=medit_email.getText().toString();
                pass=medit_pass.getText().toString();
                if(!Validations.isEmailValid(email)){
                    setVisibilityProgressGone();
                    mText_wrong.setText("Input Correct Email Please!");
                }else if(!Validations.isValidPass(pass)){
                    setVisibilityProgressGone();
                    mText_wrong.setText("Input your password Please!");
                }else{
                    createAcctount(email,pass);
                }

            }
        });


    }
   private void setVisibilityProgressGone(){
       mProgress_circular.setVisibility(View.GONE);
   }
    private void createAcctount(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            updateUI();
                            setVisibilityProgressGone();

                        } else {
                            setVisibilityProgressGone();
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            setWrongMessage(errorCode);
                        }

                        // ...
                    }
                });

    }
    private void setWrongMessage(String errorCode){
        mText_wrong.setText(Validations.getWrongMessage(errorCode));
    }
    private   void updateUI(){
        startActivity(new Intent(SignActivity.this,ContinueAccountActivity.class));
        finish();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setVisibilityProgressGone();
                        if (task.isSuccessful()) {
                            //updateUI(user);
                        } else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            setWrongMessage(errorCode);
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(main_view, "Authentication Failed."+task.getException(), Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN&&data!=null&&resultCode== Activity.RESULT_OK){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                updateUI();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //  Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
        if(resultCode==Activity.RESULT_OK){
            //  callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }
}
