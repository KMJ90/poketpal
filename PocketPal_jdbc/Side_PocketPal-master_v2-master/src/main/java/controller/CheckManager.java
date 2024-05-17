package controller;

import model.UserDTO;

import javax.swing.plaf.PanelUI;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static controller.common.JDBCTemplate.close;
import static controller.common.JDBCTemplate.getConnection;


public class CheckManager {
    public void check(UserDTO user) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("checkPalDex");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,user.getUserId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("=====================");
                System.out.print("[NO. " + rs.getString("PAL_NO") + "]");
                System.out.print("[이름 : " + rs.getString("PAL_NAME") + "]");
                System.out.print("[발견 시간 : " + rs.getString("DISCOVERY_TIME") + "]");
                System.out.print("[잡은 횟수 : " + rs.getString("CATCH_COUNT") + "]\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
    }

    public void checkCatchPal(UserDTO user) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("checkCatchPal");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,user.getUserId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("=====================");
                System.out.print("[NO. " + rs.getString("PAL_NO") + "]");
                System.out.print("[이름 : " + rs.getString("PAL_NAME") + "]\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }

    }

}