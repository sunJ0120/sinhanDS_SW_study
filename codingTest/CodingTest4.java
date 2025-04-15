package test;

import java.util.Arrays;

/*
 * 풀이 날짜 : 2025-04-10
 * 1. 자본 max값을 구한다. -> 낙찰 받을 사람
 * 2. 그 다음 max 값을 구한다 + 10000 -> 낙찰 금액
 * 3. List에 추가한다. 
 *
 * 정보 추가
 * 📢 출력시 낙찰품의 번호가 낮은순~ 이라고 하지만, 짜피 낙찰품 자체를 작은 인덱스부터 
 차례대로 넣어서 배열을 구성하므로, 마지막에 딱히 배열을 한 번더 정렬할 필요는 없다.
 * 📢 또한, 답 자체가 경매가만 배열로 구성해서 프린트 하는 것이므로, 우선순위인 사람이 같을 때 번호가 낮은 순인 사람이 낙찰한다는 조건은 고려하지 않는다.
 * (왜냐면 당장 낙찰가 자체에 영향을 미치는 정보도 아니고, 다음 루프의 결과에 영향을 미치는 정보도 아니기 때문이다.(해당 문제에선 입찰자 정보가 아닌 입찰자가 입찰한 총 액수만 고려하므로))
 */

public class CodingTest4 {

	public static void main(String[] args) {
		
		int n1 = 4;
		int[] amounts1 = {1000000, 490000, 700000, 290000};
		
		int n2 = 6;
		int[] amounts2 = {30000,70000,10000};
;
		System.out.println(Arrays.toString(solution(n1, amounts1)));
		System.out.println(Arrays.toString(solution(n2, amounts2)));
	}
	/*
	 * n = 경매 물품수
	 * amounts = 자본수
	 */
	
	public static int[] solution(int n, int[] amounts) {
		int[] answer = {0};
		answer = new int[n];
		
		for(int i = 0; i<n; i++) {
			int person = 0; //낙찰 받을 사람
			int price = 0; //낙찰 가격
			
			Arrays.sort(amounts);
			person = amounts[amounts.length-1];
			price = amounts[amounts.length-2] + 10000; //여기서 전 재산여부를 체크해야함.
			if(person < price) { //만원을 더한 가격이 전재산보다 크다면, 그냥 전재산으로
				price = person; 
			}
			answer[i] = price; //가격 더하기
			amounts[amounts.length-1] -= price; //샀으니까 제외
		}
		
		return answer;
	}
}
