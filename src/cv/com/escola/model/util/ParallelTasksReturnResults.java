package cv.com.escola.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

 @Slf4j
public class ParallelTasksReturnResults {

    private static final ExecutorService THREADLAUNCHER = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final List<Callable<Object>> callableList = new ArrayList<>();

    public void addCallable(Callable<Object> callable) {
        this.callableList.add(callable);
    }

    public void clearCallables() {
        this.callableList.clear();
    }

    public void executeThreads() {
        try {
            THREADLAUNCHER.invokeAll(this.callableList);
        } catch (InterruptedException ex) {
            log.warn("Interrupted!: ", ex);
            Thread.currentThread().interrupt();
        }
    }

    public Object[] getResult() {
        List<Future<Object>> resultList = new ArrayList<>();
        Object[] resultArray = null;
        try {
            resultList.add((Future<Object>) THREADLAUNCHER.invokeAll(this.callableList));
            resultArray = new Object[resultList.size()];
            for (int i = 0; i < resultList.size(); i++) {
                resultArray[i] = resultList.get(i).get();
            }
        } catch (InterruptedException | ExecutionException ex) {
            log.warn("Interrupted!: ", ex);
            Thread.currentThread().interrupt();
        }
        return resultArray;
    }
}
