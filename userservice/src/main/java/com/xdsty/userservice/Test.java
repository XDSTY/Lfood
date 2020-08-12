package com.xdsty.userservice;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author 张富华
 * @date 2020/8/10 16:16
 */
public class Test {

    private static Object l1 = new Object();
    private static Object l2 = new Object();

    private static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws IOException {
        new Thread(new Task1()).start();
        new Thread(new Task2()).start();
    }

    static class Task1 implements Runnable {
        @Override
        public void run() {
            synchronized (l1) {
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("已获得l1锁，开始抢l2锁");
                synchronized (l2) {
                    System.out.println("获得了l1锁和l2锁");
                }
            }
        }
    }

    static class Task2 implements Runnable {
        @Override
        public void run() {
            synchronized (l2) {
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("获得了l2锁，开始抢l1锁");
                synchronized (l1) {
                    System.out.println("获得了l2锁和l1锁");
                }
            }
        }
    }



}
