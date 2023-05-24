package com.jobrec.domain;

import java.util.ArrayList;

public class Manager extends User {
    public static boolean isSingleton = false;
    private static Manager instance = null;

    private String companyName;
    private ArrayList<Job> jobs;

    private ArrayList<Candidate> candidates;

    public Manager(String id, String firstName, String lastName, Integer age, String companyName, ArrayList<Job> jobs) {
        super(id,firstName,lastName,age, UserType.MANAGER);
        this.companyName = companyName;
        this.jobs = jobs;
    }

    private Manager() {
        super(UserType.MANAGER);
    }

    public static Manager getInstance() {
        if(isSingleton) {
            if(instance == null){
                instance = new Manager();
            }
            return instance;
        }
        return null;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job) {
        jobs.add(job);
    }

    public void removeJob(Job job) {
        jobs.remove(job);
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void addCandidate (Candidate candidate) {
        candidates.add(candidate);
    }
}
