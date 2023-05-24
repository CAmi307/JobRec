package com.jobrec.controllers;

import com.example.suggestions.domain.JobAPI;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jobrec.db.UsersDAO;
import com.example.suggestions.SuggestionFinder;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Job;
import com.jobrec.domain.UserType;
import com.jobrec.mappers.Mappers;
import com.jobrec.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class CandidateController {

    private static CandidateController instance = null;
    private UsersDAO userDB;

    public CandidateController() {
        userDB = UsersDAO.getInstance();
    }

    public static CandidateController getInstance() {
        if (instance == null) {
            instance = new CandidateController();
        }
        return instance;
    }

    public void  getJobReccomendations(Callback callback) {
        ArrayList<Job> jobs = new ArrayList<>();
        Candidate currentUser = Candidate.getInstance();
        SuggestionFinder finder = new SuggestionFinder();

        ArrayList<JobAPI> allJobs = new ArrayList<>();
        UsersDAO.getInstance().getAllJobs(new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                for (DocumentSnapshot doc: (List<DocumentSnapshot>) obj) {
                    Job job = Mappers.mapToJob(doc);
                    allJobs.add(Mappers.mapToJobAPI(job));
                }

                for(JobAPI job: finder.getJobReccomendationsForCandidate(Mappers.mapToCandidateAPI(currentUser), allJobs)) {
                    jobs.add(Mappers.mapToJob(job));
                }

                callback.onCallbackReceived(jobs);
            }
        });
    }

    public void createNewCandidate(String email) {
        userDB.addNewCandidate(email);
        Candidate.isSingleton = true;
        Candidate.getInstance().setEmail(email);
    }

    public void updateCurrentUser(String email) {
        Candidate.isSingleton = true;
        UsersDAO.getInstance().getUser(email, new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                fillInDataForUser((Candidate) obj);
            }
        });
    }

    public void updateCurrentUser(Candidate candidate) {
        Candidate.isSingleton = true;
        fillInDataForUser(candidate);
    }

    public void isUserRegistered(String email, Callback callback) {
        userDB.getUser(email, new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                callback.onCallbackReceived(obj);
            }
        });
    }

    private void fillInDataForUser(Candidate candidate) {
        Candidate.getInstance().setEmail(candidate.getEmail());
        Candidate.getInstance().setCurrentJob(candidate.getCurrentJob());
        Candidate.getInstance().setExperience(candidate.getExperience());
        Candidate.getInstance().setSalaryExpectations(candidate.getSalaryExpectations());
        Candidate.getInstance().setDescription(candidate.getDescription());
        Candidate.getInstance().setLastDegree(candidate.getLastDegree());
        Candidate.getInstance().setCity(candidate.getCity());
        Candidate.getInstance().setWorkingTypePreferred(candidate.getWorkingTypePreferred());
        Candidate.getInstance().setWorkingHoursPreferred(candidate.getWorkingHoursPreferred());
        Candidate.getInstance().setPicture(candidate.getPicture());
        Candidate.getInstance().setFavourite(candidate.isFavourite());
        Candidate.getInstance().setSkills(candidate.getSkills());
        Candidate.getInstance().setFirstName(candidate.getFirstName());
        Candidate.getInstance().setLastName(candidate.getLastName());
        Candidate.getInstance().setId(candidate.getId());
        Candidate.getInstance().setAge(candidate.getAge());
        Candidate.getInstance().setUserType(UserType.MANAGER);
    }
}
