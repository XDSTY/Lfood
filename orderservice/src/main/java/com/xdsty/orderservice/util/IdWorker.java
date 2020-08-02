package com.xdsty.orderservice.util;

import com.xdsty.orderservice.nacos.ConfigCenter;
import com.xdsty.orderservice.nacos.ConfigKeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雪花算法生成唯一id，在集群中，由于每台机器的时间戳不太一样，可能会导致在整个集群范围生成id不是严格的递增，但是在单台机器上是严格单调递增的（除了时间回退发生）
 * 雪花算法：返回long类型64bit的长整型
 * 0  000...0   000..0   000..0
 * 将64bit的长整型分为4个部分：
 * 第一部分：1bit，不使用，默认为0
 * 第二部分：41bit，用于记录时间戳，可保存的时间为（2^41 - 1） / (1000*60*60*24*365) = 69年
 * 第三部分：10bit，可自己定义，这里分为5bit的datacenterId和5bit的workerId，表示当前机器所在的数据中心id和机器id
 * 第四部分：12bit，12bit可以表示的正整数为 [0 ~ 2^12-1] 就是[0, 4095]
 * @author 张富华
 * @date 2020/7/28 11:24
 */
public class IdWorker {

    private static Logger log = LoggerFactory.getLogger(IdWorker.class);

    /**
     * 单例模式
     */
    private static class InnerInstance {
        static IdWorker idWorker = new IdWorker(16, 16, 0,
                Long.parseLong(ConfigCenter.getConfigValue(ConfigKeyEnum.SNOW_FLACK_TIMESTAMP.dataId)));
    }

    /**
     * 获取下一个id
     * @return
     */
    public static long getNextId() {
        return InnerInstance.idWorker.nextId();
    }

    private IdWorker(long workerId, long datacenterId, long sequence, long twepoch){
        // maxWorkerId的二进制为 00...011111  为2^5-1
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        //同maxWorkerId
        long maxDataCenterId = ~(-1L << datacenterIdBits);
        if (datacenterId > maxDataCenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        log.error("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
        this.twepoch = twepoch;
    }

    /**
     * 当前机器id
     */
    private long workerId;

    /**
     * 当前机器所在数据中心id
     */
    private long datacenterId;

    private long sequence;

    private long twepoch;

    private long workerIdBits = 5L;

    private long datacenterIdBits = 5L;

    private long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;

    private long datacenterIdShift = sequenceBits + workerIdBits;

    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**
     * sequenceMask的二进制为000..0111111111111  最后为12个1，用来和sequence进行位与操作，防止sequence越界
     */
    private long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;

    private synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            log.error("clock is moving backwards.  Rejecting requests until {}", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // 使用sequenceMask防止sequence越界
            sequence = (sequence + 1) & sequenceMask;
            // sequence == 0 当前timestamp的sequence已经使用完，则在tilNextMillis方法中循环等待下一毫秒
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen(){
        return System.currentTimeMillis();
    }

}
