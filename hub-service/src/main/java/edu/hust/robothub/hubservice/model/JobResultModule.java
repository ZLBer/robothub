package edu.hust.robothub.hubservice.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "job_result")
public class JobResultModule {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "job_id")
    private String  jobId;
    @Column(name = "job_time")
    private long jobTime;
    @Column(name = "job_res")
    private String  jobRes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobRes() {
        return jobRes;
    }

    public void setJobRes(String jobRes) {
        this.jobRes = jobRes;
    }

    public long getJobTime() {
        return jobTime;
    }

    public void setJobTime(long jobTime) {
        this.jobTime = jobTime;
    }
}
