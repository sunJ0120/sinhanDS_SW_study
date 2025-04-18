# 🕹️ Gomoku - 오목 게임

Java로 구현한 간단한 콘솔 기반 오목 게임입니다.  
2인 플레이(사용자 vs 컴퓨터)를 지원하며, 오목 판정 기능을 포함합니다.

## 🔧 기술 스택
- Java
- Lombok (라이브러리 사용)

## 📌 주요 기능

1. **게임 시작 랜덤화**  
   - 0 또는 1의 난수를 생성해 플레이어의 선공을 랜덤 결정

2. **오목 승리 판정**  
   - 가로, 세로, 대각선(↘, ↙) 방향으로 연속 5개의 돌을 검사하여 승리 여부 판단

3. **게임 반복 진행**  
   - 승자가 결정되기 전까지 사용자와 컴퓨터가 번갈아가며 돌을 놓음

## 🗂️ 폴더 구조
```bash
Gomoku/
└── src
   ├── Board.java 
   ├── Omok.java
   ├── Player.java 
   ├── README.md
   └── OmokController.java 
         ├── RuleChecker.java
         ├── BoardController.java
         └── OmokController.java 
```


## 🖼️ 오목 판정 로직 (추후 이미지 추가 예정)
- 대각선, 수평, 수직을 모두 검사하는 방식
- 추후 다이어그램 또는 그림 추가 예정
