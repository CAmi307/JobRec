package com.example.suggestions.domain;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class JobAPI {
    private String id;
    private String jobTitle;
    private String companyName;
    private String shortDescription;
    private String description;
    private boolean isFavourite;
    private Integer experience;
    private Integer salaryExpectations;
    private String lastDegree;
    private String city;
    private String workingHours;
    private String workingType;

    private ArrayList<DocumentReference> applicants;
    private String idManager;

    public JobAPI(String id, String jobTitle, String companyName, String shortDescription, String description, String manager) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.idManager = manager;
        this.id = id;
        this.isFavourite = false;
    }

    public JobAPI(
            String id,
            String jobTitle,
            String companyName,
            String shortDescription,
            String description,
            boolean isFavourite,
            Integer experience,
            Integer salaryExpectations,
            String lastDegree,
            String city,
            String workingHours,
            String workingType,
            String manager
    ) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.isFavourite = isFavourite;
        this.experience = experience;
        this.salaryExpectations = salaryExpectations;
        this.lastDegree = lastDegree;
        this.city = city;
        this.id = id;
        this.workingHours = workingHours;
        this.workingType = workingType;
        this.idManager = manager;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
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

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getWorkingType() {
        return workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public ArrayList<DocumentReference> getApplicants() {
        return applicants;
    }

    public void setApplicants(ArrayList<DocumentReference> applicants) {
        this.applicants = applicants;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public void addApplicant(DocumentReference candidate) {
        applicants.add(candidate);
    }

    public void removeApplicant(DocumentReference candidate) {
        applicants.remove(candidate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

