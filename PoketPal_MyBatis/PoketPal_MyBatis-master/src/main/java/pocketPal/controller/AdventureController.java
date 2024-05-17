package pocketPal.controller;

import org.apache.ibatis.session.SqlSession;
import pocketPal.common.model.dao.MonsterDAO;
import pocketPal.common.model.dto.MonsterDTO;
import pocketPal.common.model.dto.UserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static pocketPal.common.Template.getSqlSession;

public class AdventureController {
    private MonsterDAO mapper;
    Scanner sc = new Scanner(System.in);

    public void adventure(UserDTO user) {
        if(!checkHavenPal(user)){
            System.out.println("보유중인 펠은 6마리를 넘길 수 없습니다. 마을에서 일을 시켜주세요.");
            return;
        }
        String type = selectMap();
        String grade = setGrade();

        List<MonsterDTO> monsterList = setInfo(grade, type);

        MonsterDTO thisPal = setMonster(monsterList);

        System.out.println("====야생의 펠이 나타났다! ====");
        System.out.println(thisPal);

        System.out.println("==== 행동을 선택해주세요. ====");
        System.out.println("1. 잡기 \n2. 도망치기");
        int action = sc.nextInt();
        if(action == 2){
            System.out.println("==== 무사히 도망쳤다! ====");
            return;
        }
        boolean catchResult = catchPal(thisPal);
        if(catchResult){
            System.out.println("==== 포획에 성공했다! ====");
            insertPal(thisPal, user);
        } else {
            System.out.println("==== 포획에 실패했다! ====");
        }


    }

    private void insertPal(MonsterDTO thisPal, UserDTO user) {
        Map<String, String> userIdAndPalNo = new HashMap<>();
        userIdAndPalNo.put("userId", user.getUserId());
        userIdAndPalNo.put("palNo", thisPal.getPalNo()+"");
        SqlSession sqlSession = getSqlSession();
        mapper = sqlSession.getMapper(MonsterDAO.class);
        int catchCount = mapper.getCatchCount(userIdAndPalNo);
        int insertResult = mapper.insertNewPal(userIdAndPalNo);
        if(catchCount == 0){
            System.out.println("==== 처음 포획한 펠입니다. ====");
            System.out.println("==== 새로운 펠의 정보가 도감에 등록됩니다. ====");
            int insertPalDexResult = mapper.insertPalDex(userIdAndPalNo);
            if(insertResult != 0 && insertPalDexResult != 0){
                sqlSession.commit();
            } else {
                sqlSession.rollback();
            }
        } else {
            userIdAndPalNo.put("catchCount", catchCount + "");
            int updateResult = mapper.increaseCatchCount(userIdAndPalNo);
            if(updateResult != 0){
                System.out.println("==== 도감의 잡은 횟수가 갱신됩니다. ====");
                sqlSession.commit();
            } else {
                sqlSession.rollback();
            }
        }
        sqlSession.close();
    }


    private boolean catchPal(MonsterDTO thisPal) {
        int randomInt = (int)(Math.random() * 100 + 2);
        SqlSession sqlSession = getSqlSession();
        mapper = sqlSession.getMapper(MonsterDAO.class);
        int catchChance = mapper.getCatchChance(thisPal);
        if(catchChance > randomInt){
            return true;
        } else {
            return false;
        }
    }

    private MonsterDTO setMonster(List<MonsterDTO> monsterList) {
        int listSize = monsterList.size();
        int index = (int)(Math.random() * listSize);
        return monsterList.get(index);
    }

    private List<MonsterDTO> setInfo(String grade, String type) {
        Map<String ,String> randomPalInfo = new HashMap<>();
        randomPalInfo.put("type", type);
        randomPalInfo.put("grade", grade);

        SqlSession sqlSession = getSqlSession();
        mapper = sqlSession.getMapper(MonsterDAO.class);
        List<MonsterDTO> monList = mapper.getRandomPalList(randomPalInfo);
        sqlSession.close();
        return monList;
    }

    private String setGrade() {
        String result = "";
        int num = (int)(Math.random() * 100 + 1);
        if(num <= 55){
            result = "일반";
        } else if(num <= 85){
            result = "희귀";
        } else {
            result = "전설";
        }
        return result;
    }

    private boolean checkHavenPal(UserDTO user) {
        SqlSession sqlSession = getSqlSession();
        mapper = sqlSession.getMapper(MonsterDAO.class);
        List<MonsterDTO> monsterList = mapper.checkHavenPal(user);
        sqlSession.close();
        if(monsterList.size() < 6){
            return true;
        } else {
            return false;
        }
    }
    private String selectMap() {
        System.out.println("==== 이동하실 맵을 선택해주세요. ====");
        System.out.println("1. 숲 \n2. 바다 \n3. 화산");
        int map = sc.nextInt();
        String type = "";
        switch (map) {
            case 1: type = "풀"; break;
            case 2: type = "물"; break;
            case 3: type = "불"; break;
        }
        return type;
    }
}