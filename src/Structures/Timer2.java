package Structures;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @author Marlon
 */


public class Timer {
    private Text timerText;
    private HBox timerPane = new HBox();
    private volatile boolean isRunning;
    private Thread worker;
    private int time;
    private int internalTime;
    /**
     * Constructor
     * @param time establece la duracion del temporizador
     * @param fontSize establece el tamaño de la letra
     */

    public Timer(int time, int fontSize, Text timerText){
        this.time = time;
        this.isRunning = false;
        this.timerText = timerText;
        timerText.setFont(Font.font(null, FontWeight.BOLD, fontSize));
        timerPane.getChildren().add(timerText);
        timerPane.setAlignment(Pos.CENTER);
        timerText.setStyle("-fx-text-fill: white");
    }

    /**
     * Añade el temporizador a un panel especificado
     * @param root establece el panel donde se va a añadir el temporizador
     * @param col En que columna se va a insertar
     * @param row En que fila se va a insertar
     * @param colSpan Establece el colSpan
     * @param rowSpan Establece el rowSpan
     */

    public void addTimer(GridPane root, int col, int row, int colSpan, int rowSpan){
        root.add(timerPane, col, row, colSpan, rowSpan);
    }

    /**
     * Inicia el temporizador
     */
    public void startTimer(){
        if (worker != null) worker.interrupt();
        internalTime = time;
        isRunning = true;
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                isRunning = true;
                while (internalTime >= 0){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timerText.setText(Integer.toString(internalTime));
                            timerText.setStyle("-fx-text-fill: white");
                        }
                    });
                    Thread.sleep(1000);
                    internalTime--;
                }
                isRunning = false;
                System.out.println("Timer has stopped");
                return null;
            }
        };
        worker = new Thread(task);
        worker.setDaemon(true);
        worker.start();
    }

    /**
     * Detiene el temporizador
     */
    public void stopTimer() {
        if (worker != null) {
            worker.interrupt();
        }
        timerText.setText(Integer.toString(time));

        isRunning = false;
    }

    /**
     * Se obtiene el estado del temporizador, si esta en corriendo o no
     * @return un booleano con el estado del temporizador
     */

    public boolean isRunning(){
        return this.isRunning;
    }

    public HBox getTimerPane() {
        return timerPane;
    }
}
