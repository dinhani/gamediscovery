package gd.infrastructure.graph;

import java.util.Iterator;
import java.util.List;

public class IteratorOfIterators implements Iterator<String> {

    private final List<? extends Iterator> iterators;
    private int currentIteratorIndex;

    public IteratorOfIterators(List<? extends Iterator> iterators) {
        this.iterators = iterators;
    }

    // =========================================================================
    // GETTER
    // =========================================================================
    public Iterator getCurrentClosestFirstIterator() {
        return iterators.get(currentIteratorIndex);
    }

    // =========================================================================
    // ITERATOR
    // =========================================================================
    @Override
    public boolean hasNext() {
        for (Iterator iterator : iterators) {
            if (iterator.hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String next() {
        try {
            return doGetNext();
        } finally {
            currentIteratorIndex++;
        }
    }

    private String doGetNext() {
        // restart list if reached the end
        if (currentIteratorIndex >= iterators.size()) {
            currentIteratorIndex = 0;
        }

        // try current iterator, if cannot, try next iterator
        Iterator currentIterator = iterators.get(currentIteratorIndex);
        if (currentIterator.hasNext()) {
            return (String) currentIterator.next();
        } else {
            currentIteratorIndex++;
            return doGetNext();
        }
    }

}
