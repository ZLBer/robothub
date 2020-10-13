package edu.hust.robothub.core.result;

public class JobResult extends ResultKV<Long,String>{


    public JobResult(long time,String value) {
        this.key=time;
        this.value = value;
    }


}
