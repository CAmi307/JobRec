package com.jobrec.mappers;

import com.example.suggestions.domain.CandidateAPI;
import com.example.suggestions.domain.ManagerAPI;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Degree;
import com.jobrec.domain.Job;
import com.example.suggestions.domain.JobAPI;
import com.jobrec.domain.Manager;
import com.jobrec.domain.UserType;
import com.jobrec.domain.WokringType;
import com.jobrec.domain.WorkingHours;

import java.util.ArrayList;
import java.util.Objects;

public class Mappers {
    public static UserType mapToUserType(Integer userType) {
        switch (userType) {
            case 0:
                return UserType.ADMIN;
            case 1:
                return UserType.CANDIDATE;
            case 2:
                return UserType.MANAGER;
            default:
                return UserType.UNKNOWN;
        }
    }

    public static Degree mapToLastDegree(Integer lastDegree) {
        switch (lastDegree) {
            case 0:
                return Degree.PRIMARY_SCHOOL;
            case 1:
                return Degree.HIGH_SCHOOL;
            case 2:
                return Degree.LICENSE;
            case 3:
                return Degree.MASTER;
            case 4:
                return Degree.DOCTOR;
            default:
                return Degree.UNKNOWN;
        }
    }

    public static WokringType mapToWorkingType(Integer workingType) {
        switch (workingType) {
            case 0:
                return WokringType.FULL_REMOTE;
            case 1:
                return WokringType.HYBRID;
            case 2:
                return WokringType.PHYSICAL;
            default:
                return WokringType.UNKNOWN;
        }
    }

    public static WorkingHours mapToWorkingHours(Integer workingHours) {
        switch (workingHours) {
            case 0:
                return WorkingHours.FOUR_HOURS;
            case 1:
                return WorkingHours.SIX_HOURS;
            case 2:
                return WorkingHours.EIGHT_HOURS;
            default:
                return WorkingHours.UNKNOWN;
        }
    }

//    private static ArrayList<Skill> mapToSkills(ArrayList<int> dbSkill) }


    public static Candidate mapToCandidate(DocumentSnapshot userSnapshot) {
        Candidate candidate = userSnapshot.toObject(Candidate.class);
        candidate.setId(userSnapshot.getId());
        return candidate;
//        Candidate candidate = new Candidate(
//                userSnapshot.getId(),
//                String.valueOf(userSnapshot.getData().get("firstName")),
//                String.valueOf(userSnapshot.getData().get("lastName")),
//                Integer.parseInt(userSnapshot.getData().get("age")) ,
//                String.valueOf(userSnapshot.getData().get("currentJob")),
//                Integer.parseInt(String.valueOf(userSnapshot.getData().get("experience"))),
//                Integer.parseInt(String.valueOf(userSnapshot.getData().get("salaryExpectations"))),
//                String.valueOf(userSnapshot.getData().get("description")),
//                Mappers.mapToLastDegree(Integer.parseInt(String.valueOf(userSnapshot.getData().get("lastDegree")))),
//                String.valueOf(userSnapshot.getData().get("city")),
//                Mappers.mapToWorkingType(Integer.parseInt(String.valueOf(userSnapshot.getData().get("workingType")))),
//                Mappers.mapToWorkingHours(Integer.parseInt(String.valueOf(userSnapshot.getData().get("workingHours")))),
//                String.valueOf(userSnapshot.getData().get("picture")),
//                String.valueOf(userSnapshot.getData().get("isFavourite")).equals("true"),
//                new ArrayList<Job>()
//        );
//
////        Log.e("AMALIA", candidate.getUserType().toString());
//        return candidate;
    }

    public static Job mapToJob(DocumentSnapshot doc) {
        return new Job(
                doc.getId(),
                doc.getData().get("jobTitle").toString(),
                doc.getData().get("companyName").toString(),
                doc.getData().get("shortDescription").toString(),
                doc.getData().get("description").toString(),
                doc.getData().get("isFavourite").toString().equals("true"),
                Integer.parseInt(Objects.requireNonNull(doc.getData().get("experience")).toString()),
                Integer.parseInt(Objects.requireNonNull(doc.getData().get("salaryExpectations")).toString()),
                String.valueOf(doc.getData().get("lastDegree")),
                doc.getData().get("city").toString(),
                String.valueOf(doc.getData().get("workingHours")),
                String.valueOf(doc.getData().get("workingType")),
                doc.getData().get("manager").toString()
        );
    }

