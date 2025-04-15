package myAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 리팩토링 사항 (2025.04.14)
 * 1. 배열 -> list로 변경
 * 2. findAll method 일부 변경
 */

public class AccountRepository {
	private static final List<Account> accountList = new ArrayList<>();
	private static int sequence = 0; //인덱스를 id처럼 이용
	
	private static Map<String, Integer> accountNumberStore = new HashMap<>(); //accountNumber와 배열 인덱스 매핑
	
	private static final AccountRepository instance = new AccountRepository();
	//AccountRepository를 싱글톤으로 이용하도록 함.
	
	public static AccountRepository getInstance() {
		return instance;
	}

	private AccountRepository() { //생성자 생성 막기
	}
	
	public Account save(Account account) { //Accout 객체 저장
		
		String accountNumber = account.getAccountNumber();
		if(accountNumberStore.containsKey(accountNumber)) {
			throw new IllegalArgumentException("이미 존재하는 계좌번호입니다.");
		}
		
		accountList.add(account); //List에 추가
		accountNumberStore.put(accountNumber, sequence);
		sequence++;  //인덱스
		
		return account;
	}
	
	public Account findByAccountNumber(String accountNumber) {
		Integer index = accountNumberStore.get(accountNumber); //매핑정보로 index 찾기
		if(index == null) { //없는계좌인지 체크
			return null;
		}
		return accountList.get(index);
	}
	/*
	 * 변경
	 * 기존 : 동적 배열이 아니어서 null 처리를 위해 null이 아닌 만큼만 return 하도록 함
	 * 현재 : 동적 배열이므로, accountList 자체를 return
	 */
	public List<Account> findAll(){
		return accountList;
	}
}
