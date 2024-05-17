package controller;

import model.MonsterDTO;
import model.UserDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import static controller.common.JDBCTemplate.close;
import static controller.common.JDBCTemplate.getConnection;

public class AdventureManager {
    public int checkCatchPal(UserDTO user){
        int result = 0;
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        Properties prop = new Properties();
        ResultSet rs = null;
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("countCatchPal");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getUserId());
            rs = pstmt.executeQuery();
            if(rs.next()){
                result = rs.getInt("count(*)");
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
        return result;
    }
    public MonsterDTO encounter(){
        MonsterDTO monster = null;
        ArrayList<MonsterDTO> palList = null;
        String grade = grade();
        String type = selectMap();

        if(grade.equals("전설")){
            palList = encounterLegendPal(grade);
        } else {
            palList = encounterPal(grade, type);
        }

        monster = setMonster(palList);

        return monster;
    }
    public String selectMap (){
        System.out.println("=====================");
        System.out.println("【맵을 선택해주세요】");
        System.out.println("1. 숲");
        System.out.println("2. 바다");
        System.out.println("3. 화산");
        System.out.println("=====================");
        System.out.print("※ 메뉴 선택: ");
        Scanner sc = new Scanner(System.in);
        int map = sc.nextInt();
        String type = null;
        switch (map){
            case 1 : type = "풀";break;
            case 2 : type = "물";break;
            case 3 : type = "불";break;
            default: break;
        }
        return type;
    }           //encounter
    public String grade(){
        String result = null;
        int num = (int)(Math.random() * 100 + 1);
        if(num <= 55){
            result = "일반";
        } else if(num <= 85){
            result = "희귀";
        } else {
            result = "전설";
        }
        return result;
    }                //encounter
    public ArrayList<MonsterDTO> encounterPal(String grade, String type){
        ArrayList<MonsterDTO> monList = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("encounter");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, grade);
            pstmt.setString(2, type);
            rs = pstmt.executeQuery();
            while (rs.next()){
                int palNo = rs.getInt("PAL_NO");
                String palName = rs.getString("PAL_NAME");
                String palType = rs.getString("PAL_TYPE");
                String palGrade = rs.getString("PAL_GRADE");
                monList.add(new MonsterDTO(palNo, palName, palType,palGrade));
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
        return monList;
    }        //encounter
    public ArrayList<MonsterDTO> encounterLegendPal(String grade){
        ArrayList<MonsterDTO> monList = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("encounterLegend");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, grade);
            rs = pstmt.executeQuery();
            while (rs.next()){
                int palNo = rs.getInt("PAL_NO");
                String palName = rs.getString("PAL_NAME");
                String palType = rs.getString("PAL_TYPE");
                String palGrade = rs.getString("PAL_GRADE");
                monList.add(new MonsterDTO(palNo, palName, palType,palGrade));
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
        return monList;
    }               //encounter
    public MonsterDTO setMonster(ArrayList<MonsterDTO> monList){

        MonsterDTO mon = null;

        int monListSize = monList.size();
        int monIndex = (int)(Math.random() * monListSize);

        mon = monList.get(monIndex);

        return mon;
    }                 //encounter
    public int catchProbability(MonsterDTO mon){
        int result = 0;
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("probability");
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, mon.getPalNo());
            rs = pstmt.executeQuery();
            if(rs.next()){
                result = rs.getInt("G.CHANCE");
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
        return result;
    }
    public void catchPal (MonsterDTO pal, UserDTO user){
        Scanner sc = new Scanner(System.in);
        System.out.println("【행동을 선택해주세요】");
        System.out.println("1. 잡기");
        System.out.println("2. 도망치기");
        System.out.println("=====================");
        System.out.print("※ 메뉴 선택: ");
        String act = sc.nextLine();
        if(act.equals("2")){
            System.out.println("=====================");
            System.out.println("【성공적으로 도망쳤다!】");
            return;
        }
        int randomCatchPoint = (int)(Math.random() * 101);
        if(catchProbability(pal) < randomCatchPoint){
            System.out.println("=====================");
            System.out.println("【잡지 못했다!】");
            return;
        }
        /* 잡는데 성공 */
        System.out.println("=====================");
        System.out.println("【포획에 성공하였습니다!】");
        /* 도감에 등록되어있다면 잡은횟수 +1 아닐 경우 새로 추가*/
        if(checkPalDex(pal, user)){
            increaseCatchCount(pal, user);
            insertCatchPal(pal, user);
        } else {
            insertPalDex(pal, user);
            insertCatchPal(pal, user);
            System.out.println("【도감에 " + pal.getPalName() + "이(가) 새롭게 추가되었습니다】");
        }
    }
    public boolean checkPalDex(MonsterDTO pal, UserDTO user){
        boolean result = false;
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("catchCount");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getUserId());
            pstmt.setInt(2, pal.getPalNo());
            rs = pstmt.executeQuery();
            if(rs.next()){
                result = true;
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
        return result;
    }
    public void increaseCatchCount(MonsterDTO pal, UserDTO user){
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rs2 = 0;
        Properties prop1 = new Properties();
        Properties prop2 = new Properties();
        try {
            prop1.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            prop2.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop1.getProperty("updatePalDexBook");
            String query2 = prop2.getProperty("catchCount");

            pstmt = con.prepareStatement(query2);
            pstmt.setString(1, user.getUserId());
            pstmt.setInt(2, pal.getPalNo());
            rs = pstmt.executeQuery();

            int catchCount = 0;
            if(rs.next()){
                catchCount = rs.getInt("CATCH_COUNT");
            }

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, catchCount);
            pstmt.setString(2, user.getUserId());
            pstmt.setInt(3, pal.getPalNo());

            rs2 = pstmt.executeUpdate();
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
    public void insertPalDex(MonsterDTO pal, UserDTO user){
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int rs = 0;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("insertPalDexBook");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getUserId());
            pstmt.setInt(2, pal.getPalNo());
            rs = pstmt.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertCatchPal(MonsterDTO pal , UserDTO user){
        int index = checkCatchPal(user) + 1;
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int rs = 0;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/controller/common/config/PoketPal-query.xml"));
            String query = prop.getProperty("insertCatchPal");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getUserId());
            pstmt.setInt(2, pal.getPalNo());
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