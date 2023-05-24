package com.jobrec.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
import com.jobrec.controllers.ManagerController;
import com.jobrec.databinding.FragmentJobsPageBinding;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Job;
import com.jobrec.domain.Manager;
import com.jobrec.mappers.Mappers;
import com.jobrec.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class JobsPageFragment extends Fragment {

    private FragmentJobsPageBinding binding;
    private CandidateController candidateController;
    private ManagerController managerController;
    private NavController navController;
    private ArrayList<Job> jobs;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentJobsPageBinding.inflate(inflater, container, false);
        candidateController = CandidateController.getInstance();
        managerController = ManagerController.getInstance();

        navController = NavHostFragment.findNavController(JobsPageFragment.this);
        recyclerView = binding.jobsListRecyclerView;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        JobsPageAdapter adapter = new JobsPageAdapter(new ArrayList<>(), this.getContext());
        recyclerView.setAdapter(adapter);

        if (Candidate.isSingleton) {
            candidateController.getJobReccomendations(new Callback() {
                @Override
                public void onCallbackReceived(Object jobs) {
                    adapter.jobs.addAll((ArrayList<Job>) jobs);
                    adapter.notifyDataSetChanged();
                }
            });
        } else if (Manager.isSingleton) {
            adapter.setNavController(navController);
            managerController.getAllJobs(new Callback() {
                @Override
                public void onCallbackReceived(Object obj) {
                    for (DocumentReference jobRef : (ArrayList<DocumentReference>) obj) {
                        jobRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private ArrayList<Job> scanAvailableJobs() {
//        ArrayList<Job> availableJobs = new ArrayList<>();
//
////        availableJobs.add(new Job("Software Developer", "Luxoft", "Develop user friendly Android apps", "loooong description: TODO"));
////        availableJobs.add(new Job("Java Developer", "IBM", "Develop desktop applications", "loooong description: TODO"));
////        availableJobs.add(new Job("Scrum Master", "Microsoft", "Be the scrum master of all scrum masters", "loooong description: TODO"));
////        availableJobs.add(new Job("Data Base Developer", "Oracle", "Maintenance of data bases", "loooong description: TODO"));
//
//        return availableJobs;
//    }
}
