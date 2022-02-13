package com.geekbrains;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;


    public static void main(String[] args) throws InterruptedException {
        arrayFirst();
        arraySecond();
    }

    public static void arrayFirst() {
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы 1 метода:" + (System.currentTimeMillis() - a));
        System.out.println();
    }

    public static void arraySecond() throws InterruptedException {
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        long all = System.currentTimeMillis();

        float[] a1 = new float[h];
        float[] a2 = new float[h];
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        all = System.currentTimeMillis() - a;
        System.out.println("Время разбивки на 2 массива:" + all);

        a = System.currentTimeMillis();

        Thread innerThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }

        });

        Thread innerThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        innerThread1.start();
        innerThread1.join();
        System.out.println("Время прогонки 1 массива:" + (System.currentTimeMillis() - a));
        all += System.currentTimeMillis() - a;
        long timeARR = System.currentTimeMillis();


        innerThread2.start();
        innerThread2.join();
        System.out.println("Время прогонки 2 массива:" + (System.currentTimeMillis() - timeARR));
        all += System.currentTimeMillis() - timeARR;
        a = System.currentTimeMillis();


        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Время склейки массива:" + (System.currentTimeMillis() - a));
        all += System.currentTimeMillis() - a;
        System.out.println();
        System.out.println("Общее время 2 метода:" + all);

    }


}

