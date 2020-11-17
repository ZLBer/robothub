package edu.hust.robothub.core.cache;

import edu.hust.robothub.core.result.ResultKV;

import java.util.List;

public interface Cache<K,V> {
  void initCache(List<ResultKV<K,V>> cacheList);
  void cache(K key,V value);
  boolean containsKey(K key);
  V getCache(K key);
  List<ResultKV<K,V>> getAllCache();
  List<V> getAllValues();
}