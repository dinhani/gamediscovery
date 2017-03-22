package gd.infrastructure.memory;

import gd.infrastructure.steriotype.GDService;
import javax.annotation.PostConstruct;

@GDService
public class Memory {

    private Runtime runtime;

    @PostConstruct
    public void init() {
        runtime = Runtime.getRuntime();
    }

    public void gc() {
        runtime.gc();
    }

    public MemoryUsage getStatistics() {
        MemoryUsage memoryUsage = new MemoryUsage();
        memoryUsage.setUsed((runtime.totalMemory() / 1024) / 1024);
        memoryUsage.setFree((runtime.freeMemory() / 1024) / 1024);
        memoryUsage.setMax((runtime.maxMemory()) / 1024 / 1024);
        memoryUsage.setProcessors(runtime.availableProcessors());

        return memoryUsage;
    }
}
