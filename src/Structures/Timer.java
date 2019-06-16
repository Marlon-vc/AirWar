package Structures;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Timer {

    private int time;
    private int internalTime;
    private Label timerLabel;
    private Thread worker;

    public Timer(int time, Label timerLabel){
        this.time = time;
        this.timerLabel = timerLabel;
    }

    public void startTimer(){
        internalTime = time;
        worker = new Thread(()->{
            while(internalTime>=0){
                Platform.runLater(()->{
                    timerLabel.setText(internalTime + "s");
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                internalTime--;
            }
        });

        worker.setDaemon(true);
        worker.start();

    }


    public void stopTimer() {
        if (worker != null) {
            worker.interrupt();
        }
        timerLabel.setText(Integer.toString(time));
    }
}
