package omokgame.controller;

import omokgame.Board;

/*
 * BoardController 요소
 * 1. isTrueRange
 * 2. isPlaceable
 * 3. countOpenThrees //삼삼 갯수 체크
 * 4. isOpenThreeInDirection //열린 삼 갯수
 */
public class BoardController {
	Board board;
	
	BoardController(Board board){ //보드 주입
		this.board = board;
	}
	//인덱스 체크
    public boolean isTrueRange(int x, int y) { //올바른 범위인지
    	//보드판 넘어서 두는지 체크 (이거 먼저 체크해야함)
    	if((x >= board.getSize() || x < 0) || (y >= board.getSize() || y < 0)) { //0보다 작지 않게 추가
    		return false;
    	}
    	return true;
    }
    //isTrueRange에서 따로 분리... columnCheck를 위해
    public boolean isPlaceable(int x, int y) {
    	//이미 있는 곳에 두는지 체크
    	if(!board.getMap()[y][x].equals(".")) { //비지 않은곳에 두려고 할 경우, 두기 전 이므로 단순 getMap이 아니라 y, x로 해야함
    		return false;
    	}
    	return true;
    }
}
