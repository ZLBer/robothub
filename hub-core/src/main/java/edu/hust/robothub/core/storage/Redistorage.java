package edu.hust.robothub.core.storage;

import edu.hust.robothub.core.until.Config;
import redis.clients.jedis.Jedis;

public class Redistorage extends AbstractStorage<Redistorage.RedisRequest>{

    private final String REDIS_HOST = "localhost";
    private final int REDIS_PORT = 6379;
    Jedis jedis;

    public Redistorage(Config config) {
        super(config);
    }

    @Override
    public void start() {
        jedis = new Jedis(REDIS_HOST,REDIS_PORT);
    }

    @Override
    public void storage(RedisRequest obj) {
       if(obj.getType() == RedisRequsetType.SET){
           jedis.set(obj.getKey(),obj.getValue());
       }else if(obj.getType()==RedisRequsetType.SADD){
           jedis.sadd(obj.getKey(),obj.getValue());
       }else if(obj.getType()==RedisRequsetType.ZADD){
           jedis.zadd(obj.getKey(),obj.getScore(),obj.getValue());
       }
    }


    @Override
    public void stop() {
     jedis.close();
    }

    class RedisRequest{
        RedisRequsetType type;
        String key;
        String value;
        double score;

        public RedisRequest(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public RedisRequsetType getType() {
            return type;
        }

        public void setType(RedisRequsetType type) {
            this.type = type;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }

    enum RedisRequsetType{
       SET,
       SADD,
       ZADD;
    }
}
