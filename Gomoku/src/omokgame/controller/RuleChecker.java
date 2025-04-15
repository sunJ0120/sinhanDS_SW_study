package omokgame.controller;

import omokgame.Board;
import omokgame.Player;

public class RuleChecker {
	BoardController boardController; //이거 사용해서 넣긴 햇는데 주입 해주는게 맞나?
	
	RuleChecker(BoardController boardController){
		this.boardController = boardController;
	}
	//삼삼 체크
	public boolean isSamSam(Player player, Board board) {
		if(rowCheck(board, player) + columnCheck(board, player) + leftToRightDia(board, player) + rightToLeftDia(board, player) >= 2) {
			return true;
    	}
		return false; //삼삼 아님, 하나 이하
	}
	
	public int rowCheck(Board board, Player player) { //가로 체크  	
    	return check(board, player, new int[]{-1,0}, new int[]{1,0});
    }
	public int columnCheck(Board board, Player player) { //세로 체크  	
    	return check(board, player, new int[]{0,-1}, new int[]{0,1});
    }
	public int leftToRightDia(Board board, Player player) { //왼오 대각선  	
    	return check(board, player, new int[]{-1,-1}, new int[]{1,1}); 
    }
	public int rightToLeftDia(Board board, Player player) { //오왼 대각선 	
    	return check(board, player, new int[]{-1,1}, new int[]{1,-1});
    }
	
    public int check(Board board, Player player, int[] leftMove, int[] rightMove) { //가로 체크  	
    	int startX = player.getX();
    	int startY = player.getY();
    	
    	int cnt = 1; //자기자신 포함
    	
    	cnt += calculCntAndCheckEdge(board, player, leftMove); //왼쪽 돌 수
    	cnt += calculCntAndCheckEdge(board, player, rightMove); //오른쪽 돌 수
    	
    	if(cnt >= 3) { //한 줄에 돌이 3개 이상 & 양방향 빈자리
    		return 1;
    	}
    	return 0; //비어 있어서 -값이 나오거나,,3개가 안되거나
    }
    
    public int calculCntAndCheckEdge(Board board, Player player, int[] move) { //cnt하고 끝이 비어 있는지를 체크
    	int startX = player.getX();
    	int startY = player.getY();
    	
    	int cnt = 0; //자기자신 미포함을 위해
    	
    	int xInd = startX;
    	int yInd = startY;
    	
    	//leftCheck
    	while(boardController.isTrueRange(xInd + move[0], yInd + move[1]) == true && board.getMap()[yInd += move[1]][xInd += move[0]].equals(player.getStone())){
    		cnt++;
    	} //나중에 해야 끝 체크가 가능함. 상관은 없다만...
    	
    	//끝 체크
    	if(!board.getMap()[yInd][xInd].equals(".")) {
    		return -1; //한쪽이라도 안 되어 있으면 false;
    	}else {
    		return cnt; //끝이 비어 있어야 cnt를 return
    	}
    }
}
