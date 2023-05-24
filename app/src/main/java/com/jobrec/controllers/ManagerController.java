package com.jobrec.controllers;

import com.google.firebase.firestore.DocumentSnapshot;
import com.jobrec.db.UsersDAO;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Job;
import com.jobrec.domain.Manager;
import com.jobrec.domain.UserType;
import com.jobrec.mappers.Mappers;
import com.jobrec.utils.Callback;

import java.util.ArrayList;

public class ManagerController {

    private static ManagerController instance = null;
    private UsersDAO userDB;


    private ManagerController(){
        userDB = UsersDAO.getInstance();
    }

    public static ManagerController getInstance() {
        if(instance == null) {
            instance = new ManagerController();
        }
        return instance;
    }

    public ArrayList<Candidate> getAllCandidates() {
        Manager currentUser = Manager.getInstance();
        ArrayList<Candidate> applicants = new ArrayList<>();
        for(Job job: currentUser.getJobs()) {
            applicants.addAll(job.getApplicants());
        }
        return applicants;
    }

    public void getAllJobs(Callback callback) {
        UsersDAO.getInstance().getJobsForManager(new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                callback.onCallbackReceived(obj);
            }
        });

    }

    public void createNewManager(String email) {
        userDB.addNewManager(email);
        Manager.isSingleton = true;
        Manager.getInstance().setEmail(email);
    }

    public void isUserRegistered(String email, Callback callback) {
        userDB.getManager(email, new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                callback.onCallbackReceived(obj);
            }
        });
    }

    public void updateCurrentUser(String email) {
        Manager.isSingleton = true;
        UsersDAO.getInstance().getUser(email, new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                fillInDataForUser((Manager) obj);
            }
        });
    }

    public void updateCurrentUser(Manager manager) {
        Manager.isSingleton = true;
        fillInDataForUser(manager);
    }

    private void fillInDataForUser(Manager manager) {
        Manager.getInstance().setEmail(manager.getEmail());
        Manager.getInstance().setCompanyName(manager.getCompanyName());
        Manager.getInstance().setJobs(manager.getJobs());
        Manager.getInstance().setFirstName(manager.getFirstName());
        Manager.getInstance().setLastName(manager.getLastName());
        Manager.getInstance().setId(manager.getId());
        Manager.getInstance().setAge(manager.getAge());
        Manager.getInstance().setUserType(UserType.MANAGER);
    }

    public void getApplicantsForJob(String jobId, Callback callback) {
        UsersDAO.getInstance().getApplicantsForJob(jobId, new Callback() {
            @Override
            public void onCallbackReceived(Object obj) {
                callback.onCallbackReceived(Mappers.mapToCandidate((DocumentSnapshot) obj));
            }
        });
    }
}
