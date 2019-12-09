package com.tetris.main;

import com.tetris.window.Tetris;
import com.tetris.window.TetrisBoard;


public class TetrisMain{
	public static final int SCREEN_HEIGHT = TetrisBoard.PANEL_HEIGHT;
	public static final int SCREEN_WIDTH = TetrisBoard.PANEL_WIDTH;
	public static int GameMode; // 게임모드
	public static String userId; // 사용자 아이디
	public static int mapLevel = 1; // 맵 레벨
	public static boolean isLogin = false; // 로그인 유무

	public static void main(String[] args){
		new TetrisOpening();
	}
}