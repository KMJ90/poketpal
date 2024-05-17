package pocketPal.controller;

import org.apache.ibatis.session.SqlSession;
import pocketPal.common.model.dao.MonsterDAO;
import pocketPal.common.model.dto.MonsterDTO;
import pocketPal.common.model.dto.PalDexDTO;
import pocketPal.common.model.dto.UserDTO;

import java.util.List;

import static pocketPal.common.Template.getSqlSession;

public class CheckController {
    private MonsterDAO monsterDAO;
    UserDTO userDTO = new UserDTO();

    // MenuService ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    public List<PalDexDTO> checkPalDexByUserIdService(String userId) {

        SqlSession sqlSession = getSqlSession();

        monsterDAO = sqlSession.getMapper(MonsterDAO.class);

        List<PalDexDTO> monsterList = monsterDAO.checkPalDexByUserId(userId);

        sqlSession.close();

        return monsterList;
    }

    public List<MonsterDTO> checkCatchPalByUserIdService(String userId) {
        SqlSession sqlSession = getSqlSession();

        monsterDAO = sqlSession.getMapper(MonsterDAO.class);

        List<MonsterDTO> catchMonsterList = monsterDAO.checkCatchPalByUserId(userId);

        sqlSession.close();

        return catchMonsterList;
    }
    // Controller ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    public void checkPalDexByUserId(String userID) {

        List<PalDexDTO> monsterList = checkPalDexByUserIdService(userID);

        if (monsterList != null && monsterList.size() > 0 ) {
            for (PalDexDTO monster : monsterList) {
                System.out.println(monster);
            }
        } else {
            System.out.println("도감 조회에 실패하셨습니다.");
        }
    }

    public void checkCatchPalByUserId(String userID) {

        List<MonsterDTO> catchMonsterList = checkCatchPalByUserIdService(userID);

        if (catchMonsterList != null && catchMonsterList.size() > 0) {
            for (MonsterDTO catchMonster : catchMonsterList) {
                System.out.println(catchMonster);
            }
        } else {
            System.out.println("현재 소지하고 있는 펠 목록 조회에 실패하셨습니다.");
        }

    }

















}
