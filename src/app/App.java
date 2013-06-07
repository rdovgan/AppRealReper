package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import javax.swing.JFrame;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 *
 * @author Roma
 */
public class App {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
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
// //           start create table
////            query = "CREATE TABLE popr (ZAR VARCHAR(5) NOT NULL, P INTEGER NOT NULL, E INTEGER NOT NULL, H VARCHAR(2) NOT NULL, Z INTEGER)";
////            statement.execute(query);
//            BufferedReader input = null;
//            String filename = "d:\\Диск Google\\VK\\tables\\table.txt";
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
//            int e = 10;
//            for (String tmp : list) {
//                String[] split = tmp.split("\t");
//                int n = split.length;
//                int p = 20;
//                for(int i=0; i<n; i++){
//                    query = "INSERT INTO POPR VALUES('4',"+p+","+e+",'-',"+split[i]+")";
//                    statement.execute(query);
//                    p += 20;
//                }
//                e += 10;
//            }
//  //          end create table
//            query = "SELECT zar, p, e, h, z FROM popr";
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2) + " "
//                        + resultSet.getInt(3) + " " + resultSet.getString(4) + " "
//                        + resultSet.getInt(5));
//            }
//            query = "SHUTDOWN";
//            statement.execute(query);
//            connection.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//DOC start
//        XWPFDocument document = new XWPFDocument();
//        XWPFParagraph para = document.createParagraph();
//        para.setAlignment(ParagraphAlignment.CENTER);
//        XWPFRun run = para.createRun();
//        run.setBold(true);
//        run.setFontSize(36);
//        run.setText("Apache POI works well!");
//        XWPFParagraph para2 = document.createParagraph();
//        run = para2.createRun();
//        run.setText("\tApache POI is a Java library for working with MS Office documents."
//                + " Apache POI has a mature interface to handle MS Excel files."
//                + " The libraries for working with word and powerpoint files is sufficient, but evolving.");
//        //Creates a table
//        XWPFTable tab = document.createTable();
//        XWPFTableRow row = tab.getRow(0);
//        row.getCell(0).setText("Sl. No.");
//        row.addNewTableCell().setText("Name");
//        row.addNewTableCell().setText("Address");
//
//        row = tab.createRow();
//        row.getCell(0).setText("1.");
//        row.getCell(1).setText("Raman");
//        row.getCell(2).setText("Pondicherry");
//
//        document.write(new FileOutputStream("1.docx"));
        //DOC end
        JFrame j = new MainFrame();
        j.setVisible(true);
    }
}
