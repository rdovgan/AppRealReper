package app;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Random;

/**
 *
 * @author Roma
 */
public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException {
//        try {
//            Class.forName("org.hsqldb.jdbcDriver");
//            System.err.println("Драйвер БД загружен.");
//        } catch (ClassNotFoundException e) {
//            System.err.println("НЕ удалось загрузить драйвер БД.");
//            System.exit(1);
//        }
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:hsqldb:file:db", "SA", "");
//            System.err.println("Соединение создано.");
//        } catch (SQLException e) {
//            System.err.println("НЕ удалось создать соединение.");
//            System.exit(1);
//        }
//        try {
//            Statement statement = connection.createStatement();
//            String query;
            //start create table
//            query = "CREATE TABLE strilba (ZAR VARCHAR(5) NOT NULL,DAL INTEGER NOT NULL,P INTEGER NOT NULL,DX DECIMAL(3,1) NOT NULL,VD DECIMAL(3,1),Z DECIMAL(3,1))";
//            statement.execute(query);
//            BufferedReader input = null;
//            String filename = "d:\\Диск Google\\VK\\tables\\z.txt";
//            try {
//                input = new BufferedReader(new FileReader(filename));
//            } catch (FileNotFoundException e) {
//                System.out.println("File \"" + filename + "\" not found");
//                return;
//            }
//            List<String> list = new ArrayList<String>();
//            try {
//                String tmp;
//                while ((tmp = input.readLine()) != null) {
//                    list.add(tmp);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            for (String tmp : list) {
//                String[] split = tmp.split("\t");
//                if(split.length>9){
//                    query = "INSERT INTO STRILBA VALUES('z',"+split[0]+","+split[3]+","+split[5]+","+split[6]+","+split[9]+")";
//                    statement.execute(query);
//                }
//            }
            //end create table
//            query = "SELECT zar, dal, p, dx, vd, z FROM strilba";
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2) + " "
//                        + resultSet.getInt(3) + " " + resultSet.getDouble(4) + " "
//                        + resultSet.getDouble(5)+" "+resultSet.getDouble(6));
//            }
//            query = "SHUTDOWN";
//            statement.execute(query);
//            connection.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        Spost s1,s2,s3;
        s1 = new Spost(new Random().nextInt(5));
        s2 = new Spost(new Random().nextInt(5));
        s3 = new Spost(new Random().nextInt(5));
        System.out.println(s1+"\n"+s2+"\n"+s3);
    }
}
