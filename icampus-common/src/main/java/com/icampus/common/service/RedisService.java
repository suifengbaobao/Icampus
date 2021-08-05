package com.icampus.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
    // 因为common是公用的，不能因为某个jar包导致出错
    @Autowired(required = false)// 从Spring注入时，有则注入，没有则不注入
    private ShardedJedisPool shardedJedisPool;
    
    /**
     * 执行方法
     * @param fun
     * @return
     */
    private <T> T execute(Function<T, ShardedJedis> fun) {
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            // 从redis中获取数据
            return fun.callback(shardedJedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public String get(final String key){
        return this.execute(new Function<String, ShardedJedis>(){
            @Override
            public String callback(ShardedJedis e) {
                return e.get(key);
            }
        });
    }
    
    /**
     * 设置数据
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value){
        return this.execute(new Function<String, ShardedJedis>(){
            @Override
            public String callback(ShardedJedis e) {
                return e.set(key, value);
            }
        });
    }
    
    /**
     * 设置数据并设置缓存时间
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public String set(final String key, final int seconds, final String value){
        return this.execute(new Function<String, ShardedJedis>(){
            @Override
            public String callback(ShardedJedis e) {
                return e.setex(key, seconds, value);
            }
        });
    }
    
    /**
     * 根据键删除缓存数据
     * @param key
     * @return
     */
    public Long delete(final String key){
        return this.execute(new Function<Long, ShardedJedis>(){
            @Override
            public Long callback(ShardedJedis e) {
                return e.del(key);
            }
            
        });
    }
    
    /**
     * 根据键值设置生存时间
     * @param key
     * @param time
     * @return
     */
    public Long expire(final String key, final Integer time){
        return this.execute(new Function<Long, ShardedJedis>(){
            @Override
            public Long callback(ShardedJedis e) {
                return e.expire(key, time);
            }
        });
    }
    // public String get(String key){
    // ShardedJedis shardedJedis = null;
    // try {
    // // 从连接池中获取到jedis分片对象
    // shardedJedis = shardedJedisPool.getResource();
    //
    // // 从redis中获取数据
    // String value = shardedJedis.get(key);
    // return value;
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // if (null != shardedJedis) {
    // // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
    // shardedJedis.close();
    // }
    // }
    // return null;
    // }

    // /**
    // * 设置数据
    // * @param key
    // * @return
    // */
    // public void set(String key, String value){
    // ShardedJedis shardedJedis = null;
    // try {
    // // 从连接池中获取到jedis分片对象
    // shardedJedis = shardedJedisPool.getResource();
    //
    // // 向redis中设置数据
    // shardedJedis.set(key, value);
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // if (null != shardedJedis) {
    // // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
    // shardedJedis.close();
    // }
    // }
    // }
}
