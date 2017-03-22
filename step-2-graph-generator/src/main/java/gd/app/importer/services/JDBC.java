package gd.app.importer.repository;

import com.google.common.collect.Sets;
import gd.app.importer.model.Node;
import gd.infrastructure.serialization.Json;
import gd.infrastructure.steriotype.GDService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@GDService
public class DataImporterJDBC {

    @Autowired
    private Json json;

    @Autowired
    @Qualifier(value = "pgJdbc")
    private JdbcTemplate jdbc;

    // =========================================================================
    // MAIN QUERY
    // =========================================================================
    public DataImporterJDBCQuery query() {
        return new DataImporterJDBCQuery(this, json);
    }

    public Set<Node> executeQuery(String sql) {
        //System.out.println(sql);
        return Sets.newHashSet(jdbc.query(sql, BeanPropertyRowMapper.newInstance(Node.class)));
    }
}
