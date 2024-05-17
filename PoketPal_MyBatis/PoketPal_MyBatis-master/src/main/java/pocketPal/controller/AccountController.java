package pocketPal.controller;

import org.apache.ibatis.session.SqlSession;
import pocketPal.common.Template;
import pocketPal.common.model.dao.UserDAO;
import pocketPal.common.model.dto.UserDTO;

import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;

public class AccountController {

    private  UserDAO userDAO;

    private  UserDTO userDTO;

    public AccountController() {

        userDTO = new UserDTO();
    }


    /*  로그인 메소드   */
    public UserDTO signInUser(Map<String, String> loginInfo) {

        String userId = loginInfo.get("signInUserId");
        String userPwd = loginInfo.get("signInUserPwd");

        SqlSession sqlSession = Template.getSqlSession();

        userDAO = sqlSession.getMapper(UserDAO.class);

        List<UserDTO> userList = userDAO.getUserInfo(userDTO);

        boolean loginSuccess = false;

        UserDTO loginUser = null;

        for (UserDTO user : userList) {
            if (userId.equals(user.getUserId()) && userPwd.equals(user.getUserPwd())) {
                loginSuccess = true;
                loginUser = user;
                break;
            }
        }

        sqlSession.close();

        if (loginSuccess == true) {
            System.out.println("=====================");
            System.out.println("로그인 성공");
            System.out.println("【" + userDTO.getUserName() + "님 환영합니다.】");
            return loginUser;
        } else {
            System.out.println("=====================");
            System.out.println("로그인 실패");
            return null;
        }
    }


    /*  회원가입 메소드   */
    public void signUpUser(Map<String, String> userInfo) {

        String userId = userInfo.get("signUpUserId");
        String userPwd = userInfo.get("signUpUserPwd");
        String userName = userInfo.get("signUpUserName");

        UserDTO userDTO = new UserDTO(userId, userPwd, userName);

        SqlSession sqlSession = Template.getSqlSession();

        userDAO = sqlSession.getMapper(UserDAO.class);

        int result = userDAO.signUpUser(userDTO);

        if (result > 0) {
            System.out.println("=====================");
            System.out.println("회원가입 성공");
            sqlSession.commit();
        } else {
            System.out.println("=====================");
            System.out.println("회원가입 실패");
            sqlSession.rollback();
        }

        sqlSession.close();
    }
}
