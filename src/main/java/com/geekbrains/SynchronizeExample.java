package com.geekbrains;

public class SynchronizeExample {
    /**
     * Моем посуду:
     * 1) Берем губку
     * 2) Наливаем средство для мытья
     * 3) Берем тарелку
     * 4) Моем тарелку
     * 5) Протираем тарелку
     * 6) Складываем тарелку
     */


    public static class Counter {
        private int value;

        private Object monitor = new Object();
        private Object monitor2 = new Object();

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        //150 // берем
        //инкрементим
        //151 // присваиваем
        public synchronized void increment() {
            System.out.println("Хотим сделать инкремент");
            synchronized (monitor) {
                value++;
            }
            System.out.println("Закончили инкремент");
        }

        //150 // берем
        //уменьшаем
        //149 // присваиваем
        public void decrement() {
            System.out.println("Хотим сделать декремент");
            synchronized (monitor2) {
                value--;
            }
            System.out.println("Закончили декремент");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread incrementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    counter.increment();
                }
            }
        });
        Thread incrementThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    counter.increment();
                }
            }
        });
        Thread decrementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    counter.decrement();
                }
            }
        });

        incrementThread.start();
        incrementThread1.start();
        decrementThread.start();
        incrementThread.join();
        incrementThread1.join();
        decrementThread.join();

        System.out.println(counter.getValue());
    }
}
