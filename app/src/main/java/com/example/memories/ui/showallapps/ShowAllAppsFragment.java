package com.example.memories.ui.showallapps;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.memories.AppInfo;
import com.example.memories.MySavePassordAdapter;
import com.example.memories.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowAllAppsFragment extends Fragment {

    private ShowAllAppsViewModel mViewModel;
    private  View view;
    private LinearLayout linear_main,linear_progress,linear_no_result;
    FirebaseFirestore storage;
    private FirebaseAuth mAuth;
    private RecyclerView recycleView_savePassword;
    ArrayList<AppInfo>appInfos;

    public static ShowAllAppsFragment newInstance() {
        return new ShowAllAppsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.show_all_apps_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ShowAllAppsViewModel.class);
        initializeAllWighet();
        appInfos=new ArrayList<>();
        fetchData();
    }
    private void fetchData(){
        linear_no_result.setVisibility(View.GONE);
        storage.collection("apps")
                .whereEqualTo("user", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots!=null&&queryDocumentSnapshots.size()==0){
                    linear_no_result.setVisibility(View.VISIBLE);
                }
              for(QueryDocumentSnapshot ddd:queryDocumentSnapshots){
                  String password=ddd.get("password",String.class);
                  String appName = ddd.get("appName", String.class);
                  String username=ddd.get("username",String.class);
                  Long fav=ddd.get("fav",Long.class);
                  AppInfo appInfo=new AppInfo(appName,username,password);
                  appInfos.add(appInfo);
                  MySavePassordAdapter adapter = new MySavePassordAdapter(appInfos);
                  recycleView_savePassword.setAdapter(adapter);
                  recycleView_savePassword.setLayoutManager(new LinearLayoutManager(getActivity()));
                  adapter.notifyDataSetChanged();
              }
                linear_progress.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Wrong" +e.getMessage()+"   "+
                        "  !!", Toast.LENGTH_SHORT).show();
                linear_progress.setVisibility(View.GONE);

            }
        });
    }

    private void initializeAllWighet() {
        linear_main=view.findViewById(R.id.linear_main);
        storage=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        recycleView_savePassword=view.findViewById(R.id.recycleView_savePassword);
        linear_progress=view.findViewById(R.id.linear_progress);
        linear_progress.setVisibility(View.VISIBLE);
        linear_no_result=view.findViewById(R.id.linear_no_result);
        setRecycleDetails();

    }private void setRecycleDetails(){
        // in content do not change the layout size of the RecyclerView
        recycleView_savePassword.setHasFixedSize(true);
    }
    private  void addApps(String name,String username,String pass,String fav){
        View view1=getLayoutInflater().inflate(R.layout.appinfo,null);
        TextView edit_appName=view1.findViewById(R.id.edit_appName);
        EditText edit_appUserName=view1.findViewById(R.id.edit_name);
        EditText edit_password=view1.findViewById(R.id.edit_password);
        ImageView img_edit=view1.findViewById(R.id.img_edit);
        edit_appName.setText(name);
        edit_appUserName.setText(username);
        edit_password.setText(pass);
        if(fav.equals("1")){
            img_edit.setImageResource(R.drawable.bookmarks);

        }else {
             img_edit.setImageResource(R.drawable.bookmark);
        }
        linear_main.addView(view1);

    }

}
