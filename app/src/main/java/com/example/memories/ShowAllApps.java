package com.example.memories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.memories.ui.showallapps.ShowAllAppsFragment;

public class ShowAllApps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_apps_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ShowAllAppsFragment.newInstance())
                    .commitNow();
        }
    }
}
