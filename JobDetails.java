
public class JobDetails {

    private long jobId;
    private Status status;
    private long jobUpdateTime;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getJobUpdateTime() {
        return jobUpdateTime;
    }

    public void setJobUpdateTime(long jobUpdateTime) {
        this.jobUpdateTime = jobUpdateTime;
    }

    @Override
    public String toString() {
        return "JobDetails{" +
                "jobId=" + jobId +
                ", status=" + status +
                ", jobUpdateTime=" + jobUpdateTime +
                '}';
    }

    public static JobDetailsBuilder getBuilder(){
        return new JobDetailsBuilder();
    }

    //Builder
    public static class JobDetailsBuilder{
        private long jobId;
        private Status status;
        private long jobUpdateTime;

        public  JobDetailsBuilder withJobId(long jobId){
            this.jobId = jobId;
            return this;
        }

        public JobDetailsBuilder withStatus(Status status){
            this.status = status;
            return this;
        }

        public JobDetailsBuilder withJobUpdateTime(long jobUpdateTime){
            this.jobUpdateTime = jobUpdateTime;
            return this;
        }

        public JobDetails build(){
            JobDetails jobDetails = new JobDetails();
            jobDetails.setJobId(jobId);
            jobDetails.setStatus(status);
            jobDetails.setJobUpdateTime(jobUpdateTime);
            return jobDetails;
        }

    }
}
