package add.data.test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class AddSouvenir {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // 数据库驱动地址
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/icampus?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true"; // 数据库地址
    private static final String USER = "baobao"; // 用户名
    private static final String PASSWORD = "19951209"; // 密码
    private static Connection conn = null; // 数据库链接对象
    private static PreparedStatement pstat = null; // 处理数据库对象
    private static String USER_DATA_URL = "E:\\icampus\\data\\souvenir-C.txt";
    private static FileInputStream fis = null;
    private static Scanner scanner = null;
    private static Random rand = new Random();
    public static Connection getConn(){
        try {
            Class.forName(JDBC_DRIVER);
            if(conn == null){
                conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void main(String[] args){
        String sql = "insert ic_item(title,description,content,price,nums,image,status,created,updated) values(?,?,?,?,?,?,?,?,?)";
        try {
            fis =  new FileInputStream(USER_DATA_URL);
            scanner = new Scanner(fis,"gb2312");
            getConn().setAutoCommit(false);
            pstat = getConn().prepareStatement(sql);
            while(scanner.hasNext()){
                String data = scanner.nextLine();
                String[] columns = data.split("&");
                String title = columns[0];
                String description = columns[1];
                String price = columns[2];
                String image = columns[3];
                pstat.setObject(1, title);
                pstat.setObject(2, description);
                pstat.setObject(3, null);
                pstat.setObject(4, price);
                pstat.setObject(5, 1000);
                pstat.setObject(6, image);
                pstat.setObject(7, 0);
                pstat.setObject(8, new Date());
                pstat.setObject(9, new Date());
                
                pstat.addBatch();
            }
            int[] counts = pstat.executeBatch();
            getConn().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
