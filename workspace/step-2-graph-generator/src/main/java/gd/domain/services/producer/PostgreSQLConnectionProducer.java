package gd.domain.services.producer;

import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.steriotype.GDProducer;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@GDProducer
public class PostgreSQLConnectionProducer {

    // ENV VARS
    private static final String POSTGRESQL_URI = "GD_POSTGRESQL_CONN";
    private static final String POSTGRESQL_USER = "GD_POSTGRESQL_CONN_USER";
    private static final String POSTGRESQL_PASSWORD = "GD_POSTGRESQL_CONN_PASSWORD";

    // SERVICES
    @Autowired
    private Environment environment;

    // =========================================================================
    // JDBC
    // =========================================================================
    public DataSource datasource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(org.postgresql.Driver.class.getName());
        ds.setUrl(environment.readValue(POSTGRESQL_URI));
        ds.setUsername(environment.readValue(POSTGRESQL_USER));
        ds.setPassword(environment.readValue(POSTGRESQL_PASSWORD));

        return ds;
    }

    @Bean(name = "pgJdbc")
    @Lazy
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(datasource());
    }

    @Bean(name = "pgNamedJdbc")
    @Lazy
    public NamedParameterJdbcTemplate namedJdbcTemplate() {
        return new NamedParameterJdbcTemplate(datasource());
    }

}
