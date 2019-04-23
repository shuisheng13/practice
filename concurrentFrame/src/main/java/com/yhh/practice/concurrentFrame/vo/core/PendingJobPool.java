package com.yhh.practice.concurrentFrame.vo.core;

import com.yhh.practice.concurrentFrame.vo.ITaskProcesser;
import com.yhh.practice.concurrentFrame.vo.JobInfo;
import com.yhh.practice.concurrentFrame.vo.TaskResult;
import com.yhh.practice.concurrentFrame.vo.TaskResultType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.*;

public class PendingJobPool {

    //保守估计
    private static final int THREAD_COUNTS =
            Runtime.getRuntime().availableProcessors();
    //有界阻塞队列(任务队列)
    private static BlockingQueue<Runnable> taskQueue
            = new ArrayBlockingQueue<Runnable>(5000);
    //线程池，固定大小，有界队列
    private static ExecutorService taskExecutor =
            new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60,
                    TimeUnit.SECONDS, taskQueue);
    //job的存放容器
    private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap
            = new  ConcurrentHashMap<String, JobInfo<?>>();
    private static CheckJobProcesser checkJob
            = CheckJobProcesser.getInstance();
    /***
     * 单例框架实例
     */

    private PendingJobPool(){}

    public static  Map<String,JobInfo<?>> getMap() {
        return jobInfoMap;
    }

    public static class JobPendHolder{
        public static PendingJobPool pool = new PendingJobPool();
    }
    public static PendingJobPool getInstall(){
        return  JobPendHolder.pool;
    }
    //将任务处理器再次包装成runnable
    public static class PendingTask<T,R> implements Runnable{
        private JobInfo<R> jobInfo;
        private T processDate;

        public PendingTask(JobInfo<R> jobInfo, T processDate) {
            this.jobInfo = jobInfo;
            this.processDate = processDate;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcesser<T,R> iTaskProcesser = (ITaskProcesser<T, R>) jobInfo.getiTaskProcesser();
            TaskResult<R> taskResult = null;
            try{
                taskResult = iTaskProcesser.taskExecute(processDate);
                if(null==taskResult){
                    taskResult = new TaskResult<R>(TaskResultType.Exception,r,"result is null");
                }
                if(null==taskResult.getTaskResultType()){
                    if (taskResult.getDesc() == null) {
                        taskResult = new TaskResult<R>(TaskResultType.Exception, r, "reason is null");
                    } else {
                        taskResult = new TaskResult<R>(TaskResultType.Exception, r,
                                "result is null,but reason:" + taskResult.getDesc());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                taskResult = new TaskResult<R>(TaskResultType.Exception, r,
                        e.getMessage());
            }finally {
                jobInfo.addTaskResult(taskResult,checkJob);
            }

        }
    }

    public <R> JobInfo<R> getJob(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if(null==jobInfo){
            throw new RuntimeException("非法任务");
        }
        return  jobInfo;
    }
    public <T,R> void putTask(String jobName,T t){
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T,R> pendingTask = new PendingTask<T, R>(jobInfo,t);
        taskExecutor.execute(pendingTask);
    }

    //注册任务v
    public <R> void registerJob(String jobName, int jobLength,
                            ITaskProcesser<?, ?> iTaskProcesser,
                            long exprieTime){

        JobInfo<R> jobInfo = new  JobInfo<R>(
                jobName,jobLength,iTaskProcesser,exprieTime);
                if(jobInfoMap.putIfAbsent(jobName,jobInfo)!=null){
                    throw new RuntimeException(jobName+"已经注册了！");
                }

    }
    //获取每个任务的任务具体详情
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        return jobInfo.getTaskDetail();
    }
    //获取任务进度
    public <R> String getTaskProgess(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        return jobInfo.getTotalProcess();
    }

}
