package myAccount;

import java.util.List;

public class AccountController {
	AccountRepository accountRepository = AccountRepository.getInstance();
	
	public List<Account> inven() { //계좌 목록
		return accountRepository.findAll();
	}
	
	public Account findAccountOrPrintError(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber); //계좌 찾기
		
		if(account == null) { //계좌 없음
			System.out.println("계좌번호가 존재하지 않습니다.");
			return null;
		}
		
		return account;
	}
	
	public void deposit(String accountNumber, int money) {//예금
		
		Account account = findAccountOrPrintError(accountNumber);
		if(account == null) {
			return;
		}
		
		if(account.deposit(money)) {
			System.out.println("성공적으로 예금 완료되었습니다.");
		}else {
			System.out.println("돈은 0원 이상이어야 합니다.");
		}
	}
	
	public void draw(String accountNumber, int money) { //출금
		Account account = findAccountOrPrintError(accountNumber);
		if(account == null) {
			return;
		}
		
		if(account.draw(money)) {
			System.out.println("성공적으로 출금이 완료되었습니다.");
		}else {
			System.out.println("잔액보다 많은 돈을 출금할 수 없습니다.");
		}
	}
	
	public void accountInfo(String accountNumber) {
		Account account = findAccountOrPrintError(accountNumber);
		if(account == null) {
			return;
		}
		
		System.out.println(account.toString());
	}
}
