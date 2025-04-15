package omokgame.controller;

/*
 * 구조 리팩토링 ver3 [2025.04.15]
 * Omok의 로직 양방향 탐색으로 전체 변경 (RuleChecker에서 삼삼과의 통일성을 위해...)
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import omokgame.Board;
import omokgame.Player;
import omokgame.rule.RuleChecker;
import omokgame.util.StoneCount;

public class OmokController {
	BufferedReader br;
	
	Player user;
    Player computer;
    final int BOARD_SIZE = 19;
    
    Board board;
    BoardController boardController;
    
    StoneCount stoneCount;
    RuleChecker ruleChecker;
    
    int startFlag;
    
	
	//생성자에서 객체 주입
    public OmokController(){
    	br = new BufferedReader(new InputStreamReader(System.in)); //낭비를 막기 위해 한 번만 생성
    	
		this.user = new Player("사용자", "O");
		this.computer = new Player("컴퓨터", "X");
		
		board.setInstance(BOARD_SIZE); //싱글톤 static으로 생성했으므로 바로 객체에 접근한다.
		this.board = board.getInstance();
		this.boardController = new BoardController(board);
	
		this.stoneCount = new StoneCount(boardController);
		this.ruleChecker = new RuleChecker(stoneCount);
		
		this.startFlag = 0;
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
			
			//삼삼규칙 위반시 반책패 처리
			if(ruleChecker.isSamSam(currentPlayer, board)) {
				System.out.println("삼삼규칙 위반으로 반칙입니다." + currentPlayer.getName() + "의 패배!");
				board.print();
				break;
			}
			
			//육목규칙 위반시 반책패 처리
			if(ruleChecker.isSix(currentPlayer, board)) {
				System.out.println("육목규칙 위반으로 반칙입니다." + currentPlayer.getName() + "의 패배!");
				board.print();
				break;
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
		
		if(!boardController.isTrueRange(currentPlayer.getX(), currentPlayer.getY())) {
    		System.out.println("보드판을 넘어서 둘 수 없습니다.");
    		return false; //종료
    	}
    	
    	if(!boardController.isPlaceable(currentPlayer.getX(), currentPlayer.getY())) {
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
    	int cntOmok = 0;
		if(stoneCount.count(board, player, new int[]{-1,0}, new int[]{1,0}) == 5) { //가로체크
			cntOmok++;
		}
		if(stoneCount.count(board, player, new int[]{0,-1}, new int[]{0,1}) == 5) { //세로 체크
			cntOmok++;
		}
		if(stoneCount.count(board, player, new int[]{-1,-1}, new int[]{1,1}) == 5) { //왼오 대각선 
			cntOmok++;
		}
		if(stoneCount.count(board, player, new int[]{-1,1}, new int[]{1,-1}) == 5) { //오왼 대각선
			cntOmok++;
		}
		
		if(cntOmok >= 1) { //한개 이상이면 오목
			return true;
    	}
		return false; //오목 아님.
    }
}
