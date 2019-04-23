package com.yhh.practice.concurrentFrame.vo;

import com.yhh.practice.concurrentFrame.vo.core.CheckJobProcesser;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 提交给并发框架的执行任务实体类
 * @param <R>
 */
public class JobInfo<R> {

    private final String jobName; //任务名
    private final int jobLength; //任务个数
    private final ITaskProcesser<?,?> iTaskProcesser; //任务执行处理器
    private AtomicInteger successCount;//成功执行任务数
    private AtomicInteger taskProcesserCount; //总执行任务数
    private final LinkedBlockingDeque<TaskResult<R>> linkedBlockingQueue; //任务执行结果放置的容器
    private final long exprieTime;  //任务执行过期时间

    public JobInfo(String jobName, int jobLength,
                   ITaskProcesser<?, ?> iTaskProcesser,
                   long exprieTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.iTaskProcesser = iTaskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount = new AtomicInteger(0);
        this.linkedBlockingQueue = new LinkedBlockingDeque<TaskResult<R>>(jobLength);
        this.exprieTime = exprieTime;
    }

    public ITaskProcesser<?, ?> getiTaskProcesser() {
        return iTaskProcesser;
    }

    public int getSuccessCount() {
        return successCount.get();
    }
    public int getTaskProcesserCount() {
        return taskProcesserCount.get();
    }
    public int getFailCount(){
        return taskProcesserCount.get()-successCount.get();
    }
    public String getTotalProcess(){
        return "Success["+successCount.get()+"]/Current["
                +taskProcesserCount.get()+"] Total["+jobLength+"]";
    }

    /****
     * 获取任务执行结果
     * @return
     */
    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskResults = new LinkedList<TaskResult<R>>();
        TaskResult<R> taskResult;
        while((taskResult=linkedBlockingQueue.pollFirst())!=null){
            taskResults.add(taskResult);
        }
        return  taskResults;
    }

    /***
     *  成功 successCount +1
     *  任务总处理数 +1
     *  将处理结果放入容器中
     * @param taskResult
     */
    public void addTaskResult(TaskResult<R> taskResult, CheckJobProcesser checkJob){
        if(TaskResultType.Success.equals(taskResult.getTaskResultType())){
            successCount.incrementAndGet();
        }
        linkedBlockingQueue.addLast(taskResult);
        taskProcesserCount.incrementAndGet();
        if(taskProcesserCount.get()==jobLength) {
            checkJob.putJob(jobName, exprieTime);
        }
    }

}
