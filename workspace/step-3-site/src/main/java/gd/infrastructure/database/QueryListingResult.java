package gd.infrastructure.database;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.collections4.CollectionUtils;

public class QueryListingResult<T> {

    private Collection<T> elements = Collections.EMPTY_LIST;
    private QueryPagination pagination = QueryPagination.noPagination();
    private Optional<Number> totalRecords = Optional.absent();

    private QueryListingResult() {

    }

    // =========================================================================
    // BUILDER
    // =========================================================================
    public static QueryListingResult empty() {
        QueryListingResult result = new QueryListingResult();
        return result;
    }

    public static QueryListingResult create(Iterable elements) {
        QueryListingResult result = new QueryListingResult();
        result.elements = Lists.newArrayList();
        CollectionUtils.addAll(result.elements, elements);
        return result;
    }

    public static QueryListingResult create(Object[] elements) {
        QueryListingResult result = new QueryListingResult();
        result.elements = Lists.newArrayList();
        CollectionUtils.addAll(result.elements, elements);
        return result;
    }

    public QueryListingResult withPagination(QueryPagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public QueryListingResult withTotalRecords(Number totalRecords) {
        this.totalRecords = Optional.of(totalRecords);
        return this;
    }

    // =========================================================================
    // CHECKS
    // =========================================================================
    public boolean isPaginated() {
        return totalRecords.isPresent();
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public Collection<T> getElements() {
        return elements;
    }

    public QueryPagination getPagination() {
        return pagination;
    }

    public Optional<Number> getTotalRecords() {
        return totalRecords;
    }
}
