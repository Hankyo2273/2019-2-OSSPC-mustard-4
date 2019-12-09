package com.tetris.main;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.tetris.window.Tetris;

@SuppressWarnings("serial")
public class ModeSelectionWindow extends JFrame implements ActionListener{
	JLabel description = new JLabel("게임 모드를 선택하세요");
	JButton nomalModeButton = new JButton("일반모드");
	JButton timeModeButton = new JButton("타임모드");
	JButton mapModeButton = new JButton("맵모드");

	
	
	public ModeSelectionWindow() {
		// TODO Auto-generated constructor stub
		/**
		 * 1. JFrame 세팅
		 * 2. 버튼 세팅 및 추가
		 */
		
		WindowSetting();
		CreateButton();
	}

	// 버튼 클릭시 발동
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nomalModeButton) { // 일반모드 선택
			TetrisMain.GameMode = 1;
		}else if(e.getSource() == timeModeButton) {
			TetrisMain.GameMode = 2;
		}else if(e.getSource() == mapModeButton) {
			TetrisMain.GameMode = 3;
		}
		dispose(); // 기존의 창 닫음
		new Tetris();
	}
	
	public void CreateButton() {
	
		
		mapModeButton.setBounds(100,100,100,40);
		timeModeButton.setBounds(100,200,100,40);
		nomalModeButton.setBounds(100,300,100,40);
		add(mapModeButton);add(timeModeButton);add(nomalModeButton); //버튼추가
		
		
		
		mapModeButton.addActionListener(this);
		timeModeButton.addActionListener(this);
		nomalModeButton.addActionListener(this);
	}
	
	
	/**
	 *  JFrame 세팅
	 */
	public void WindowSetting() {
		setTitle("모드선택");
		setSize(300, 400);
		setLayout(null); 
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		description.setBounds(100,40,100,40);
		add(description); // 게임설명
	      
	}
	
	

}
