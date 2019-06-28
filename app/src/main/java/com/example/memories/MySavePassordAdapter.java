package com.example.memories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MySavePassordAdapter extends RecyclerView.Adapter<MySavePassordAdapter.ViewHolder> {
    private ArrayList<AppInfo> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView edit_appName;
        public EditText edit_name;
        public EditText edit_password;
        public ImageView img_bookmark;
        public ViewHolder(View viewItems) {
            super(viewItems);
            edit_name = viewItems.findViewById(R.id.edit_name);
            edit_password=viewItems.findViewById(R.id.edit_password);
            img_bookmark=viewItems.findViewById(R.id.img_bookmark);
            edit_appName=viewItems.findViewById(R.id.edit_appName);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MySavePassordAdapter(ArrayList<AppInfo> Dataset) {
        mDataset = Dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MySavePassordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
      /*  TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        ...*/
        View contactView=LayoutInflater.from(parent.getContext()).inflate(R.layout.appinfo,parent,false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView) ;
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MySavePassordAdapter.ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
      //  holder.textView.setText(mDataset[position]);
        // Get the data model based on position
        AppInfo contact = mDataset.get(position);

        // Set item views based on your views and data model
        TextView edit_appName=viewHolder.edit_appName;
        edit_appName.setText(contact.getmApp_name());
        EditText editname = viewHolder.edit_name;
        editname.setText(contact.getmUser_name());
        EditText  edit_password=viewHolder.edit_password;
        edit_password.setText(contact.mApp_pass);
        ImageView img_bookmark=viewHolder.img_bookmark;


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
