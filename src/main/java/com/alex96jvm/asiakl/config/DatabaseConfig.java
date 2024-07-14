package com.alex96jvm.asiakl.config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConfig {
    private static final DataSource dataSource;
    static {
        try {
            Properties properties = new Properties();
            try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find db.properties");
                }
                properties.load(input);
            }
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.setDriverClassName(properties.getProperty("db.driver"));
            config.setMaximumPoolSize(5);
            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }
    public static DataSource getDataSource() {
        return dataSource;
    }
}
