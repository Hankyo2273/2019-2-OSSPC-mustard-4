package com.tetris.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tetris.main.TetrisMain;

import retrofit2.*;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author minshik
 *
 */
public class GameResultInfoWindow{
	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame();
	
	private int gameScore;
	private int gameMode;
	private String info;
	private int ranking;
	private String time;

	
	public GameResultInfoWindow(int gamescore, int gamemode, String time) {
		this.gameMode = gamemode;
		this.time = time;
		this.gameScore = gamescore;
		
		// minshik 추가
		WindowSetting();
		ComponentNotLogin(); // 로그인 하지 않았을 경우 결과창
				
				
	}

	
	

	public GameResultInfoWindow(int gamescore, int gamemode, int ranking, String info) {
		this.gameScore = gamescore;	
		this.gameMode = gamemode;
		this.info = info;
		this.ranking = ranking;
	
		
		
		
		// minshik 추가
		WindowSetting();
		Component();
	}
	
	
	
	/**
	 *  JFrame 세팅
	 */
	public void WindowSetting() {
	
		if(gameMode == 1) {
			frame.setTitle("일반모드 결과");
		}else if(gameMode == 2) {
			frame.setTitle("타임모드 결과");
		}else if(gameMode == 3) {
			frame.setTitle("맵모드 결과");
		}
		frame.setSize(300, 400);
		frame.setLayout(null); 
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
	}
	
