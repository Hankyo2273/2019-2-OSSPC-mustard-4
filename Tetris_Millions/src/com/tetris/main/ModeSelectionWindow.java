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
	JLabel description = new JLabel("���� ��带 �����ϼ���");
	JButton nomalModeButton = new JButton("�Ϲݸ��");
	JButton timeModeButton = new JButton("Ÿ�Ӹ��");
	JButton mapModeButton = new JButton("�ʸ��");

	
	
	public ModeSelectionWindow() {
		// TODO Auto-generated constructor stub
		/**
		 * 1. JFrame ����
		 * 2. ��ư ���� �� �߰�
		 */
		
		WindowSetting();
		CreateButton();
	}

	// ��ư Ŭ���� �ߵ�
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nomalModeButton) { // �Ϲݸ�� ����
			TetrisMain.GameMode = 1;
		}else if(e.getSource() == timeModeButton) {
			TetrisMain.GameMode = 2;
		}else if(e.getSource() == mapModeButton) {
			TetrisMain.GameMode = 3;
		}
		dispose(); // ������ â ����
		new Tetris();
	}
	
	public void CreateButton() {
	
		
		mapModeButton.setBounds(100,100,100,40);
		timeModeButton.setBounds(100,200,100,40);
		nomalModeButton.setBounds(100,300,100,40);
		add(mapModeButton);add(timeModeButton);add(nomalModeButton); //��ư�߰�
		
		
		
		mapModeButton.addActionListener(this);
		timeModeButton.addActionListener(this);
		nomalModeButton.addActionListener(this);
	}
	
	
	/**
	 *  JFrame ����
	 */
	public void WindowSetting() {
		setTitle("��弱��");
		setSize(300, 400);
		setLayout(null); 
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		description.setBounds(100,40,100,40);
		add(description); // ���Ӽ���
	      
	}
	
	

}
