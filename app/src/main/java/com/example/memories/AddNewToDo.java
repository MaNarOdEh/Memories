package com.example.memories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.memories.ui.addnewtodo.AddNewToDoFragment;

public class AddNewToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_to_do_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddNewToDoFragment.newInstance())
                    .commitNow();
        }
    }
}
