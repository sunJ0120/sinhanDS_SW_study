package test;

import java.util.Arrays;
import java.util.Comparator;

/*
 * 조건
 * 1. 등수를 기준으로 먼저 정렬
 * 2. 구매하려는 티켓수가 많은 사람 정렬
 * 
 * + if문 (남은 티켓수 관련) 같은 경우는 가장 나중에 정렬 후에 체크한다.
 */

public class CodingTest2 {

	public static void main(String[] args) {
		int[][] li1 = {{2, 3},{1, 7},{2, 4},{3, 5}};
		int ticket1 = 10;
		
		System.out.println(solution(ticket1, li1));
		
		int[][] li2 = {{1,9},{3,6},{2,5}};
		int ticket2 = 8;
		
		System.out.println(solution(ticket2, li2));
		
		int[][] li3 = {{3,1},{2,5},{2,10},{3,8},{1,2}};
		int ticket3 = 20000;
		
		System.out.println(solution(ticket3, li3));
	}
	
	public static int solution (int tickets, int[][] requests) {
		int soldTickets = 0;
		
		Arrays.sort(requests, new Comparator<int[]>() {
	
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0] == o2[0]) {
					return o2[1] - o1[1]; //내림차순
				}
				return o1[0] - o2[0]; //기본적으로는 등수 기준
			}
		});
		
		for(int i = 0; i<requests.length; i++) {
			if(requests[i][1] <= tickets) { //전체 티켓이 여유가 있을 경우
				soldTickets += requests[i][1];
				tickets -= requests[i][1]; //팔린 수 만큼 제거
			}
		}
	
		return soldTickets;
	}
}
