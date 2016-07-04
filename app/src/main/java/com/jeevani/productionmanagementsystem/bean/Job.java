package com.jeevani.productionmanagementsystem.bean;

/**
 * Created by GUPTA on 04-Jul-16.
 */
public class Job {

    private String jobId;
    private String job;
    private String jobDetail;
    private String basicRate;
    private String dARate;

    public Job() {
    }

    public Job(String job, String jobDetail, String basicRate, String dARate) {
        this.job = job;
        this.jobDetail = jobDetail;
        this.basicRate = basicRate;
        this.dARate = dARate;
    }

    public Job(String jobId, String job, String jobDetail, String basicRate, String dARate) {
        this.jobId = jobId;
        this.job = job;
        this.jobDetail = jobDetail;
        this.basicRate = basicRate;
        this.dARate = dARate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    public String getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public String getdARate() {
        return dARate;
    }

    public void setdARate(String dARate) {
        this.dARate = dARate;
    }
}
