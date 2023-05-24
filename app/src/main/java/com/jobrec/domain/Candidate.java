package com.jobrec.domain;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class Candidate extends User {
    public static boolean isSingleton = false;
    private static Candidate instance = null;

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

    private Candidate() {
        super(UserType.CANDIDATE);
    }

    public static Candidate getInstance() {
        if (isSingleton) {
            if (instance == null) {
                instance = new Candidate();
            }
            return instance;
        }
        return null;
    }

    public Candidate(String id, String firstName, String lastName, Integer age) {
        super(id, firstName, lastName, age, UserType.CANDIDATE);
    }

    public Candidate(
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
        super(id, firstName, lastName, age, UserType.CANDIDATE);
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

    public Candidate(
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
        super(id, firstName, lastName, age, UserType.CANDIDATE);
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

    public Degree getLastDegree() {
        if(lastDegree!=null){
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
        } else {
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

    public WokringType getWorkingTypePreferred() {
        if (workingTypePreferred != null) {
            switch (workingTypePreferred) {
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
                this.workingTypePreferred = "HYBRID";
                break;
            case FULL_REMOTE:
                this.workingTypePreferred = "FULL_REMOTE";
                break;
            case PHYSICAL:
                this.workingTypePreferred = "PHYSICAL";
                break;
            default:
                this.workingTypePreferred = "UKNOWN";
        }
    }

    public WorkingHours getWorkingHoursPreferred() {
        if (workingHoursPreferred != null) {
            switch (this.workingHoursPreferred) {
                case "FOUR_HOURS":
                    return WorkingHours.FOUR_HOURS;
                case "SIX_HOURS":
                    return WorkingHours.SIX_HOURS;
                case "EIGHT_HOURS":
                    return WorkingHours.EIGHT_HOURS;
                default:
                    return WorkingHours.UNKNOWN;
            }
        } else {
            return WorkingHours.UNKNOWN;
        }
    }

    public void setWorkingHoursPreferred(WorkingHours workingHoursPreferred) {
        switch (workingHoursPreferred) {
            case FOUR_HOURS:
                this.workingHoursPreferred = "FOUR_HOURS";
                break;
            case SIX_HOURS:
                this.workingHoursPreferred = "SIX_HOURS";
                break;
            case EIGHT_HOURS:
                this.workingHoursPreferred = "EIGHT_HOURS";
                break;
            default:
                this.workingHoursPreferred = "UNKNOWN";
        }
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

    public void addApplication(DocumentReference documentReference) {
        if(applications == null) {
            this.applications  = new ArrayList<>();
        }
        if(documentReference!=null) {
            this.applications.add(documentReference);
        }
    }
}
