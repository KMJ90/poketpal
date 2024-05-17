package pocketPal.common.model.dao;

import pocketPal.common.model.dto.MonsterDTO;
import pocketPal.common.model.dto.PalDexDTO;
import pocketPal.common.model.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface MonsterDAO {
    List<PalDexDTO> checkPalDexByUserId(String userId);
    List<MonsterDTO> checkCatchPalByUserId(String userId);
    List<MonsterDTO> checkHavenPal(UserDTO user);
    List<MonsterDTO> getRandomPalList(Map<String, String> randomPalInfo);
    int getCatchChance(MonsterDTO thisPal);
    int getCatchCount(Map<String, String> userIdAndPalNo);
    int insertNewPal(Map<String, String> userIdAndPalNo);
    int insertPalDex(Map<String, String> userIdAndPalNo);
    int increaseCatchCount(Map<String, String> userIdAndPalNo);

    int workPal(Map<String, String> info);

    MonsterDTO checkPalType(String palNo);

    List<MonsterDTO> checkCatchPal(String userId);
}
