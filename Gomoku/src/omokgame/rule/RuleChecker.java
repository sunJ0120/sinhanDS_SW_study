package omokgame.rule;

import omokgame.Board;
import omokgame.Player;
import omokgame.util.StoneCount;

/*
 * 삼삼 조건 두개 이상
 */

public class RuleChecker {
	StoneCount stoneCount;

	public RuleChecker(StoneCount stoneCount){
		this.stoneCount = stoneCount;
	}
	//삼삼 체크
	public boolean isSamSam(Player player, Board board) {
		int cntSamSam = 0;
		if(stoneCount.count(board, player, new int[]{-1,0}, new int[]{1,0},true) == 3) { //가로체크
			cntSamSam++;
		}
		if(stoneCount.count(board, player, new int[]{0,-1}, new int[]{0,1},true) == 3) { //세로 체크
			cntSamSam++;
		}
		if(stoneCount.count(board, player, new int[]{-1,-1}, new int[]{1,1},true) == 3) { //왼오 대각선 
			cntSamSam++;
		}
		if(stoneCount.count(board, player, new int[]{-1,1}, new int[]{1,-1},true) == 3) { //오왼 대각선
			cntSamSam++;
		}
		
		if(cntSamSam >= 2) { //두개 이상이면 삼삼
			return true;
    	}
		return false; //삼삼 아님, 하나 이하거나..아니거나
	}
	//육목 체크
	public boolean isSix(Player player, Board board) {
		int cntSix = 0;
		if(stoneCount.count(board, player, new int[]{-1,0}, new int[]{1,0}) == 6) { //가로체크
			cntSix++;
		}
		if(stoneCount.count(board, player, new int[]{0,-1}, new int[]{0,1}) == 6) { //세로 체크
			cntSix++;
		}
		if(stoneCount.count(board, player, new int[]{-1,-1}, new int[]{1,1}) == 6) { //왼오 대각선 
			cntSix++;
		}
		if(stoneCount.count(board, player, new int[]{-1,1}, new int[]{1,-1}) == 6) { //오왼 대각선
			cntSix++;
		}
		
		if(cntSix >= 1) { //하나 이상이면 육목
			return true;
    	}
		return false; //삼삼 아님, 하나 이하
	}
}
