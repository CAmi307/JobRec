package com.example.suggestions.domain;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class CandidateAPI extends UserAPI {
    private static boolean isSingleton = false;
    private static CandidateAPI instance = null;

    private String currentJob;
    private Integer experience;
    private Integer salaryExpectations;
    private String description;
    private String lastDegree;
    private String city;
    private String workingTypePreferred;
    private String workingHoursPreferred;
    private ArrayList<DocumentReference> applications;
    private String picture;
    private boolean isFavourite;
    private ArrayList<String> skills = new ArrayList<>();

    private CandidateAPI() {}

    public static CandidateAPI getInstance() {
        if (instance == null) {
            instance = new CandidateAPI();
        }
        return instance;
    }

    public CandidateAPI(String id, String firstName, String lastName, Integer age) {
        super(id, firstName, lastName, age);
    }

    public CandidateAPI(
            String id,
            String firstName,
            String lastName,
            Integer age,
            String currentJob,
            Integer experience,
            Integer salaryExpectations,
            String description,
            String lastDegree,
            String city,
            String workingTypePreferred,
            String workingHoursPreferred,
            String picture,
            ArrayList<String> skills,
            boolean isFavourite
    ) {
        super(id, firstName, lastName, age);
        this.currentJob = currentJob;
        this.experience = experience;
        this.salaryExpectations = salaryExpectations;
        this.description = description;
        this.lastDegree = lastDegree;
        this.city = city;
        this.workingTypePreferred = workingTypePreferred;
        this.workingHoursPreferred = workingHoursPreferred;
        this.picture = picture;
        this.skills = skills;

        this.isFavourite = isFavourite;
    }

    public CandidateAPI(
            String id,
            String firstName,
            String lastName,
            Integer age,
            String currentJob,
            Integer experience,
            Integer salaryExpectations,
            String description,
            String lastDegree,
            String city,
            String workingTypePreferred,
            String workingHoursPreferred,
            String picture,
            boolean isFavourite
    ) {
        super(id, firstName, lastName, age);
        this.currentJob = currentJob;
        this.experience = experience;
        this.salaryExpectations = salaryExpectations;
        this.description = description;
        this.lastDegree = lastDegree;
        this.city = city;
        this.workingTypePreferred = workingTypePreferred;
        this.workingHoursPreferred = workingHoursPreferred;
        this.picture = picture;
        this.isFavourite = isFavourite;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getSalaryExpectations() {
        return salaryExpectations;
    }

    public void setSalaryExpectations(Integer salaryExpectations) {
        this.salaryExpectations = salaryExpectations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWorkingTypePreferred() {
        return workingTypePreferred;
    }

    public void setWorkingTypePreferred(String workingTypePreferred) {
        this.workingTypePreferred = workingTypePreferred;
    }

    public String getWorkingHoursPreferred() {
        return workingHoursPreferred;
    }

    public void setWorkingHoursPreferred(String workingHoursPreferred) {
        this.workingHoursPreferred = workingHoursPreferred;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public ArrayList<DocumentReference> getApplications() {
        return applications;
    }

    public void setApplications(ArrayList<DocumentReference> applications) {
        this.applications = applications;
    }
}
