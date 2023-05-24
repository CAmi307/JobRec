package com.example.suggestions;

import com.example.suggestions.domain.CandidateAPI;
import com.example.suggestions.domain.JobAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SuggestionFinder {

    public ArrayList<JobAPI> getJobReccomendationsForCandidate(CandidateAPI candidate, ArrayList<JobAPI> availableJobs){
        ArrayList<JobAPI> jobs = new ArrayList<>();

        for(JobAPI job: availableJobs) {
            int counter = 0;
            if(Objects.equals(job.getSalaryExpectations(), candidate.getSalaryExpectations())) {
                counter++;
            }
            if(job.getExperience() <= candidate.getExperience()) {
                counter++;
            }
            if(Objects.equals(job.getLastDegree(), candidate.getLastDegree())) {
                counter++;
            }
            if (Objects.equals(job.getCity(), candidate.getCity())) {
                counter++;
            }
            if(Objects.equals(job.getWorkingHours(), candidate.getWorkingHoursPreferred())) {
                counter++;
            }
            if(Objects.equals(job.getWorkingType(), candidate.getWorkingTypePreferred())) {
                counter++;
            }

            if(counter >= 3) {
                jobs.add(job);
            }
        }

        return jobs;
    }
}
