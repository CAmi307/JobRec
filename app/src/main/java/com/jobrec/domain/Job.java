package com.jobrec.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Job implements Serializable {
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

    private ArrayList<Candidate> applicants;
    private String idManager;

    public Job(String id,String jobTitle, String companyName, String shortDescription, String description, String manager) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.idManager = manager;
        this.isFavourite = false;
    }

    public Job(
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
            String idManager
    ) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.isFavourite = isFavourite;
        this.experience = experience;
        this.salaryExpectations = salaryExpectations;
        this.lastDegree = lastDegree;
        this.city = city;
        this.workingHours = workingHours;
        this.workingType = workingType;
        this.idManager = idManager;
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

    public Degree getLastDegree() {
        if(lastDegree!=null) {
            switch (this.lastDegree) {
                case "PRIMARY_SCHOOL":
                    return Degree.PRIMARY_SCHOOL;
                case "HIGH_SCHOOL":
                    return Degree.HIGH_SCHOOL;
                case "LICENSE":
                    return Degree.LICENSE;
                case "MASTER":
                    return Degree.MASTER;
                case "DOCTOR":
                    return Degree.DOCTOR;
                default:
                    return Degree.UNKNOWN;
            }
        }else {
            return Degree.UNKNOWN;
        }
    }

    public void setLastDegree(Degree lastDegree) {
        switch (lastDegree) {
            case PRIMARY_SCHOOL:
                this.lastDegree = "PRIMARY_SCHOOL";
                break;
            case HIGH_SCHOOL:
                this.lastDegree = "HIGH_SCHOOL";
                break;
            case LICENSE:
                this.lastDegree = "LICENSE";
                break;
            case MASTER:
                this.lastDegree = "MASTER";
                break;
            case DOCTOR:
                this.lastDegree = "DOCTOR";
                break;
            default:
                this.lastDegree = "UNKNOWN";
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public WorkingHours getWorkingHours() {
        if(workingHours!=null) {
            switch (this.workingHours) {
                case "FOUR_HOURS":
                    return WorkingHours.FOUR_HOURS;
                case "SIX_HOURS":
                    return WorkingHours.SIX_HOURS;
                case "EIGHT_HOURS":
                    return WorkingHours.EIGHT_HOURS;
                default:
                    return WorkingHours.UNKNOWN;
            }
        } else  {
            return WorkingHours.UNKNOWN;
        }
    }

    public void setWorkingHours(WorkingHours workingHoursPreferred) {
        switch (workingHoursPreferred) {
            case FOUR_HOURS:
                this.workingHours = "FOUR_HOURS";
                break;
            case SIX_HOURS:
                this.workingHours = "SIX_HOURS";
                break;
            case EIGHT_HOURS:
                this.workingHours = "EIGHT_HOURS";
                break;
            default:
                this.workingHours = "UNKNOWN";
        }
    }

    public WokringType getWorkingType() {
        if(workingType!=null) {
            switch (this.workingType) {
                case "HYBRID":
                    return WokringType.HYBRID;
                case "FULL_REMOTE":
                    return WokringType.FULL_REMOTE;
                case "PHYSICAL":
                    return WokringType.PHYSICAL;
                default:
                    return WokringType.UNKNOWN;
            }
        } else {
            return WokringType.UNKNOWN;
        }
    }

    public void setWorkingTypePreferred(WokringType workingTypePreferred) {
        switch (workingTypePreferred) {
            case HYBRID:
                this.workingType = "HYBRID";
                break;
            case FULL_REMOTE:
                this.workingType = "FULL_REMOTE";
                break;
            case PHYSICAL:
                this.workingType = "PHYSICAL";
                break;
            default:
                this.workingType = "UKNOWN";
        }
    }

    public ArrayList<Candidate> getApplicants() {
        return applicants;
    }

    public void setApplicants(ArrayList<Candidate> applicants) {
        this.applicants = applicants;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public void addApplicant(Candidate candidate) {
        applicants.add(candidate);
    }

    public void removeApplicant(Candidate candidate) {
        applicants.remove(candidate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
