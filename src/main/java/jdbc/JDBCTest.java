package jdbc;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCTest {
    public static void main(String[] args) throws SQLException {
        /******* Statement **********/
        // 1、2部可以封装成工具类
        // 1. 向驱动管理器注册厂商提供的驱动器
        DriverManager.registerDriver(new Driver());
        // 2. 建立连接  底层是通过TCP/IP连接的
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crashcourse", "root", "1995");
        // 3. 创建语句
        Statement statement = connection.createStatement();
        // 4. 执行语句
        ResultSet resultSet = statement.executeQuery("select * from orderitems");
        // 5. 处理结果
        while(resultSet.next()) {
            System.out.println(resultSet.getObject(1) + "\t"
                    + resultSet.getObject(2) + "\t"
                    + resultSet.getObject(3) + "\t");
        }

        System.out.println("PreparedStatement.......");

        /******* PreparedStatement **********/
        PreparedStatement preparedStatement = connection.prepareStatement("select * from products where prod_id = ?");
        preparedStatement.setString(1, "ANV01");
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            System.out.println(resultSet.getObject(1) + "\t"
                    + resultSet.getObject(2) + "\t"
                    + resultSet.getObject(3) + "\t");
        }


        System.out.println("\nmaps");
        List<Map<String, Object>> resList = query("select * from orderitems", connection);
        for (Map<String, Object> map : resList) {
            System.out.println(map);
        }

        connection.close();
    }

    // 用Map代表一个结果，用List返回的模版
    // 目前版本不算很好，因为要调用者传递connection，另外，并没有确切的对象
    static List<Map<String, Object>> query(String sql, Connection connection) throws SQLException {
        // 3. 准备sql
        Statement statement = connection.createStatement();

        // 4. 读取结果
        ResultSet resultSet = statement.executeQuery(sql);

        // 5. 处理结果成map
        Map<String, Object> rsMap = null;
        List<Map<String, Object>> list = new ArrayList<>();
        // 5.1 先知道结果集元数据，可以借此获得列名、类型、数量
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int colNum = rsMetaData.getColumnCount();
        while (resultSet.next()) {
            rsMap = new HashMap<String, Object>();
            for (int i=1; i <= colNum; i++) {
                rsMap.put(rsMetaData.getColumnName(i), resultSet.getObject(i));
            }
            list.add(rsMap);
        }
        return list;
    }
}
