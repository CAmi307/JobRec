package com.example.suggestions.domain;

import java.util.ArrayList;

public class ManagerAPI extends UserAPI {
    private static boolean isSingleton = false;
    private static ManagerAPI instance = null;

    private String companyName;
    private ArrayList<JobAPI> jobs;

    public ManagerAPI(String id, String firstName, String lastName, Integer age, String companyName, ArrayList<JobAPI> jobs) {
        super(id,firstName,lastName,age);
        this.companyName = companyName;
        this.jobs = jobs;
    }

    private ManagerAPI() {}

    public ManagerAPI getInstance() {
        if(instance == null){
            instance = new ManagerAPI();
        }
        return instance;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ArrayList<JobAPI> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<JobAPI> jobs) {
        this.jobs = jobs;
    }

    public void addJob(JobAPI job) {
        jobs.add(job);
    }

    public void removeJob(JobAPI job) {
        jobs.remove(job);
    }
}

