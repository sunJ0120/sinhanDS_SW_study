package omokgame;

import lombok.Getter;
import lombok.Setter;

/*
 * 편의를 위해 약간 변경
 * 좌표를 저장할 x,y 필드를 추가한다.
// * 좌표를 프린트 하기 위한 toString를 오버라이드 한다.
 */
@Setter //좌표 셋팅이나 가져오는 것의 편의성을 위해 설정
@Getter
public class Player {
    String name;
    String stone;
    private int x; 
    private int y;
    
    public Player(String name, String stone) {
        this.name = name;
        this.stone = stone;
    }
    
//    @Override
//    public String toString() {
//    	return String.valueOf(x+'A') + " " + y;
//    }
}
