package myAccount;

import java.util.List;
import java.util.Scanner;

/**
 * 리팩토링 사항 (2025.04.14)
 * 1. 계좌 생성 과정에서 exception 처리
 * 2. exception에 걸릴 시 continue로 다시 돌아가도록 설정
 */

public class Test {
	public static void main(String[] args) {
		AccountRepository accountRepository = AccountRepository.getInstance();
		AccountController accountController = new AccountController();
		
		StringBuilder stb = new StringBuilder();
		stb.append("-------------------------" + "\n");
		stb.append("1.계좌생성|2.계좌목록|3.예금|4.출금|5.종료" + "\n");
		stb.append("-------------------------" + "\n");
		stb.append("선택 > ");
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println(stb);
			int choice = Integer.parseInt(sc.nextLine());
			
			if(choice == 1) {
				System.out.println("--------");
				System.out.println("계좌생성");
				System.out.println("--------");
				
				try{
					System.out.print("계좌번호 : ");
					String accountNumber = sc.nextLine(); //계좌번호
					
					System.out.print("계좌주 : ");
					String name = sc.nextLine(); //계좌주
					
					System.out.print("초기입금액 : ");
					int balance = Integer.parseInt(sc.nextLine()); //초기 입금액
					
					Account account = new Account(accountNumber,name,balance); //계좌 객체 생성
				
					accountRepository.save(account);
				}catch(Exception e){ //계좌생성에러
					System.out.println(e.getMessage());
					continue;
				}
				System.out.println("결과 : 계좌가 생성되었습니다.");
			}else if(choice == 2) { //계좌 목록
				System.out.println("--------");
				System.out.println("계좌목록");
				System.out.println("--------");
				
				List<Account> accounts = accountRepository.findAll();
				for(Account account : accounts) {
					String accountNumber = account.getAccountNumber();
					String Name = account.getName();
					int balance = account.getBalance();
					
					accountController.accountInfo(accountNumber); //info 출력
				}
			}else if(choice == 3) { //예금
				System.out.println("--------");
				System.out.println("예금");
				System.out.println("--------");
				
				System.out.print("계좌번호 : ");
				String accountNumber = sc.nextLine(); //계좌번호
				System.out.print("예금액 : ");
				int depositBalance = Integer.parseInt(sc.nextLine()); //예금액
	
				accountController.deposit(accountNumber, depositBalance); //예금
			}else if(choice == 4) { //출금
				System.out.println("--------");
				System.out.println("출금");
				System.out.println("--------");
				
				System.out.print("계좌번호 : ");
				String accountNumber = sc.nextLine(); //계좌번호
				System.out.print("출금액 : ");
				int drawBalance = Integer.parseInt(sc.nextLine()); //출금액
				
				accountController.draw(accountNumber, drawBalance); //출금
			}else if(choice == 5){ //종료
				System.out.println("프로그램 종료");
				break;
			}else {
				System.out.println("보기에 있는 숫자를 선택해주십시오.");
			}
		}
	}
}
