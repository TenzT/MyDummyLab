package jdbc;

import com.mysql.jdbc.Driver;
import org.junit.runners.model.InitializationError;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class MyDataSource {
    private static final String URL = "jdbc:mysql://localhost:3306/crashcourse";
    private static final String USRNAME = "root";
    private static final String PASSWD = "1995";
    private static final int INIT_SIZE = 5;
    private static final int MAX_SIZE = 10;

    private static int currentCount = 0;

    private LinkedList<Connection> connectionPool = new LinkedList<>();

    static {
        // 注册驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MyDataSource() {
        this(INIT_SIZE);
    }

    public MyDataSource(int size) throws IllegalArgumentException {
        if (size > MAX_SIZE || size <= 0) {
            throw new IllegalArgumentException("Invalid Size");
        }

        // 向连接池插入连接
        try {
            for (int i=0; i<size; i++) {
                connectionPool.addLast(this.createConnection());
                currentCount++;
            }
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // 出队表示拿出连接
    public synchronized Connection getConnection() throws SQLException {
        if (connectionPool.size() > 0) {
            return connectionPool.removeFirst();
        } else if (this.currentCount < MAX_SIZE){
            return this.createConnection();
        } else {
            throw new SQLException("Pool empty");
        }
    }

    // 入队表示释放连接
    public synchronized void freeConnection(Connection connection) {
        connectionPool.addLast(connection);
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USRNAME, PASSWD);
    }

}
