package balbucio.throwable;

import balbucio.throwable.runnables.ThrowableReturn;
import balbucio.throwable.runnables.ThrowableRunnable;
import java.io.FileWriter;
import java.util.Optional;
import java.util.logging.Logger;

public class Throwable {

    /**
     * Nothing will come out in the console if an error occurs.
     * @param run
     */
    public static void silently(ThrowableRunnable run){
        try{
            run.run();
        } catch (Exception ignored){}
    }

    /**
     * Nothing will come out in the console if an error occurs.
     * Will execute another runnable if an error occurs.
     * @param run
     * @param onError
     */
    public static void silentlyAnd(ThrowableRunnable run, Runnable onError){
        try{
            run.run();
        } catch (Exception ignored){
            onError.run();
        }
    }

    /**
     * Will close the application if an error occurs.
     * @param run
     * @param code
     */
    public static void closeIfThrow(ThrowableRunnable run, int code){
        try{
            run.run();
        } catch (Exception e){
            System.exit(code);
        }
    }

    /**
     * It will print if an error occurs.
     * @param run
     */
    public static void printThrow(ThrowableRunnable run){
        try{
            run.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * It will only print the error message on your Java Logger.
     * @param run
     * @param logger
     */
    public static void throwInLogger(ThrowableRunnable run, Logger logger){
        try{
            run.run();
        } catch (Exception e){
            logger.severe(e.getMessage());
        }
    }

    /**
     * If the Exception class is this, print the error.
     * @param run
     * @param clazz
     */
    public static void throwIfInstanceOf(ThrowableRunnable run, Class<Exception> clazz){
        try{
            run.run();
        } catch (Exception e){
            if(clazz.isAssignableFrom(e.getClass())){
                e.printStackTrace();
            }
        }
    }

    /**
     * It will print the error if the condition is positive.
     * @param run
     * @param con
     */
    public static void throwIf(ThrowableRunnable run, boolean con){
        try{
            run.run();
        } catch (Exception e){
            if(con) {
                e.printStackTrace();
            }
        }
    }

    /**
     * It will print the error and execute the runnable in case of error.
     * @param run
     * @param onError
     */
    public static void throwAnd(ThrowableRunnable run, Runnable onError){
        try{
            run.run();
        }catch (Exception e){
            e.printStackTrace();
            onError.run();
        }
    }

    /**
     * It won't print anything, but it will save the error in a .log file.
     * @param run
     */
    public static void throwToFile(ThrowableRunnable run){
        try{
            run.run();
        } catch (Exception ex){
            silently(() -> {
                FileWriter writer = new FileWriter("throw-"+ex.getClass().getName()+"-"+System.currentTimeMillis()+".log");
                writer.append(ex.getMessage()).append("\n");
                writer.append(ex.toString());
                writer.flush();
                writer.close();
            });
        }
    }

    /**
     * Executes code that can return an Object, in case of error it will return an empty Optional.
     * @param run
     * @return
     */
    public static <T> Optional<T> returnOptionalOrSilently(ThrowableReturn<T> run){
        Optional<T> opti = null;

        try{
            opti = Optional.of(run.get());
        } catch (Exception ignored){}

        return opti;
    }

    /**
     * Executes code that can return an Object, in case of error it will return an empty Optional.
     * And if there is an error, Runnable will be executed.
     * @param run
     * @param onError
     * @return
     */
    public static <T> Optional<T> returnOptionalOrSilently(ThrowableReturn<T> run, Runnable onError){
        Optional<T> opti = null;

        try{
            opti = Optional.of(run.get());
        } catch (Exception ignored){
            onError.run();
        }

        return opti;
    }

    /**
     * Executes code that can return an Object, in case of error it will return an empty Optional.
     * And if there is an error is printed.
     * @param run
     * @return
     */
    public static <T> Optional<T> returnOptionalOrThrow(ThrowableReturn<T> run){
        Optional<T> opti = null;

        try{
            opti = Optional.of(run.get());
        } catch (Exception ignored){
            ignored.printStackTrace();
        }

        return opti;
    }
}
