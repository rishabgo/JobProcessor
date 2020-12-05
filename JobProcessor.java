import java.util.List;

public interface JobProcessor {

    public void updateJobState(long jobId,Status status,long epochTimeOfUpdate);
    public Status getJobState(long jobId) throws InvalidJobException;
    public List<JobDetails> getTopKActiveJobs(int noOfActiveJobs);
}
