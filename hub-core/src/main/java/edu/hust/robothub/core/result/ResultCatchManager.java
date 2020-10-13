package edu.hust.robothub.core.result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public enum ResultCatchManager {
    Instance;

    private ConcurrentHashMap<String, JobResultList> resultMap = new ConcurrentHashMap<>();

    static public ResultCatchManager getInstance() {
        return Instance;
    }


    //添加缓存
    public boolean add(String jobId, JobResult jobResult) {
        JobResultList jobResultList = resultMap.get(jobId);

        if (jobResultList == null) {
            jobResultList = new JobResultList(jobId);
            resultMap.put(jobId, jobResultList);
        }
        return jobResultList.addJobResult(jobResult);
    }

    public JobResultList get(String jobId){
        return resultMap.get(jobId);
    }

    //清空缓存
    List<JobResultList> drainAllResultList() {
         List<JobResultList> res=new ArrayList<>();
        for (String key : resultMap.keySet()) {
          res.add(resultMap.remove(key));
        }
        return res;
    }

}
