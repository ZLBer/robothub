package edu.hust.robothub.core.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.hust.robothub.core.until.Config;

import java.sql.Connection;
import java.sql.SQLException;


//此处用hikariCP作为连接池
public class MysqlStorage extends AbstractStorage<String>{
    private final String MYSQL_URL = "jdbc:mysql://localhost:3306/crm?characterEncoding=UTF-8";
    private final String MYSQL_USER = "root";
    private final String MYSQL_PASSWORD = "root";

    HikariDataSource dataSource;

    public MysqlStorage(Config config) {
        super(config);
    }

    @Override
    public void start() {
        //实例化类
        HikariConfig hikariConfig = new HikariConfig();
        //设置url
        hikariConfig.setJdbcUrl(MYSQL_URL);
        //数据库帐号
        hikariConfig.setUsername(MYSQL_USER);
        //数据库密码
        hikariConfig.setPassword(MYSQL_PASSWORD);
        dataSource= new HikariDataSource(hikariConfig);

    }

    @Override
    public void storage(String sql) {
        try {
            Connection connection = dataSource.getConnection();
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
     dataSource.close();
    }
}
