package omokgame.util;

import omokgame.Board;
import omokgame.Player;
import omokgame.controller.BoardController;

/*
 * Omok과 삼삼 규칙 반복 로직 정리
 */
public class StoneCount {
	BoardController boardController; //이거 사용해서 넣긴 햇는데 주입 해주는게 맞나?
	
	public StoneCount(BoardController boardController){
		this.boardController = boardController;
	}
	
	public int count(Board board, Player player, int[] leftMove, int[] rightMove) {
		return count(board, player, leftMove, rightMove, false); //이건 이제 끝 체크가 필요 없을때
	}
	
	public int count(Board board, Player player, int[] leftMove, int[] rightMove, boolean edgeCheck) {
		int startX = player.getX();
    	int startY = player.getY();
    	
    	int cnt = 1; //자기자신 포함
    	
    	cnt += calculCntAndCheckEdge(board, player, leftMove,edgeCheck); //왼쪽 돌 수
    	cnt += calculCntAndCheckEdge(board, player, rightMove,edgeCheck); //오른쪽 돌 수
    	
    	return cnt; //삼삼이면 3개가 나올꺼고, 삼삼인데 안되면 -100이 나올꺼고, 오목이면 5가 나올꺼공
	}
	
	public int calculCntAndCheckEdge(Board board, Player player, int[] move, boolean edgeCheck) { //cnt하고 끝이 비어 있는지를 체크
    	boolean check = true;
		
		int startX = player.getX();
    	int startY = player.getY();
    	
    	int cnt = 0; //자기자신 미포함을 위해
    	
    	int xInd = startX;
    	int yInd = startY;
    	
    	//leftCheck
    	while(boardController.isTrueRange(xInd + move[0], yInd + move[1]) == true && 
    			board.getMap()[yInd += move[1]][xInd += move[0]].equals(player.getStone())){
    		cnt++;
    	}
    	
    	//끝 체크
    	if(!board.getMap()[yInd][xInd].equals(".")) {
    		check = false;
    	}
    	
    	if(edgeCheck && !check){ //edgeCheck해야 하는데,위 체크 통과 못함.
    		return -100; //삼삼 통과 못하도록
    	}
    	return cnt;
    }

}