	/**
	* 컴포넌트 만들기
	*/
	public void Component() {
		
		
		if(this.info.equalsIgnoreCase("new")) {
		
			JLabel record = new JLabel("게임결과"); 
			record.setBounds(100,40,100,40);
			record.setFont(new Font("Monospaced", Font.BOLD, 13));
			record.setForeground(Color.BLACK);
			
			
			JLabel userId = new JLabel(TetrisMain.userId + " 님");
			userId.setBounds(100, 100, 100, 40);
			userId.setForeground(Color.black);
			
			
			JLabel score = new JLabel("★★★★새로운 점수★★★★");
			score.setBounds(100, 120, 200, 40);
			score.setFont(new Font("Monospaced", Font.BOLD, 11));
			score.setForeground(Color.BLACK);
			
			
			JLabel score_ = new JLabel(String.valueOf(gameScore));
			score_.setBounds(100, 140, 100, 40);
			score_.setForeground(Color.BLACK);
			score_.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			
			JLabel raking = new JLabel("★★★★새로운 랭킹★★★★ ");
			raking.setBounds(100,180,200,40);
			raking.setFont(new Font("Monospaced", Font.BOLD, 11));
			raking.setForeground(Color.BLACK);
			
			
			JLabel raking_ = new JLabel(String.valueOf(ranking));
			raking_.setBounds(100,200,140,40);				
			raking_.setForeground(Color.BLACK);
			raking_.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			
			JLabel hyperlink = new JLabel("랭킹 보기");
			hyperlink.setBounds(100,220,140,40);				
			hyperlink.setForeground(Color.BLUE.darker());
			hyperlink.setFont(new Font("Monospaced", Font.BOLD, 20));
			hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			hyperlink.addMouseListener(new MouseAdapter() {
				 
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        // the user clicks on the label
			    

			            try {
							Desktop.getDesktop().browse(new URI("http://15.164.218.103/result.php?id=" + TetrisMain.userId));
						} catch (URISyntaxException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
			            
			    }
			 
			    @Override
			    public void mouseEntered(MouseEvent e) {
			        // the mouse has entered the label
			    }
			 
			    @Override
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    }
			});
			
			
			
			
			frame.add(record);
			frame.add(userId);
			frame.add(score);
			frame.add(score_);
			frame.add(raking);
			frame.add(raking_);
			frame.add(hyperlink);
			
			
		}else if (this.info.equalsIgnoreCase("high")) {
			JLabel record = new JLabel("게임결과"); 
			record.setBounds(100,40,100,40);
			record.setFont(new Font("Monospaced", Font.BOLD, 13));
			record.setForeground(Color.BLACK);
			JLabel userId = new JLabel(TetrisMain.userId + " 님");
			userId.setBounds(100, 100, 100, 40);
			userId.setForeground(Color.black);
			
			
			JLabel score = new JLabel("☆☆☆☆점수 갱신☆☆☆☆");
			score.setBounds(100, 120, 200, 40);
			score.setFont(new Font("Monospaced", Font.BOLD, 11));
			score.setForeground(Color.BLACK);
			
			
			JLabel score_ = new JLabel(String.valueOf(gameScore));
			score_.setBounds(100, 140, 100, 40);
			score_.setForeground(Color.BLACK);
			score_.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			
			JLabel raking = new JLabel("랭킹");
			raking.setBounds(100,180,140,40);
			raking.setFont(new Font("Monospaced", Font.BOLD, 11));
			raking.setForeground(Color.BLACK);
			
			
			JLabel raking_ = new JLabel(String.valueOf(ranking));
			raking_.setBounds(100,200,140,40);		
			raking_.setForeground(Color.BLACK);
			raking_.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			
			JLabel hyperlink = new JLabel("랭킹 보기");
			hyperlink.setBounds(50,220,140,40);				
			hyperlink.setForeground(Color.BLUE.darker());
			hyperlink.setFont(new Font("Monospaced", Font.BOLD, 20));
			hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			hyperlink.addMouseListener(new MouseAdapter() {
				 
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        // the user clicks on the label
			    

			            try {
							Desktop.getDesktop().browse(new URI("http://15.164.218.103/result.php?id=" + TetrisMain.userId));
						} catch (URISyntaxException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
			            
			    }
			 
			    @Override
			    public void mouseEntered(MouseEvent e) {
			        // the mouse has entered the label
			    }
			 
			    @Override
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    }
			});
			
			
			frame.add(record);
			frame.add(userId);
			frame.add(score);
			frame.add(score_);
			frame.add(raking);
			frame.add(raking_);
			frame.add(hyperlink);
			
		}else if(this.info.equalsIgnoreCase("low")) {
			JLabel record = new JLabel("게임결과"); 
			record.setBounds(100,40,100,40);
			record.setFont(new Font("Monospaced", Font.BOLD, 13));
			record.setForeground(Color.BLACK);
			
			
			JLabel userId = new JLabel(TetrisMain.userId + " 님");
			userId.setBounds(100, 100, 100, 40);
			userId.setForeground(Color.black);
			
			
			JLabel score = new JLabel("점수");
			score.setBounds(100, 120, 200, 40);
			score.setFont(new Font("Monospaced", Font.BOLD, 11));
			score.setForeground(Color.BLACK);
			
			
			JLabel score_ = new JLabel(String.valueOf(gameScore));
			score_.setBounds(100, 140, 100, 40);
			score_.setForeground(Color.BLACK);
			score_.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			
			JLabel raking = new JLabel("랭킹 ");
			raking.setBounds(100,180,200,40);
			raking.setFont(new Font("Monospaced", Font.BOLD, 11));
			raking.setForeground(Color.BLACK);
			
			
			JLabel raking_ = new JLabel(String.valueOf(ranking));
			raking_.setBounds(100,200,140,40);			
			raking_.setForeground(Color.BLACK);
			raking_.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			JLabel hyperlink = new JLabel("랭킹 보기");
			hyperlink.setBounds(50,220,140,40);				
			hyperlink.setForeground(Color.BLUE.darker());
			hyperlink.setFont(new Font("Monospaced", Font.BOLD, 20));
			hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			hyperlink.addMouseListener(new MouseAdapter() {
				 
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        // the user clicks on the label
			    

			            try {
							Desktop.getDesktop().browse(new URI("http://15.164.218.103/result.php?id=" + TetrisMain.userId));
						} catch (URISyntaxException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
			            
			    }
			 
			    @Override
			    public void mouseEntered(MouseEvent e) {
			        // the mouse has entered the label
			    }
			 
			    @Override
			    public void mouseExited(MouseEvent e) {
			        // the mouse has exited the label
			    }
			});
			
			
			frame.add(record);
			frame.add(userId);
			frame.add(score);
			frame.add(score_);
			frame.add(raking);
			frame.add(raking_);
			frame.add(hyperlink);
		}
		
	}
	

	
	
	public void ComponentNotLogin() {
		
		if(TetrisMain.GameMode == 3) {
			
			JLabel record = new JLabel("게임결과"); 
			record.setBounds(100,40,100,40);
			record.setFont(new Font("Monospaced", Font.BOLD, 13));
			record.setForeground(Color.BLACK);
			
			
			JLabel timeLabel = new JLabel("경과시간");
			timeLabel.setBounds(100, 120, 200, 40);
			timeLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
			timeLabel.setForeground(Color.BLACK);
			
			
			JLabel timeresult = new JLabel(String.valueOf(time));
			timeresult.setBounds(100, 140, 100, 40);
			timeresult.setForeground(Color.BLACK);
			timeresult.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			JLabel text1 = new JLabel("로그인 되어 있지 않습니다");
			text1.setBounds(0,180,200,80);
			text1.setFont(new Font("Monospaced", Font.BOLD, 11));
			text1.setForeground(Color.BLACK);
			
			JLabel text2 = new JLabel("기록을 등록하고 랭킹을 보고 싶다면 로그인 후 플레이!");
			text2.setBounds(0,200,500 ,80);
			text2.setFont(new Font("Monospaced", Font.BOLD, 11));
			text2.setForeground(Color.BLACK);

			
			frame.add(timeLabel);
			frame.add(timeresult);
			frame.add(text1);
			frame.add(text2);
			
		}else {
			JLabel record = new JLabel("게임결과"); 
			record.setBounds(100,40,100,40);
			record.setFont(new Font("Monospaced", Font.BOLD, 13));
			record.setForeground(Color.BLACK);
			
			
			JLabel scoreLabel = new JLabel("점수");
			scoreLabel.setBounds(100, 120, 200, 40);
			scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
			scoreLabel.setForeground(Color.BLACK);
			
			
			JLabel scoreResult = new JLabel(String.valueOf(gameScore));
			scoreResult.setBounds(100, 140, 100, 40);
			scoreResult.setForeground(Color.BLACK);
			scoreResult.setFont(new Font("Monospaced", Font.BOLD, 20));
			
			JLabel text1 = new JLabel("로그인 되어 있지 않습니다");
			text1.setBounds(0,180,200,80);
			text1.setFont(new Font("Monospaced", Font.BOLD, 11));
			text1.setForeground(Color.BLACK);
			
			JLabel text2 = new JLabel("기록을 등록하고 랭킹을 보고 싶다면 로그인 후 플레이!");
			text2.setBounds(0,200,500 ,80);
			text2.setFont(new Font("Monospaced", Font.BOLD, 11));
			text2.setForeground(Color.BLACK);

		
			frame.add(record);
			frame.add(scoreLabel);
			frame.add(scoreResult);
			frame.add(text1);
			frame.add(text2);
		}

		
	}
}
