package com.jobrec.app;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jobrec.R;
import com.jobrec.adapters.JobsPageAdapter;
import com.jobrec.controllers.CandidateController;
import com.jobrec.databinding.ActivityYourApplicationsBinding;
import com.jobrec.db.UsersDAO;
import com.jobrec.domain.Job;
import com.jobrec.mappers.Mappers;
import com.jobrec.utils.Callback;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class YourApplicationsActivity extends AppCompatActivity {
    private ActivityYourApplicationsBinding binding;
    private CandidateController candidateController;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("AMALIA", "dupa start applications");
        binding = ActivityYourApplicationsBinding.inflate(getLayoutInflater());

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        candidateController = CandidateController.getInstance();

        recyclerView = binding.applicationsRecyclerView;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(YourApplicationsActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Job> applications = new ArrayList<>();
        JobsPageAdapter adapter = new JobsPageAdapter(applications, this);
        UsersDAO.getInstance().getApplications(new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                for (DocumentReference application : (ArrayList<DocumentReference>) obj) {
                    application.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                adapter.jobs.add(Mappers.mapToJob(task.getResult()));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });

        recyclerView.setAdapter(adapter);
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
