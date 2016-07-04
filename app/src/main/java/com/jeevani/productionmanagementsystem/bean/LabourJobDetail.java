package com.jeevani.productionmanagementsystem.bean;

/**
 * Created by GUPTA on 04-Jul-16.
 */
public class LabourJobDetail {

    private String labourJobId;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String device;
    private String type = "LABOUR";
    private String jobId;
    private String job;
    private String jobDetail;
    private String basicRate;
    private String dARate;
    private String jobDate;
    private String remark = "INCOMPLETE";

    public LabourJobDetail() {
    }

    public LabourJobDetail(String labourJobId, String userId, String firstName, String lastName, String email, String phone, String device, String type, String jobId, String job, String jobDetail, String basicRate, String dARate, String jobDate, String remark) {
        this.labourJobId = labourJobId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.device = device;
        this.type = type;
        this.jobId = jobId;
        this.job = job;
        this.jobDetail = jobDetail;
        this.basicRate = basicRate;
        this.dARate = dARate;
        this.jobDate = jobDate;
        this.remark = remark;
    }

    public String getLabourJobId() {
        return labourJobId;
    }

    public void setLabourJobId(String labourJobId) {
        this.labourJobId = labourJobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
