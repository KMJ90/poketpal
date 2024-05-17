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

public class AccountManager {
    Scanner sc = new Scanner(System.in);
    public UserDTO login (){
        UserDTO result = null;
        System.out.println("=====================");
        System.out.print("ID: ");
        String userId = sc.nextLine();
        System.out.print("PASSWORD: ");
        String userPwd = sc.nextLine();

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("login");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPwd);
            rs = pstmt.executeQuery();
            if(rs.next()){
                result = new UserDTO();
                result.setUserId(rs.getString("USER_ID"));
                result.setUserPwd(rs.getString("USER_PWD"));
                result.setUserName(rs.getString("USER_NAME"));
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

        if(result == null){
            System.out.println("=====================");
            System.out.println("【일치하는 계정이 없습니다.】");
        } else {
            System.out.println("=====================");
            System.out.println("【로그인 되었습니다.】");
        }
        return result;
    }
    public void register(){
        System.out.println("===== 회원가입을 시작합니다 =====");
        System.out.print("아이디를 입력해주세요: ");
        String userId = sc.nextLine();
        while (userId.length() > 15){
            System.out.println("=====================");
            System.out.println("【아이디는 15자 이내로 입력해주세요.】");
            userId = sc.nextLine();
        }
        System.out.print("비밀번호를 입력해주세요: ");
        String userPwd = sc.nextLine();
        while (userPwd.length() > 15){
            System.out.println("=====================");
            System.out.println("【비밀번호는 15자 이내로 입력해주세요.】");
            userPwd = sc.nextLine();
        }
        System.out.print("이름을 입력해주세요: ");
        String userName = sc.nextLine();
        while (userName.length() > 7){
            System.out.println("=====================");
            System.out.println("【이름은 7자 이내로 입력해주세요.】");
            userName = sc.nextLine();
        }

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int rs = 0;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("register");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPwd);
            pstmt.setString(3, userName);
            rs = pstmt.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
    }
}