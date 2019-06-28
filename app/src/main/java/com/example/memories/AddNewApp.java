package com.example.memories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.memories.ui.addnewapp.AddNewAppFragment;

public class AddNewApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_app_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddNewAppFragment.newInstance())
                    .commitNow();
        }
    }
}
