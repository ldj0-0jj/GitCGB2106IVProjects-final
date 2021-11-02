package com.jt.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DataSourceTests {
    /**
     * DataSource为Java中一个数据源规范，所有连接池的设计
     * 需要需要遵守此规范，我们获取链接可以通过此对象获取。
     * 假如项目中添加了连接池依赖，spring底层会自动创建此对象
     */
    @Autowired
    private DataSource dataSource;//HikariDataSource
    @Test
    void testGetConnection() throws SQLException {
        Connection connection =
                dataSource.getConnection();
        System.out.println(connection);
    }
}
