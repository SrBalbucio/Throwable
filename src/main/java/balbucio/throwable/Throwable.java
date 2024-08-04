package balbucio.throwable;

import balbucio.throwable.model.ThrowableReturn;
import balbucio.throwable.model.ThrowableRunnable;

import java.io.FileWriter;
import java.util.Optional;
import java.util.logging.Logger;

public class Throwable {

    /**
     * Nothing will come out in the console if an error occurs.
     *
     * @param run
     */
    public static void silently(ThrowableRunnable run) {
        try {
            run.run();
        } catch (Exception ignored) {
        }
    }

    /**
     * Nothing will come out in the console if an error occurs.
     * Will execute another runnable if an error occurs.
     *
     * @param run
     * @param onError
     */
    public static void silentlyAnd(ThrowableRunnable run, ThrowableRunnable onError) {
        try {
            run.run();
        } catch (Exception ignored) {
            try {
                onError.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Will close the application if an error occurs.
     *
     * @param run
     * @param code
     */
    public static void closeIfThrow(ThrowableRunnable run, int code) {
        try {
            run.run();
        } catch (Exception e) {
            System.exit(code);
        }
    }

    /**
     * It will print if an error occurs.
     *
     * @param run
     */
    public static void printThrow(ThrowableRunnable run) {
        try {
            run.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * It will only print the error message on your Java Logger.
     *
     * @param run
     * @param logger
     */
    public static void throwInLogger(ThrowableRunnable run, Logger logger) {
        try {
            run.run();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * If the Exception class is this, print the error.
     *
     * @param run
     * @param clazz
     */
    public static void throwIfInstanceOf(ThrowableRunnable run, Class<Exception> clazz) {
        try {
            run.run();
        } catch (Exception e) {
            if (clazz.isAssignableFrom(e.getClass())) {
                e.printStackTrace();
            }
        }
    }

    /**
     * It will print the error if the condition is positive.
     *
     * @param run
     * @param con
     */
    public static void throwIf(ThrowableRunnable run, boolean con) {
        try {
            run.run();
        } catch (Exception e) {
            if (con) {
                e.printStackTrace();
            }
        }
    }

    /**
     * It will print the error and execute the runnable in case of error.
     *
     * @param run
     * @param onError
     */
    public static void throwAnd(ThrowableRunnable run, ThrowableRunnable onError) {
        try {
            run.run();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                onError.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * It won't print anything, but it will save the error in a .log file.
     *
     * @param run
     */
    public static void throwToFile(ThrowableRunnable run) {
        try {
            run.run();
        } catch (Exception ex) {
            silently(() -> {
                FileWriter writer = new FileWriter("throw-" + ex.getClass().getName() + "-" + System.currentTimeMillis() + ".log");
                writer.append(ex.getMessage()).append("\n");
                writer.append(ex.toString());
                writer.flush();
                writer.close();
            });
        }
    }

    /**
     * Executes code that can return an Object, in case of error it will return an empty Optional.
     *
     * @param run
     * @return
     */
    public static <T> Optional<T> silentlyOptional(ThrowableReturn<T> run) {
        Optional<T> opti = Optional.empty();

        try {
            opti = Optional.of(run.get());
        } catch (Exception ignored) {
        }

        return opti;
    }

    /**
     * Executes code that can return an Object, in case of error it will return an empty Optional.
     * And if there is an error, Runnable will be executed.
     *
     * @param run
     * @param onError
     * @return
     */
    public static <T> Optional<T> silentlyOptional(ThrowableReturn<T> run, ThrowableRunnable onError) {
        Optional<T> opti = Optional.empty();

        try {
            opti = Optional.of(run.get());
        } catch (Exception ignored) {
            try {
                onError.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return opti;
    }

    /**
     * Executes code that can return an Object, in case of error it will return an empty Optional.
     * And if there is an error is printed.
     *
     * @param run
     * @return
     */
    public static <T> Optional<T> throwOptional(ThrowableReturn<T> run) {
        Optional<T> opti = Optional.empty();

        try {
            opti = Optional.of(run.get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return opti;
    }

    /**
     * Utilize sleep without having to approach approaches.
     *
     * @param millis
     */
    public static void threadSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Runnable silentlyRunnable(ThrowableRunnable rr){
        return () -> {
            try {
                rr.run();
            } catch (Exception ignored) {}
        };
    }

    public static Runnable throwRunnable(ThrowableRunnable rr){
        return () -> {
            try {
                rr.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
