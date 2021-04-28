package com.king.frame.mvvmframe.bean;


import com.king.frame.mvvmframe.base.livedata.StatusEvent;

import androidx.annotation.Nullable;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class Resource<T> {

    @StatusEvent.Status
    public int status;

    @Nullable
    public String message;

    @Nullable
    public T data;

    @Nullable
    public Throwable error;

    public Resource(@StatusEvent.Status int status) {
        this(status,null);
    }

    public Resource(@StatusEvent.Status int status, @Nullable String message) {
        this(status,message,null);
    }

    public Resource(@StatusEvent.Status int status, @Nullable String message, @Nullable T data) {
        this(status,message,data,null);
    }

    public Resource(@StatusEvent.Status int status, @Nullable String message, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public boolean isLoading(){
        return status == StatusEvent.Status.LOADING;
    }

    public boolean isSuccess(){
        return status == StatusEvent.Status.SUCCESS;
    }

    public boolean isFailure(){
        return status == StatusEvent.Status.FAILURE;
    }

    public boolean isError(){
        return status == StatusEvent.Status.ERROR;
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(StatusEvent.Status.LOADING);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(StatusEvent.Status.LOADING, null,data);
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(StatusEvent.Status.SUCCESS,null,data);
    }

    public static <T> Resource<T> failure(String msg) {
        return new Resource<>(StatusEvent.Status.FAILURE, msg);
    }

    public static <T> Resource<T> error(Throwable t) {
        return error(t,null);
    }

    public static <T> Resource<T> error(Throwable t,String msg) {
        return new Resource<>(StatusEvent.Status.ERROR, msg,null,t);
    }

}
