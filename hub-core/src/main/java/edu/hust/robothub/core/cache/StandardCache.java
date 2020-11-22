package edu.hust.robothub.core.cache;

import edu.hust.robothub.core.result.ResultKV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardCache<K,V> extends AbstratCache<K,V> {


    Map<K,V> cacheMap=new HashMap<>();

    @Override
    public void initCache(List<ResultKV<K, V>> cacheList) {
        for (ResultKV<K, V> resultKV : cacheList) {
            cache(resultKV.getKey(),resultKV.getValue());
        } 
    }


    @Override
    public void cache(K key,V value) {
       cacheMap.put(key,value);
    }

    @Override
    public boolean containsKey(K key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public V getCache(K key) {
        return cacheMap.get(key);
    }

    @Override
    public List<ResultKV<K, V>> getAllCache() {
        List<ResultKV<K,V>> res=new ArrayList<>();
        for (Map.Entry<K, V> kvEntry : cacheMap.entrySet()) {
             res.add(new ResultKV<>(kvEntry.getKey(),kvEntry.getValue()));
        }
        return res;
    }

    @Override
    public List<V> getAllValues() {
        return new ArrayList<>( cacheMap.values());
    }
}