    public static Manager mapToManager(DocumentSnapshot userSnapshot) {
        return new Manager(
                userSnapshot.getId(),
                userSnapshot.get("firstName", String.class),
                userSnapshot.get("lastName", String.class),
                userSnapshot.get("age", Integer.class),
                userSnapshot.get("companyName", String.class),
                new ArrayList<Job>()
        );
    }

    public static Job mapToJob(JobAPI job) {
        return new Job(
                job.getId(),
                job.getJobTitle(),
                job.getCompanyName(),
                job.getShortDescription(),
                job.getDescription(),
                job.isFavourite(),
                job.getExperience(),
                job.getSalaryExpectations(),
                job.getLastDegree(),
                job.getCity(),
                job.getWorkingHours(),
                job.getWorkingType(),
                job.getIdManager()
        );
    }

    public static JobAPI mapToJobAPI(Job job) {
        return new JobAPI(
                job.getId(),
                job.getJobTitle(),
                job.getCompanyName(),
                job.getShortDescription(),
                job.getDescription(),
                job.isFavourite(),
                job.getExperience(),
                job.getSalaryExpectations(),
                job.getLastDegree().toString(),
                job.getCity(),
                job.getWorkingHours().toString(),
                job.getWorkingType().toString(),
                job.getIdManager()
        );
    }

    public static Manager mapToManager(ManagerAPI manager) {
        ArrayList<Job> jobs = new ArrayList<>();
        for (JobAPI job : manager.getJobs()) {
            jobs.add(Mappers.mapToJob(job));
        }

        return new Manager(
                manager.getId(),
                manager.getFirstName(),
                manager.getLastName(),
                manager.getAge(),
                manager.getCompanyName(),
                jobs
        );
    }

    public static ManagerAPI mapToManagerAPI(Manager manager) {
        ArrayList<JobAPI> jobs = new ArrayList<>();
        for (Job job : manager.getJobs()) {
            jobs.add(Mappers.mapToJobAPI(job));
        }

        return new ManagerAPI(
                manager.getId(),
                manager.getFirstName(),
                manager.getLastName(),
                manager.getAge(),
                manager.getCompanyName(),
                jobs
        );
    }

    public static Candidate mapToCandidate(CandidateAPI candidate) {
//        ArrayList<Job> applications = new ArrayList<>();
//        for (JobAPI job : candidate.getApplications()) {
//            applications.add(Mappers.mapToJob(job));
//        }

        return new Candidate(
                candidate.getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getAge(),
                candidate.getCurrentJob(),
                candidate.getExperience(),
                candidate.getSalaryExpectations(),
                candidate.getDescription(),
                candidate.getLastDegree(),
                candidate.getCity(),
                candidate.getWorkingTypePreferred(),
                candidate.getWorkingHoursPreferred(),
                candidate.getPicture(),
                candidate.isFavourite()
        );
    }

    public static CandidateAPI mapToCandidateAPI(Candidate candidate) {
        ArrayList<JobAPI> applications = new ArrayList<>();
//        if (candidate.getApplications() != null) {
//            for (Job job : candidate.getApplications()) {
//                applications.add(Mappers.mapToJobAPI(job));
//            }
//        }

        String lastDegree = null;
        String workingType = null;
        String workingHours = null;
        if(candidate.getLastDegree() != null) {
            lastDegree = candidate.getLastDegree().toString();
        }
        if(candidate.getWorkingTypePreferred() != null) {
            workingType = candidate.getWorkingTypePreferred().toString();
        }
        if(candidate.getWorkingHoursPreferred()!=null) {
            workingHours = candidate.getWorkingHoursPreferred().toString();
        }

        return new CandidateAPI(
                candidate.getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getAge(),
                candidate.getCurrentJob(),
                candidate.getExperience(),
                candidate.getSalaryExpectations(),
                candidate.getDescription(),
                lastDegree,
                candidate.getCity(),
                workingType,
                workingHours,
                candidate.getPicture(),
                candidate.isFavourite()
        );
    }
}