package com.example.memories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import com.example.memories.ui.addnewapp.AddNewAppFragment;
import com.example.memories.ui.addnewtodo.AddNewToDoFragment;
import com.example.memories.ui.showallapps.ShowAllAppsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class FirstActivtiy extends AppCompatActivity {
    private  FirebaseAuth mAtuh;
    private FrameLayout mMainFramLayout;
    private FloatingActionButton maAdNew,mLog_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_activtiy);
        mAtuh=FirebaseAuth.getInstance();
        if(mAtuh.getCurrentUser()==null){
            startActivity(new Intent(FirstActivtiy.this,MainActivity.class));
            finish();
        }
        initialzeAllWighet();
        makeNecessaryEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAtuh.getCurrentUser()==null){
            startActivity(new Intent(FirstActivtiy.this,MainActivity.class));
            finish();
        }
    }

    private void initialzeAllWighet() {
        mMainFramLayout=findViewById(R.id.main_fram_layout);
        maAdNew=findViewById(R.id.add_new);
        mLog_out=findViewById(R.id.log_out);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment=new ShowAllAppsFragment();
        fragmentTransaction.replace(R.id.main_fram_layout, fragment).addToBackStack( null ).commit();
        mAtuh=FirebaseAuth.getInstance();
    }
    private void makeNecessaryEvent() {
        maAdNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment=new AddNewToDoFragment();
                fragmentTransaction.replace(R.id.main_fram_layout, fragment).addToBackStack( null ).commit();

            }
        });
        mLog_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAtuh.signOut();
                startActivity(new Intent(FirstActivtiy.this,MainActivity.class));
            }
        });
    }
    public  void add_apps(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment_add_owner=new AddNewAppFragment();
        fragmentTransaction.replace(R.id.main_fram_layout, fragment_add_owner).addToBackStack( null ).commit();
    }
}
