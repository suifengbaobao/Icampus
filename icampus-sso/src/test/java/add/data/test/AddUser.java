package add.data.test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Scanner;

public class AddUser {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // 数据库驱动地址
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/icampus?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true"; // 数据库地址
    private static final String USER = "baobao"; // 用户名
    private static final String PASSWORD = "19951209"; // 密码
    private static Connection conn = null; // 数据库链接对象
    private static PreparedStatement pstat = null; // 处理数据库对象
    private static String USER_DATA_URL = "E:\\icampus\\data\\card-C.txt";
    private static FileInputStream fis = null;
    private static Scanner scanner = null;
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
        String sql = "insert ic_user(username,password,sex,birthday,school,email,hobby,signature,image,type,created,updated) values(?,md5(?),?,?,?,?,?,?,?,?,?,?)";
        try {
            fis =  new FileInputStream(USER_DATA_URL);
            scanner = new Scanner(fis,"gb2312");
            getConn().setAutoCommit(false);
            pstat = getConn().prepareStatement(sql);
            while(scanner.hasNext()){
                String data = scanner.nextLine();
                String[] columns = data.split("&");
                for(int i = 0; i <= columns.length; i++){
                    if(i==4){
                        pstat.setString(5, "东北林业大学");
                        continue;
                    }else if(i == 1){
                        pstat.setString(i + 1, columns[i]);
                    }else if(i < 4 && i != 1){ 
                        pstat.setObject(i+1, columns[i]);
                    }else{
                        pstat.setObject(i+1, columns[i-1]);
                    }
                }
                pstat.setObject(10, "普通会员");
                pstat.setObject(11, new Date());
                pstat.setObject(12, new Date());
                pstat.addBatch();
            }
            int[] counts = pstat.executeBatch();
            getConn().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
