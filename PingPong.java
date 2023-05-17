public class Main {

    public void init() {
        PingPong pingPong = new PingPong();
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        pingPong.action(PING);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        ).start();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            pingPong.action(PONG);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}

class PingPong {

    public static final String PING = "PING";
    public static final String PONG = "PONG";
    public static final int MAX_ITERATIONS = 10;
    public static final int MAX_SLEEP_MS = 500;

    private int amount = 0;
    private String lastMessage = PONG;

    public synchronized void action(String lastMessage) throws InterruptedException {
        while (amount < MAX_ITERATIONS) {
            if (this.lastMessage.equals(lastMessage)) {
                wait();
            } else {
                System.out.println(lastMessage);
                this.lastMessage = lastMessage;
                sleep(MAX_SLEEP_MS);
                notifyAll();
                amount++;
            }
        }
    }
}
