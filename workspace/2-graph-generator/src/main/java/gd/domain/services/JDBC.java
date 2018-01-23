package gd.domain.services;

import com.google.common.collect.Sets;
import gd.domain.entities.Node;
import gd.infrastructure.serialization.Json;
import gd.infrastructure.steriotype.GDService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@GDService
public class JDBC {

    @Autowired
    private Json json;

    @Autowired
    @Qualifier(value = "pgJdbc")
    private JdbcTemplate jdbc;

    // =========================================================================
    // MAIN QUERY
    // =========================================================================
    public JDBCQuery query() {
        return new JDBCQuery(this, json);
    }

    public Set<Node> executeQuery(String sql) {        
        return Sets.newHashSet(jdbc.query(sql, BeanPropertyRowMapper.newInstance(Node.class)));
    }
}
