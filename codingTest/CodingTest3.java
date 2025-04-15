package test;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 리팩토링 ver2 [25.04.09]
 * 1. 누적합 배열을 따로 만들지 않고, 이전 & 이후 누적합을 직접 변수로 구하기
 * 2. 이전 vip, 이후 vip를 flag로 두고 체크
 */

public class CodingTest3 {

	public static void main(String[] args) {
		int[] periods_1 = {8,23,24};
		int[][] payments_1 = {{100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000}, 
		                    {100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000}, 
		                    {350000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000}};
		int[] estimates_1 = {100000, 100000, 100000};
		
		System.out.println("___________1번 예시__________");
		System.out.println(Arrays.toString(solution(periods_1, payments_1, estimates_1)));
		
		int[] periods_2 = {24, 59, 59, 60};
		int[][] payments_2 = {{50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000},
		                      {50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000}, 
		                      {350000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000},
		                      {50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000, 50000}};
		int[] estimates_2 = {350000, 50000, 40000, 50000};
		
		System.out.println("___________2번 예시__________");
		System.out.println(Arrays.toString(solution(periods_2, payments_2, estimates_2)));
	}
	
	public static int[] solution(int[] periods, int[][] payments, int[] estimates) {
		int[] answer = { 0, 0 };
		
		for(int i = 0; i<periods.length; i++) {
			int moneySum = 0;
			int laterMoneySum = 0;
			boolean vip;
			boolean postVip;
			
			for(int n : payments[i]) {
				moneySum += n;
			}
			laterMoneySum = moneySum + estimates[i] - payments[i][0];
			vip = isVip(periods[i],moneySum);
			postVip = isVip(periods[i]+1,laterMoneySum); //periods[i]+1 -> 기간이 하나 증가하였으므로
			
			if(!vip && postVip) { //VIP X -> VIP O
				answer[0]++;
			}else if(vip && !postVip) {
				answer[1]++;
			}
		}
		return answer;
	}
	
	public static boolean isVip(int periods, int prefixSum) {
		if(periods < 24) { //2년 미만이면 vip 탈락
			return false;
		}
		
		if(24 <= periods && periods < 60) { //2년 이상 5년 미만 라인
			if(prefixSum < 900000) { //90만원 미만~
				return false;
			}
		}
		if(periods >= 60) { //5년 이상 라인
			if(prefixSum < 600000) { //60만원 미만~
				return false;
			}	
		}
		return true;
	}
}
