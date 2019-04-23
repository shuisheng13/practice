package com.yhh.practice.concurrentFrame.vo;

/***
 *  要求调用者必须继承的业务接口
 * @param <T>
 * @param <R>
 */
public interface ITaskProcesser<T,R> {

    TaskResult<R> taskExecute(T data);

}
