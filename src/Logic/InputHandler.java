package Logic;

public class InputHandler {
    private String shootKey;
    private double speed;
    private Controller controller;

    private boolean start;

    public InputHandler(String shootKey, Controller instance) {
        this.shootKey = shootKey;
        this.speed = 1;
        this.controller = instance;
        this.start = false;
    }

    public void setShootKey(String shootKey) {
        this.shootKey = shootKey;
    }

    public String getShootKey() {
        return this.shootKey;
    }

    public void setStart(String key) {
        if (key.equals(shootKey)) {
            if (!this.start) {
                this.start = true;
                startThread();
            }
        }
    }

    public void setEnd(String key){
        if (key.equals(shootKey)) {
            this.start = false;
            this.controller.shootMissile(this.speed);
            this.speed = 1;
        }
    }

    private void startThread(){

        Thread thread = new Thread(()->{
            System.out.println("Starting thread of input...");
            while(start){
                if (speed < 25) {
                    speed += 0.25;
                    System.out.println("Speed " + speed);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

}

