package omokgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import omokgame.controller.OmokController;

/*
 * 구조 리팩토링 ver1 [2025.04.14]
 * main class인 Omok에 너무 많은 로직이 섞여 있음
 * main class에는 start()만 두고, 일 처리를 OmokController가 하도록 변경
 */

public class OmokApplication {
    public static void main(String[] args)  throws IOException{
    	OmokController omokController = new OmokController();
    	omokController.start();
    }
}
