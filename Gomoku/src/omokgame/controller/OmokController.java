package omokgame.controller;

/*
 * 구조 리팩토링 ver2 [2025.04.14]
 * Omok에서 하던 로직 체크 업무 이전
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import omokgame.Board;
import omokgame.Player;

public class OmokController {
	Player user;
    Player computer = new Player("컴퓨터", "X");
    final int BOARD_SIZE = 19;
    Board board;
    BoardController boardControll;
    int startFlag;
    BufferedReader br;
	
	//생성자에서 객체 주입
    public OmokController(){
		this.user = new Player("사용자", "O");
		this.computer = new Player("컴퓨터", "X");
		board.setInstance(BOARD_SIZE); //싱글톤 static으로 생성했으므로 바로 객체에 접근한다.
		this.board = board.getInstance();
		this.boardControll = new BoardController(board);
		this.startFlag = 0;
		br = new BufferedReader(new InputStreamReader(System.in)); //낭비를 막기 위해 한 번만 생성
	}
	
	//오목 시작
	public void start() throws IOException {
		startFlag = chooseStart(); //처음 플래그는 랜덤 생성
		while(true) { //계속 진행
			board.print(); //놓기 전에 보드판 보여주기
			Player currentPlayer = startFlag == 1 ? user : computer; //진행할 플레이어 정하기
			System.out.println(currentPlayer.getName() + "의 turn 입니다. 돌을 둘 좌표를 입력하시오 (형식 : X Y)");
			
			if(!receiveAndValidateInput(currentPlayer)) { //receiveAndValidateInput이 잘못 들어오면 continue
				continue; //다시 돌게 한다.
			}
			
			if(isWin(currentPlayer)) {
				System.out.println(currentPlayer.getName() + "의 승리!");
				// 승리한 보드를 한 번 더 프린트
				board.print();
				break;
			}
			startFlag = startFlag == 1 ? 0 : 1; //플래그 반대로 뒤집기
		}
	}
	
	/*
     * [수정] startFlag 기준으로 누가 돌을 둘지 정하기 위해 매개변수에 플래그 추가
     * - 보드판을 넘어선 범위를 입력했는지
     * - 돌이 이미 있는 곳에 두려고 했는지를 체크
     * - 입력 받는 것을 범위 체크하는 곳에 둔다!
     */
	public boolean receiveAndValidateInput(Player currentPlayer) throws IOException{
		StringTokenizer st = new StringTokenizer(br.readLine());
		 
		//띄어쓰기 구분을 위해 BufferedReader 사용
		//소문자로 잘못 입력할 상황을 대비해서 uppercase로 변환 추가
		currentPlayer.setX(st.nextToken().toUpperCase().charAt(0) - 'A');
		currentPlayer.setY(Integer.parseInt(st.nextToken()));
		
		if(!boardControll.isTrueRange(currentPlayer.getX(), currentPlayer.getY())) {
    		System.out.println("보드판을 넘어서 둘 수 없습니다.");
    		return false; //종료
    	}
    	
    	if(!boardControll.isPlaceable(currentPlayer.getX(), currentPlayer.getY())) {
    		System.out.println("이미 돌이 있는곳에는 둘 수 없습니다.");
    		return false; //종료
    	}
    	
    	board.getMap()[currentPlayer.getY()][currentPlayer.getX()] = currentPlayer.getStone();
    	return true;
    }
    
    //0 or 1 랜덤으로 누가 먼저 시작 할지를 정하기
    public int chooseStart() {
    	return (int)(Math.random() * 2);
    }
    
    //이기는 조건에 있는지를 검사한다.
    public boolean isWin(Player player) {
    	//하나라도 충족하면 오목이다.
    	if(rowCheck(player) || columnCheck(player) || leftToRightDia(player) || rightToLeftDia(player)) {
    		return true;
    	}
    	return false;
    }
    /*
     * 각 방향 오목 여부 체크를 위한 메서드
     */
    public boolean rowCheck(Player player) { //가로 체크  	
    	int startX = player.getX();
    	int startY = player.getY();
    	return findStartAndCalculateCnt(player, startX, startY, new int[] {1,0}); //x로 1칸, y로 0칸 이동으로 체크
    }
    
    public boolean columnCheck(Player player) { //세로 체크
    	int startX = player.getX();
    	int startY = player.getY();
    	return findStartAndCalculateCnt(player, startX, startY, new int[] {0,1}); //x로 0칸 , y로 1칸 이동으로 체크
    }
    
    public boolean leftToRightDia(Player player) { //왼->오 대각선
    	int startX = player.getX();
    	int startY = player.getY();
    	return findStartAndCalculateCnt(player, startX, startY, new int[] {1,1}); //x로 1칸 , y로 1칸 이동으로 체크
    }
    
    public boolean rightToLeftDia(Player player) { //오->왼 대각선
    	int startX = player.getX();
    	int startY = player.getY();
    	
    	return findStartAndCalculateCnt(player, startX, startY, new int[] {1,-1}); //x로 1칸 , y로 -1칸 이동으로 체크
    }
    
    /*
     * 오목 체크를 위한 메서드
     * 
     * start점을 찾은 다음, 온 길의 반대 방향으로 가면서 오목 여부를 체크한다.
     * 
     * ⭐ direction에 x방향과 y방향을 담는다.
     * ⭐ 반대로 갈때는 반대로 빼주면 된다.
     * ⭐ start점을 구해서 cnt를 구한다.
     */
    public boolean findStartAndCalculateCnt(Player player, int startX, int startY, int[] direction) {
    	int cnt = 0;
    	
    	//가장 쉽게, 정방향 대로 가본다. (가로의 경우 왼 -> 오)
    	do{ //player의  stone일때만 움직인다.
    		startX += direction[0];
    		startY += direction[1];
    	}while(boardControll.isTrueRange(startX, startY) && board.getMap()[startY][startX].equals(player.getStone())); //캡슐화 유지를 위해 player.getStone()으로 변경
    	
    	startX -= direction[0]; //한 번 반대 방향으로 계산.
    	startY -= direction[1]; //한 번 반대 방향으로 계산.
    	//찾은 방향의 반대로 가야 전체 돌 수를 셀 수 있다.
    	while(boardControll.isTrueRange(startX, startY) && board.getMap()[startY][startX].equals(player.getStone())) { 
    		//player의  stone일때만 움직인다.
    		//또한 움질일때도 움직이는 곳이 맞는 인덱스인지 체크해야 한다. (이거 먼저 체크해야 인덱스 에러 안남)
    		cnt++;
    		startX -= direction[0];
        	startY -= direction[1];
    		//온 길의 반대로 움직여야 stone의 갯수를 셀 수 있다.
    	}
    	
    	if(cnt == 5) { //5개가 되면 true
    		return true;
    	}
		return false;
    }
}
