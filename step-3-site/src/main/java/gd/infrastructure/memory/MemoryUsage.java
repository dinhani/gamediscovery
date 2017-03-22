package gd.infrastructure.memory;

public class MemoryUsage {

    private long used;
    private long free;
    private long max;
    private int processors;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public MemoryUsage() {
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    public long getAllocated() {
        return used + free;
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getFree() {
        return free;
    }

    public void setFree(long free) {
        this.free = free;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public int getProcessors() {
        return processors;
    }

    public void setProcessors(int processors) {
        this.processors = processors;
    }

}
