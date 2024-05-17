package controller;

import model.UserDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static controller.common.JDBCTemplate.close;
import static controller.common.JDBCTemplate.getConnection;

public class WorkManager {
    public void work(UserDTO user) {
        Scanner sc = new Scanner(System.in);

        Connection con1 = getConnection();
        Connection con2 = getConnection();

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        ResultSet rs1 = null;
        int rs2 = 0;

        Properties prop1 = new Properties();
        Properties prop2 = new Properties();

        CheckManager checkManager = new CheckManager();
        checkManager.checkCatchPal(user);

        System.out.println("=====================");
        System.out.print("【일을 시킬 포켓펠 이름을 입력하세요】: ");
        String palName = sc.nextLine();

        try {
            prop1.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query1 = prop1.getProperty("workPal1");
            pstmt1 = con1.prepareStatement(query1);
            pstmt1.setString(1, palName);
            rs1 = pstmt1.executeQuery();

            prop2.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query2 = prop2.getProperty("workPal2");
            pstmt2 = con2.prepareStatement(query2);
            pstmt2.setString(1, palName);
            pstmt2.setString(2, user.getUserId());
            rs2 = pstmt2.executeUpdate();

            if(rs1.next()){
                if (rs1.getString("PAL_TYPE").equals("풀")) {
                    if(rs2 != 0){
                        System.out.println(palName + " 은(는) 집짓는 일을 도와주기로 했다!");
                    }
                } else if (rs1.getString("PAL_TYPE").equals("불")) {
                    if(rs2 != 0){
                        System.out.println(palName + " 은(는) 요리를 도와주기로 했다!");
                    }
                } else if (rs1.getString("PAL_TYPE").equals("물")) {
                    if(rs2 != 0){
                        System.out.println(palName + " 은(는) 농사를 하기로 했다!");
                    }
                } else if (rs1.getString("PAL_TYPE").equals("빛")) {
                    if (rs2 != 0) {
                        System.out.println(palName + " 은(는) 펠들을 감독하기로 했다!");
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs1);
            close(pstmt1);
            close(pstmt2);
            close(con1);
            close(con2);
        }


    }



}