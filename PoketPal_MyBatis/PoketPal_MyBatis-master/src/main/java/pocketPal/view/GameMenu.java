package pocketPal.view;

import pocketPal.common.model.dto.UserDTO;
import pocketPal.controller.AccountController;
import pocketPal.controller.AdventureController;
import pocketPal.controller.CheckController;
import pocketPal.controller.WorkController;

import java.util.*;

public class GameMenu {

    Scanner sc = new Scanner(System.in);
    UserDTO user = null;
    AccountController accountController;

    public void mainMenu() {
        accountController = new AccountController();
        AdventureController adventureController = new AdventureController();
        CheckController checkController = new CheckController();
        WorkController workController = new WorkController();

        while (user == null) {

            System.out.println("=====================");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 종료");
            System.out.println("=====================");
            System.out.print("※ 메뉴 선택: ");
            String choice = sc.next();
            switch (choice) {
                case "1": user = accountController.signInUser(userCredentials()); break;
                case "2": accountController.signUpUser(userRegistration()); break;
                case "0": return;
                default:
                    System.out.println("=====================");
                    System.out.println("【올바르지 않은 입력입니다】");
            }
        }

        System.out.println("\n" +
                "██     ██ ███████ ██       ██████  ██████  ███    ███ ███████     ██████   ██████   ██████ ██   ██ ███████ ████████ ██████   █████  ██      \n" +
                "██     ██ ██      ██      ██      ██    ██ ████  ████ ██          ██   ██ ██    ██ ██      ██  ██  ██         ██    ██   ██ ██   ██ ██      \n" +
                "██  █  ██ █████   ██      ██      ██    ██ ██ ████ ██ █████       ██████  ██    ██ ██      █████   █████      ██    ██████  ███████ ██      \n" +
                "██ ███ ██ ██      ██      ██      ██    ██ ██  ██  ██ ██          ██      ██    ██ ██      ██  ██  ██         ██    ██      ██   ██ ██      \n" +
                " ███ ███  ███████ ███████  ██████  ██████  ██      ██ ███████     ██       ██████   ██████ ██   ██ ███████    ██    ██      ██   ██ ███████ \n" );
        System.out.println("=====================");
        System.out.println("【" + user.getUserName() + " 님 환영 합니다.】");


        while (true) {

            System.out.println("=====================");
            System.out.println("【행동을 선택해주세요】");
            System.out.println("1. 모험");
            System.out.println("2. 도감");
            System.out.println("3. 펠 확인");
            System.out.println("4. 일 시키기");
            System.out.println("0. 종료");
            System.out.println("=====================");
            System.out.print("※ 메뉴 선택: ");
            int action = sc.nextInt();
            switch (action){
                case 1 : adventureController.adventure(user); break;
                case 2: checkController.checkPalDexByUserId(user.getUserId());break;
                case 3: checkController.checkCatchPalByUserId(user.getUserId());break;
                case 4: workController.workPal(user);break;
                case 0: return;
            }
        }

    }

    /*  로그인 정보 입력 값  */
    private Map<String, String> userCredentials() {

        System.out.println("=====================");
        System.out.print("아이디: ");
        String userId = sc.next();

        System.out.println("=====================");
        System.out.print("비밀번호: ");
        String userPwd = sc.next();

        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("signInUserId", userId);
        loginInfo.put("signInUserPwd", userPwd);

        return loginInfo;
    }

    /*  회원가입 정보 입력 값   */
    private Map<String, String> userRegistration() {

        System.out.println("=====================");
        System.out.print("사용할 아이디를 입력해주세요: ");
        String userId = sc.next();

        System.out.println("=====================");
        System.out.print("사용할 비밀번호를 입력해주세요: ");
        String userPwd = sc.next();

        System.out.println("=====================");
        System.out.print("사용할 이름을 입력해 주세요: ");
        String userName = sc.next();

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("signUpUserId", userId);
        userInfo.put("signUpUserPwd", userPwd);
        userInfo.put("signUpUserName", userName);

        return userInfo;
    }

    // 로그인 서브 메뉴


}
