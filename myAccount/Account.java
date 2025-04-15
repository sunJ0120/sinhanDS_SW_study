package myAccount;

/**
 * 리팩토링 사항 (2025.04.14)
 * 1. 계좌 생성시 금액이 0 이상이도록 체크 추가
 * 2. 계좌 생성시 계좌번호 형식이 000-000 이도록 정규표현식 검사 추가
 */

public class Account {
	private final String accountNumber; //계좌번호는 불변
	private String name;
	private int balance; //간단한 예제이니 int로 잡기
	
	public Account(String accountNumber, String name, int balance){
		if(!isValidBalance(balance)) { //false
			throw new IllegalArgumentException("초기 입금액은 0원 이상이어야 합니다.");
		}
		if (!isValidAccountNumber(accountNumber)) {
		    throw new IllegalArgumentException("계좌번호는 000-000 형식으로 입력해주십시오.");
		}
		this.accountNumber = accountNumber;
		this.name = name;
		this.balance = balance;
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	//입금 기능
	public boolean deposit(int money) {
		if(money <= 0) {
			return false;
		}
		balance = this.balance + money;
		return true;
	}
	
	//출금 기능
	public boolean draw(int money) {
		if(this.getBalance() < money || money <= 0) { 
			return false;
		}
		balance = this.balance - money;
		return true;
	}
	
	@Override
	public String toString() { //계좌 출력용
		return this.accountNumber + "  " + this.name + "  " + this.balance;
	}
	//계좌 생성시 금액 유효성 체크
	public boolean isValidBalance(int balance) {
		if(balance < 0) { //0원 이하 계좌는 생성할 수 없음
			return false;
		}
		return true;
	}
	//계좌 생성시 계좌번호 유효성 체크
	public static boolean isValidAccountNumber(String accountNumber) {
		return accountNumber.matches("^\\d{3}-\\d{3}$");

	}
}
