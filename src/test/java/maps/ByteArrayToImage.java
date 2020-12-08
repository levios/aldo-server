//package maps;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.*;
//import java.sql.*;
//import javax.imageio.ImageIO;
//
//import static maps.EmployeeController.decompressBytes;
//
//public class ByteArrayToImage {
//    public static void main(String args[]) throws Exception {
//        System.out.println("start");
//        readPicture();
//        System.out.println("end");
//    }
//
//
//    private static Connection connect() {
//        // SQLite connection string
//        String url = "jdbc:sqlite:F://Programming/jre/maps/scraper.db1.sqlite";
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(url);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return conn;
//    }
//
//
//    /**
//     * read the picture file and insert into the material master table
//     */
//    public static void readPicture() {
//        // update sql
//        String selectSQL = "SELECT pic_byte FROM image";
//        ResultSet rs = null;
//        FileOutputStream fos = null;
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            conn = connect();
//            pstmt = conn.prepareStatement(selectSQL);
//            //pstmt.setInt(1, materialId);
//            rs = pstmt.executeQuery();
//
//            // write binary stream into file
//            File file = new File("F:\\Pictures\\VIOLA\\new\\output.jpg");
//            fos = new FileOutputStream(file);
//
//            System.out.println("Writing BLOB to file " + file.getAbsolutePath());
//            while (rs.next()) {
//                InputStream input = rs.getBinaryStream("pic_byte");
//                byte[] targetArray = new byte[input.available()];
//                input.read(targetArray);
//                byte[] deco = decompressBytes(targetArray);
//                fos.write(deco);
//            }
//        } catch (SQLException | IOException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//
//                if (conn != null) {
//                    conn.close();
//                }
//                if (fos != null) {
//                    fos.close();
//                }
//
//            } catch (SQLException | IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//}