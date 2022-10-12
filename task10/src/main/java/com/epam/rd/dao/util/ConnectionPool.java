package com.epam.rd.dao.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static HikariDataSource ds;

    private ConnectionPool() {}

    public static Connection getConnection() throws SQLException {
        if (ds == null) {
            ResourceBundle settings = ResourceBundle.getBundle("settings");
            setUpCP(
                    settings.getString("dbUrl"),
                    settings.getString("dbUser"),
                    settings.getString("dbPass")
            );
        }

        return ds.getConnection();
    }

    public static void setUpCustomConfiguration(String url, String usr, String pass) {
        setUpCP(url, usr, pass);
    }

    private static void setUpCP(String url, String usr, String pass) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl( url );
        config.setUsername( usr );
        config.setPassword( pass );

        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }
}
