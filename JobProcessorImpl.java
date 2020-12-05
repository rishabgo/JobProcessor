import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JobProcessorImpl implements JobProcessor{

    private static ConcurrentHashMap<Long,JobDetails> jobQueue = new ConcurrentHashMap<>();

    @Override
    public void updateJobState(long jobId, Status status, long epochTimeOfUpdate) {

        JobDetails jobDetails = jobQueue.get(jobId);
        if(jobDetails != null){
            jobDetails.setStatus(status);
            jobDetails.setJobUpdateTime(epochTimeOfUpdate);
        }else{

            JobDetails jobDetail = JobDetails.getBuilder()
                                   .withJobId(jobId)
                                   .withStatus(status)
                                   .withJobUpdateTime(epochTimeOfUpdate)
                                   .build();
            jobQueue.put(jobId,jobDetail);
        }
    }

    @Override
    public Status getJobState(long jobId) throws InvalidJobException {

        JobDetails jobDetails = jobQueue.get(jobId);
        if(jobDetails !=null){
            return jobDetails.getStatus();
        }
       throw  new InvalidJobException("Invalid job id:" + jobId);
    }

    @Override
    public List<JobDetails> getTopKActiveJobs(int noOfActiveJobs) {

        List<JobDetails>  activeJobDetailsList = new ArrayList<>();
        Queue<JobDetails> jobDetailsQueue = new PriorityQueue<>(noOfActiveJobs, (o1, o2) -> (int) (o1.getJobUpdateTime()-o2.getJobUpdateTime()));

        for(JobDetails jobDetails : jobQueue.values()){
             if((jobDetails.getStatus()  != Status.COMPLETE) || (jobDetails.getStatus() != Status.ERROR)){
                 jobDetailsQueue.add(jobDetails);
             }
        }

        int jobQueueSize = jobDetailsQueue.size();
        for(int i=0;i<noOfActiveJobs && i<jobQueueSize;i++){
           activeJobDetailsList.add(jobDetailsQueue.poll());
        }
       return activeJobDetailsList;
    }

    public static void main(String[] args) {
        JobProcessorImpl jobProcessor = new JobProcessorImpl();
        jobProcessor.updateJobState(1,Status.IN_QUEUE,12);
        jobProcessor.updateJobState(2,Status.ERROR,1);
        jobProcessor.updateJobState(3,Status.IN_PROCESS,10);
        jobProcessor.updateJobState(2,Status.IN_PROCESS,5);
        jobProcessor.updateJobState(5,Status.COMPLETE,30);
        jobProcessor.updateJobState(6,Status.COMPLETE,1);
        jobProcessor.updateJobState(7,Status.COMPLETE,2);
        jobProcessor.updateJobState(8,Status.COMPLETE,3);

        System.out.println(jobProcessor.getTopKActiveJobs(5));
    }
}
