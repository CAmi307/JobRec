package com.jobrec.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QuerySnapshot;
import com.jobrec.adapters.CandidatesPageAdapter;
import com.jobrec.controllers.ManagerController;
import com.jobrec.databinding.FragmentCandidatesPageBinding;
import com.jobrec.db.UsersDAO;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Job;
import com.jobrec.utils.Callback;

import java.util.ArrayList;

public class CandidatesPageFragment extends Fragment {
    private FragmentCandidatesPageBinding binding;

    private ManagerController managerController;
    private UsersDAO database;

    private Job job;
    private ArrayList<Candidate> candidates;
    private RecyclerView recyclerView;
    CandidatesPageAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCandidatesPageBinding.inflate(inflater, container, false);
        managerController = ManagerController.getInstance();

        job = (Job) getArguments().get("job");
        database = UsersDAO.getInstance();
        recyclerView = binding.jobsListRecyclerView;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        candidates = managerController.getAllCandidates();
        adapter = new CandidatesPageAdapter(candidates, job.getId());
        recyclerView.setAdapter(adapter);


        managerController.getApplicantsForJob(job.getId(), new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                adapter.candidates.add((Candidate) obj);
                adapter.notifyDataSetChanged();
            }
        });

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

//    private ArrayList<Candidate> scanAvailableCandidatesForJob() {
//        ArrayList<Candidate> availableCandidates = new ArrayList();
//
//        availableCandidates.add(new Candidate(
//                "Amalia",
//                "",
//                21,
//                "Software Developer",
//                3,
//                5000,
//                "Looong description about mee",
//                Degree.LICENSE,
//                "Bucharest",
//                WokringType.FULL_REMOTE,
//                WorkingHours.EIGHT_HOURS,
//                "",
//                new ArrayList<Skill>(),
//                false
//        ));
//        availableCandidates.add(new Candidate(
//                "Amalia",
//                "Simion",
//                21,
//                "Software Developer",
//                3,
//                5000,
//                "Looong description about mee",
//                Degree.LICENSE,
//                "Bucharest",
//                WokringType.FULL_REMOTE,
//                WorkingHours.EIGHT_HOURS,
//                "",
//                new ArrayList<Skill>(),
//                true
//        ));
//        availableCandidates.add(new Candidate(
//                "Amalia",
//                "Simion",
//                21,
//                "Software Developer",
//                3,
//                5000,
//                "Looong description about mee",
//                Degree.LICENSE,
//                "Bucharest",
//                WokringType.FULL_REMOTE,
//                WorkingHours.EIGHT_HOURS,
//                "",
//                new ArrayList<Skill>(),
//                true
//        ));
//        availableCandidates.add(new Candidate(
//                "Amalia",
//                "Simion",
//                21,
//                "Software Developer",
//                3,
//                5000,
//                "Looong description about mee",
//                Degree.LICENSE,
//                "Bucharest",
//                WokringType.FULL_REMOTE,
//                WorkingHours.EIGHT_HOURS,
//                "",
//                new ArrayList<Skill>(),
//                true
//        ));
//
//        return availableCandidates;
//    }
}
