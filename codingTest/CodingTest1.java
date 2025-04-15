package test;

/**
 * 일단 m이하로 내려가면 거절하고, 수를 그대로 놔둔다.
 * 배열을 다 돌면서 계산해야 한다.
 * 초기 잔액은 0으로 시작
 * 답은 return으로 내보내기
 */

public class CodingTest1 {
	public static void main(String[] args) {
		//1번 입력 예시
		int m = 5000;
		int[] ledger = new int[] {10000, -13000, -4000, -2000, 6500, -20000};
		System.out.println(solution(m, ledger));
		
		//2번 입력 예시
		int m2 = 34151;
		int[] ledger2 = new int[] {-34152, -40000, -50000};
		System.out.println(solution(m2, ledger2));
	}
	
	public static int solution (int m, int[] ledger) {
		int account = 0;
		
		for(int n : ledger) {
			account += n;
			if(account < -m) { //m미만일 시 거절
				account -= n; //역계산으로 되돌리기
			}
		}
		return account;
	}
}
