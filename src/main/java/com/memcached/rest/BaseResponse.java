package com.memcached.rest;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    private long processTime;
    private long averageTime;

    public BaseResponse(long processTime, long averageTime) {
        this.processTime = processTime;
        this.averageTime = averageTime;
    }

    public long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(long averageTime) {
        this.averageTime = averageTime;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
}
