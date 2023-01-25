class CounterThread extends Thread {

    @Override
    public void run() {
        long counter = 0;

        while (Thread.currentThread().isInterrupted()) {
            counter++;
        }
        System.out.println("It was interrupted");
    }
}