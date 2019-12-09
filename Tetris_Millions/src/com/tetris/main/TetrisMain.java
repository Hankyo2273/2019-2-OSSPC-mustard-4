package com.tetris.main;

import com.tetris.window.Tetris;
import com.tetris.window.TetrisBoard;


public class TetrisMain{
	public static final int SCREEN_HEIGHT = TetrisBoard.PANEL_HEIGHT;
	public static final int SCREEN_WIDTH = TetrisBoard.PANEL_WIDTH;
	public static int GameMode; // ���Ӹ��
	public static String userId; // ����� ���̵�
	public static int mapLevel = 1; // �� ����
	public static boolean isLogin = false; // �α��� ����

	public static void main(String[] args){
		new TetrisOpening();
	}
}