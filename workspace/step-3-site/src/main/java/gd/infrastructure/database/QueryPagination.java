package gd.infrastructure.database;

import org.neo4j.ogm.cypher.query.Pagination;

public class QueryPagination {

    private final int page;
    private final int skip;
    private final int pageSize;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    private QueryPagination(int page, int skip, int pageSize) {
        this.page = page;
        this.skip = skip;
        this.pageSize = pageSize;
    }

    // =========================================================================
    // OBJECT METHODS
    // =========================================================================
    @Override
    public String toString() {
        return String.format("page=%s, skip=%s, limit=%s", page, skip, pageSize);
    }

    // =========================================================================
    // FACTORY METHODS
    // =========================================================================
    public static QueryPagination noPagination() {
        return fromPage(0, 1000);
    }

    public static QueryPagination fromPage(int page, int pageSize) {
        // page 0 is page 1
        if (page <= 0) {
            page = 1;
        }

        // parse
        int skip = (page - 1) * pageSize;
        int limit = pageSize;
        return new QueryPagination(page, skip, limit);
    }

    // =========================================================================
    // CONVERTER
    // =========================================================================}
    public Pagination toNeo4J() {
        return new Pagination(page - 1, pageSize);
    }

    public String toNeo4JCypher() {
        return " SKIP " + skip + " LIMIT " + pageSize + " ";
    }

    // =========================================================================
    // CHECKS
    // =========================================================================
    public boolean shouldPaginate() {
        return pageSize > 0;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public int getPage() {
        return page;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return getPageSize();
    }

    public int getPageSize() {
        return pageSize;
    }
}
