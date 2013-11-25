package fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.exception;

/**
 * @author Gregory Boissinot
 */
public class MapReducerRunnerException extends RuntimeException{

    public MapReducerRunnerException() {
    }

    public MapReducerRunnerException(String s) {
        super(s);
    }

    public MapReducerRunnerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MapReducerRunnerException(Throwable throwable) {
        super(throwable);
    }
}
