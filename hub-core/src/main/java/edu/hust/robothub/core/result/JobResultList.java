package edu.hust.robothub.core.result;

import java.util.ArrayList;
import java.util.List;

public class JobResultList extends ResultKV<String, List<JobResult>> {


    public JobResultList(String jobId) {
        key = jobId;
        value = new ArrayList<>();
    }

    public boolean addJobResult(JobResult jobResult) {
        return value.add(jobResult);
    }
}
