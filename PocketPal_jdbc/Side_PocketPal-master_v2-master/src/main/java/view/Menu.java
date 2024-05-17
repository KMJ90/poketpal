package view;

import controller.*;
import model.MonsterDTO;
import model.UserDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    ArrayList<MonsterDTO> catchMonster = new ArrayList<>();
    CheckManager checkManager = new CheckManager();
    Scanner sc = new Scanner(System.in);
    int map;
    UserDTO user = null;

    public void mainMenu(){
        while (user == null){
            System.out.println("=====================");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 종료");
            System.out.println("=====================");
            System.out.print("※ 메뉴 선택: ");
            String choice = sc.nextLine();
            AccountManager accountManager = new AccountManager();
            switch (choice){
                case "1": user = accountManager.login(); break;
                case "2": accountManager.register(); break;
                case "0": return;
                default :
                    System.out.println("=====================");
                    System.out.println("【올바르지 않은 입력입니다】");
            }
        }
        if(user != null){
        System.out.println("【" + user.getUserName() + "님 환영합니다.】");
        }
        while (true){
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
                case 1: adventure(user);break;
                case 2: cheakBook();break;
                case 3: cheakPal();break;
                case 4: work();break;
                case 0: return;
            }
        }
    }
    public void adventure(UserDTO user){
        AdventureManager adventureManager = new AdventureManager();
        if(adventureManager.checkCatchPal(user) >= 6){
            System.out.println("【보유중인 펠은 6마리를 넘길 수 없습니다. 마을에서 일을 시켜주세요】");
            return;
        }
        MonsterDTO pal = adventureManager.encounter();
        System.out.println("=====================");
        System.out.println("【야생 펠이 나타났다!】");
        System.out.println(pal);
        adventureManager.catchPal(pal, user);
    }
    public void cheakBook(){
        checkManager.check(user);
    }
    public void cheakPal(){
        checkManager.checkCatchPal(user);
    }
    public void work(){
        WorkManager workManager = new WorkManager();
        workManager.work(user);
    }
}
