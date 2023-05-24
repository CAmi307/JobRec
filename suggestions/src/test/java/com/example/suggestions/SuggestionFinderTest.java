package com.example.suggestions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.suggestions.domain.CandidateAPI;
import com.example.suggestions.domain.JobAPI;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SuggestionFinderTest {

    private SuggestionFinder finder;

    CandidateAPI candidateAPI;
    JobAPI notMatchingJob;
    JobAPI matchingJob;

    @Before
    public void setup() {
        finder = new SuggestionFinder();
        candidateAPI = new CandidateAPI(
                "randomId",
                "Amalia",
                "",
                21,
                "Software Developer",
                3,
                5000,
                "Looong description about mee",
                "LICENSE",
                "Bucharest",
                "FULL_REMOTE",
                "EIGHT_HOURS",
                "",
                false
        );
        matchingJob = new JobAPI(
                "randomId",
                "Scrum Master",
                "Microsoft",
                "Be the scrum master of all scrum masters",
                "loooong description",
                false,
                1,
                5000,
                "LICENSE",
                "Bucharest",
                "EIGHT_HOURS",
                "FULL_REMOTE",
                "managerId"
        );
        notMatchingJob = new JobAPI(
                "randomId",
                "Scrum Master",
                "Microsoft",
                "Be the scrum master of all scrum masters",
                "loooong description",
                false,
                12,
                3000,
                "MASTER",
                "Bucharest",
                "SIX_HOURS",
                "HYBRID",
                "managerId"
        );

    }

    @Test
    public void getJobReccomendationsForCandidate_should_return_an_empty_list_if_job_list_provided_is_empty() {
        ArrayList<JobAPI> jobs = new ArrayList<>();

        ArrayList<JobAPI> expected = new ArrayList<>();

        assertEquals(expected, finder.getJobReccomendationsForCandidate(candidateAPI, jobs));
    }

    @Test
    public void getJobReccomendationsForCandidate_should_return_an_empty_list_if_no_job_is_reccommended_for_user() {
        ArrayList<JobAPI> jobs = new ArrayList<>();
        jobs.add(notMatchingJob);

        ArrayList<JobAPI> expected = new ArrayList<>();

        assertEquals(expected, finder.getJobReccomendationsForCandidate(candidateAPI, jobs));
    }

    @Test
    public void getJobReccomendationsForCandidate_should_exclude_not_reccomended_jobs() {
        ArrayList<JobAPI> jobs = new ArrayList<>();
        jobs.add(matchingJob);
        jobs.add(notMatchingJob);

        ArrayList<JobAPI> expected = new ArrayList<>(Arrays.asList(matchingJob));

        assertEquals(expected, finder.getJobReccomendationsForCandidate(candidateAPI, jobs));
    }
}