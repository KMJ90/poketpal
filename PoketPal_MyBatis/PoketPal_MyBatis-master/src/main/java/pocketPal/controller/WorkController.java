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

public class WorkController {

    private MonsterDAO monsterDAO;
    public void workPal(UserDTO user) {

        Scanner sc = new Scanner(System.in);

        checkCatchPal(user);

        System.out.println("일을 시킬 포켓펠 번호 입력 : ");
        String palNo = sc.nextLine();

        int result = tryWorkPal(user, palNo);
        if(result > 0){
            System.out.println("일시키기 성공");
            MonsterDTO monster = checkPalType(palNo);
            howWork(monster);
        } else {
            System.out.println("일시키기 실패");
        }
    }

    private void checkCatchPal(UserDTO user) {
        SqlSession sqlSession = getSqlSession();
        monsterDAO = sqlSession.getMapper(MonsterDAO.class);
        String userId = user.getUserId();
        List<MonsterDTO> monsterList = monsterDAO.checkCatchPal(userId);
        for(MonsterDTO monster : monsterList){
            System.out.println(monster);
        }
    }

    private void howWork(MonsterDTO monster) {
        String type = monster.getPalType();
        String name = monster.getPalName();
        if(type.equals("물")){
            System.out.println(name + "은(는) 농사를 도와주기로 했다.");
        }
        if(type.equals("불")){
            System.out.println(name + "은(는) 요리를 도와주기로 했다.");
        }
        if(type.equals("풀")){
            System.out.println(name + "은(는) 건축을 도와주기로 했다.");
        }
        if(type.equals("빛")){
            System.out.println(name + "은(는) 마을을 관리하기로 했다.");
        }
    }

    private MonsterDTO checkPalType(String palNo) {
        SqlSession sqlSession = getSqlSession();
        monsterDAO = sqlSession.getMapper(MonsterDAO.class);
        MonsterDTO monster = monsterDAO.checkPalType(palNo);
        return monster;
    }

    private int tryWorkPal(UserDTO user, String palNo) {
        SqlSession sqlSession = getSqlSession();
        monsterDAO = sqlSession.getMapper(MonsterDAO.class);
        Map<String,String> info = new HashMap<>();
        info.put("userId", user.getUserId());
        info.put("palNo", palNo);
        int result = monsterDAO.workPal(info);
        if(result > 0){
            sqlSession.commit();
        } else {
            sqlSession.rollback();
        }
        sqlSession.close();
        return result;

//        키 Str 1 값 Str 하나
//        키 Str 값 Str
//        키 Str 값 Str
//        키 Str 값 Str

    }
}
