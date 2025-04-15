package omokgame;

import lombok.Getter;
import lombok.Setter;

/*
 * Board는 게임에서 단 하나만 존재해야 하므로 싱글톤으로 생성
 */

@Getter
@Setter
public class Board {
	private int size;
	String[][] map;
	private static Board instance;

	private Board(int size) { // 기본 보드판 생성
		this.size = size;
		map = new String[size][size];

		for (int row = 0; row < size; row++) { // 모든 칸 기본 값 "."
			for (int col = 0; col < size; col++) {
				map[row][col] = ".";
			}
		}
	}
	
	public static void setInstance(int size) {
		if(instance == null) {
	        instance = new Board(size);
	    }
	}
	
	public static Board getInstance() {
	    return instance;
	}

	public void print() { // 보드판 좌표 출력
		// y좌표
		System.out.print("   ");
		for (int col = 0; col < size; col++) {
			char boardY = (char) ('A' + col);
			System.out.print(boardY + " ");
		}
		System.out.println();

		// x좌표
		for (int row = 0; row < size; row++) {
			if (row < 10) {
				System.out.print(" " + row); // 한 자리 숫자는 정렬 위해 공백 추가
			} else {
				System.out.print(row);
			}
			for (int col = 0; col < size; col++) {
				System.out.print(" " + map[row][col]);
			}
			System.out.println();
		}
	}
}
