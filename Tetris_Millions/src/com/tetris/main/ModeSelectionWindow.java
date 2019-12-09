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
	JLabel map_desc = new JLabel("�Ӿ�Ÿ���� 6���� ���� Ŭ���� �غ�����! (VERY HARD)"); // ADD MAP MODE DESCRIPTION - HWADONG
	JLabel normal_desc = new JLabel("���� �⺻�� ���� ����! (VERY EASY)");
	JLabel time_desc = new JLabel("���Ƿ��� �����ֱ⿣ 2���̸� ������� (2 MINUTES)");
	JLabel desc2 = new JLabel("Challenge your record !");
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
	
		
		mapModeButton.setBounds(200,150,100,40);
		timeModeButton.setBounds(200,250,100,40);
		nomalModeButton.setBounds(200,350,100,40);
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
		setSize(500, 500);
		setLayout(null); 
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		description.setBounds(150,50,300,20);
		description.setFont(description.getFont().deriveFont(20.0f));
		description.setForeground(Color.RED);
		map_desc.setBounds(80,110,400,40); // ��庰 ���� �߰�
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
		add(description); // ���Ӽ���
	    add(map_desc); 
	    add(time_desc);
	    add(normal_desc);
	    add(desc2);
	}
	
	

}
