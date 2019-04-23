package com.yhh.practice.concurrentFrame.vo;

public class TaskResult<R> {
    private final TaskResultType taskResultType;
    private final R returnvalue;
    private final String desc;

    public TaskResult(TaskResultType taskResultType, R returnvalue, String desc) {
        this.taskResultType = taskResultType;
        this.returnvalue = returnvalue;
        this.desc = desc;
    }
    public TaskResult(TaskResultType taskResultType, R returnvalue) {
        this.taskResultType = taskResultType;
        this.returnvalue = returnvalue;
        this.desc="Success";
    }

    public TaskResultType getTaskResultType() {
        return taskResultType;
    }

    public R getReturnvalue() {
        return returnvalue;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskResultType=" + taskResultType +
                ", returnvalue=" + returnvalue +
                ", desc='" + desc + '\'' +
                '}';
    }
}
