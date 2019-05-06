package entity;

public class ScriptExecutionTimer implements ExecutionTimer {

    private long startTime;
    private long stopTime;

    @Override
    public void startCounter() {
        startTime = System.nanoTime();

    }

    @Override
    public void endCounter() {
        stopTime = System.nanoTime();
    }

    @Override
    public double elapsedTime() {
        double elapsedTime = (double) (stopTime - startTime);
        return elapsedTime;
    }

}
