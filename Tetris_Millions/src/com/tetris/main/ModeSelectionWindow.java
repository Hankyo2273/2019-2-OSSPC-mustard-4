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
	JLabel map_desc = new JLabel("머쓱타드의 6가지 맵을 클리어 해보세요! (VERY HARD)"); // ADD MAP MODE DESCRIPTION - HWADONG
	JLabel normal_desc = new JLabel("나는 기본이 제일 좋아! (VERY EASY)");
	JLabel time_desc = new JLabel("내실력을 보여주기엔 2분이면 충분하지 (2 MINUTES)");
	JLabel desc2 = new JLabel("Challenge your record !");
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
	
		
		mapModeButton.setBounds(200,150,100,40);
		timeModeButton.setBounds(200,250,100,40);
		nomalModeButton.setBounds(200,350,100,40);
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
		setSize(500, 500);
		setLayout(null); 
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		description.setBounds(150,50,300,20);
		description.setFont(description.getFont().deriveFont(20.0f));
		description.setForeground(Color.RED);
		map_desc.setBounds(80,110,400,40); // 모드별 설명 추가
		map_desc.setFont(map_desc.getFont().deriveFont(13.0f));
		map_desc.setForeground(Color.BLUE);
		time_desc.setBounds(90,210,400,40);
		time_desc.setFont(time_desc.getFont().deriveFont(13.0f));
		time_desc.setForeground(Color.BLUE);
		normal_desc.setBounds(130,310,400,40);
		normal_desc.setFont(normal_desc.getFont().deriveFont(13.0f));
		normal_desc.setForeground(Color.BLUE);
		desc2.setBounds(130,420,300,20);
		desc2.setFont(desc2.getFont().deriveFont(20.0f));
		add(description); // 게임설명
	    add(map_desc); 
	    add(time_desc);
	    add(normal_desc);
	    add(desc2);
	}
	
	

}
