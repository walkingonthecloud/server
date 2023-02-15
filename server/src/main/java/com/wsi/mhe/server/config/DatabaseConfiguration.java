package com.wsi.mhe.server.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

import javax.sql.DataSource;

@Data
public class DatabaseConfiguration {

    private String url;
    private String username;
    private String driver;
    private String password;
    private String schema;
    private Integer minimumIdle;
    private Integer maximumPoolSize;
    private Long idleTimeout;
    private Long maxLifetime;
    private Long connectionTimeout;
    private String poolName;
    private String connectionInitSql;

    /**
     * Creates the data source.
     *
     * @return the data source
     */
    public DataSource createDataSource() {
        var ds = new HikariDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setJdbcUrl(url);
        ds.setSchema(schema);
        ds.setDriverClassName(driver);
        ds.setAutoCommit(true);
        if (minimumIdle != null)
            ds.setMinimumIdle(minimumIdle);
        else
            ds.setMinimumIdle(1);
        if (maximumPoolSize != null)
            ds.setMaximumPoolSize(maximumPoolSize);
        else
            ds.setMaximumPoolSize(2);
        if (idleTimeout != null)
            ds.setIdleTimeout(idleTimeout);
        else
            ds.setIdleTimeout(10000);
        if (maxLifetime != null)
            ds.setMaxLifetime(maxLifetime);
        else
            ds.setMaxLifetime(20000);
        if (connectionTimeout != null)
            ds.setConnectionTimeout(connectionTimeout);
        else
            ds.setConnectionTimeout(20000);
        if (poolName != null)
            ds.setPoolName(poolName);
        else
            ds.setPoolName("wmos-jdbc-service-pool");
        if (connectionInitSql != null)
            ds.setConnectionInitSql(connectionInitSql);
        else
            ds.setConnectionInitSql("SELECT 1 FROM DUAL");
        return ds;
    }
}
