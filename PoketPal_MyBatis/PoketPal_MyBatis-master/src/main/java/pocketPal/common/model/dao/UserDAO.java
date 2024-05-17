package pocketPal.common.model.dao;

import pocketPal.common.model.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    int signUpUser(UserDTO userDTO);

    List<UserDTO> signInUser(Map<String, String> loginInfo);

    List<UserDTO> getUserInfo(UserDTO userDTO);
}
