package com.tetris.main;

import java.awt.Color;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.tetris.window.Button;
import com.tetris.window.Tetris;

/**
 * 
 * @author minshik_kim
 * 2019 / 12 / 05 이미지 버튼 새롭게 바꾸었습니다.
 *
 */

public class TetrisOpening extends JFrame {
   
   private Image screenImage;
   private Graphics screenGraphic;
   private ImageIcon exitButtonBasicImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/exitButtonBasic.png"));
   private ImageIcon exitButtonEnteredImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/exitButtonEntered.png"));
   
   private ImageIcon startButtonBasicImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/startBtnBasic.png"));
   private ImageIcon startButtonEnteredImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/startBtnEnter.png"));
   
   private ImageIcon quitButtonBasicImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/quitBtnBasic.png"));
   private ImageIcon quitButtonEnteredImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/quitBtnEnter.png"));
   
   /**
    *  로그인, 설정, 게임방법 버튼 이미지 추가 
    *  by kms
    */
   private ImageIcon loginButtonImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/loginBtnBasic.png"));
   private ImageIcon loginButtonEnterImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/loginBtnEnter.png"));
   
   private ImageIcon settingButtonImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/settingBtnBasic.png"));
   private ImageIcon settingButtonEnterImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/settingBtnEnter.png"));
   
   private ImageIcon howToPlayButtonImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/howToPlayBtnBasic.png")); 
   private ImageIcon howToPlayButtonEnterImage = new ImageIcon(TetrisMain.class.getResource("../../../Images/howToPlayBtnEnter.png"));
   
		   
		   
		   
		   
		   
		   
		   
		   
		   
   private Image background = new ImageIcon(TetrisMain.class.getResource("../../../Images/IntroBackground.jpg")).getImage();
   private JLabel menuBar = new JLabel(new ImageIcon(TetrisMain.class.getResource("../../../Images/menuBar.png")));
   
   private JButton exitButton = new JButton(exitButtonBasicImage);
   private JButton startButton = new JButton(startButtonBasicImage);
   private JButton quitButton = new JButton(quitButtonBasicImage);
   
   
   private JButton loginButton = new JButton(loginButtonImage);
   private JButton settingButton = new JButton(settingButtonImage);
   private JButton howToPlayButton = new JButton(howToPlayButtonImage);
   
   
   private int mouseX, mouseY;
   private boolean isMainScreen = false;
   
   public TetrisOpening() {
	   
	   setUndecorated(true);
	   setTitle("Tetris");
	   setSize(TetrisMain.SCREEN_WIDTH, TetrisMain.SCREEN_HEIGHT);
	   setResizable(false);
	   setLocationRelativeTo(null);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setVisible(true);
	   setBackground(new Color(0, 0, 0, 0));
	   setLayout(null);
	   
	   
	   
	   
	   
	   exitButton.setBounds(880, 0, 30, 30);
	   exitButton.setBounds(890, 0, 30, 30);
	   exitButton.setBorderPainted(false);
	   exitButton.setContentAreaFilled(false);
	   exitButton.setFocusPainted(false);
	   exitButton.addMouseListener(new MouseAdapter() {
		   
		   @Override
		   public void mouseEntered(MouseEvent e) {
			   exitButton.setIcon(exitButtonEnteredImage);
			   exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			   
		   }
		   @Override
		   public void mouseExited(MouseEvent e) {
			   exitButton.setIcon(exitButtonBasicImage);
			   exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		   }
		   @Override
		   public void mousePressed(MouseEvent e) {
			   
			   try { 
				   Thread.sleep(1000); 
			   } catch(InterruptedException ex) {
				   ex.printStackTrace();
			   } 
			   System.exit(0); 
		   }
	   });
	   
	   add(exitButton); 
	   
	   
	   // 그냥 시작하기 버튼
	   startButton.setBounds(260, 200, 400, 50); 
	   startButton.setBorderPainted(false); 
	   startButton.setContentAreaFilled(false);
	   startButton.setFocusPainted(false); 
	   startButton.addMouseListener(new MouseAdapter() { 
		   @Override
		   public void mouseEntered(MouseEvent e) { 
			   
			   startButton.setIcon(startButtonEnteredImage);
			   startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			   
		   }
		   @Override
		   public void mouseExited(MouseEvent e) {
			   startButton.setIcon(startButtonBasicImage);
			   startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		   }
		   
		   @Override
		   public void mousePressed(MouseEvent e) { 
			   startButton.setVisible(false);
			   quitButton.setVisible(false);
			   isMainScreen = true;
			   
			  
			   
			   new ModeSelectionWindow(); 
		   }
	   });
	   
	   
	   add(startButton); 
	   
	   
	   
	   
	   quitButton.setBounds(260, 250, 400, 50);
	   quitButton.setBorderPainted(false);
	   quitButton.setContentAreaFilled(false);
	   quitButton.setFocusPainted(false);
	   quitButton.addMouseListener(new MouseAdapter() {
		   @Override
		   public void mouseEntered(MouseEvent e) {
			   quitButton.setIcon(quitButtonEnteredImage);
			   quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			   
		   }
		   @Override
		   public void mouseExited(MouseEvent e) {
			   quitButton.setIcon(quitButtonBasicImage);
			   quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		   }
		   @Override
		   public void mousePressed(MouseEvent e) {
			   try {
				   Thread.sleep(1000);
			   } catch (InterruptedException ex) {
				   ex.printStackTrace();
			   }
			   System.exit(0);
			   
		   }
	   });
	   
	   add(quitButton); 
	   
	   
	   
	   
	   
	   // 로그인 버튼
	   loginButton.setBounds(260, 300, 400, 50);
	   loginButton.setBorderPainted(false);
	   loginButton.setContentAreaFilled(false);
	   loginButton.setFocusPainted(false);
	   loginButton.addMouseListener(new MouseAdapter() {
		   @Override
		   public void mouseEntered(MouseEvent e) {
			   loginButton.setIcon(loginButtonEnterImage);
			   loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			   
		   }
		   @Override
		   public void mouseExited(MouseEvent e) {
			   loginButton.setIcon(loginButtonImage);
			   loginButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		   }
		   @Override
		   public void mousePressed(MouseEvent e) {
//        	 startButton.setVisible(false);
//             quitButton.setVisible(false);
			   //background 占쏙옙占쌩깍옙
//             isMainScreen = true;
			   
			   
			   Login login_frame = new Login();
			   login_frame.setVisible(true);
			   
		   }
	   });
	   
	   
	   add(loginButton); 
	   
	   
	   
	   
	   
	   howToPlayButton.setBounds(260, 350, 400, 50);
	   howToPlayButton.setBorderPainted(false);
	   howToPlayButton.setContentAreaFilled(false);
	   howToPlayButton.setFocusPainted(false);
	   howToPlayButton.addMouseListener(new MouseListener() {
		   
		   @Override
		   public void mouseReleased(MouseEvent e) {
			   // TODO Auto-generated method stub
			   
		   }
		   
		   @Override //게임방법 코드작성 - 경제훈
		   public void mousePressed(MouseEvent e) { 
			   JFrame f=new JFrame();
			   
			   f.setTitle("게임방법");
			   f.setSize(400,300);
			   f.setLocationRelativeTo(null);
			   f.setVisible(true);
			   
			   
			   JLabel howtoplay = new JLabel("★★★게임 조작 방법★★★");
			   howtoplay.setBounds(100,20,200,40);
			   howtoplay.setFont(new Font("Monospaced", Font.BOLD, 13));
			   howtoplay.setForeground(Color.BLACK);
			   f.add(howtoplay);
			   
			   
			   JLabel howtoplay1=new JLabel("1. 이동방법 : ← → ↓");
			   howtoplay1.setBounds(70,60,200,40);
			   howtoplay1.setFont(new Font("Monospaced", Font.BOLD, 13));
			   howtoplay1.setForeground(Color.BLACK);
			   f.add(howtoplay1);
			   
			   
			   JLabel howtoplay2=new JLabel("2. 시계방향 회전 : ↑");
			   howtoplay2.setBounds(70,90,200,40);
			   howtoplay2.setFont(new Font("Monospaced", Font.BOLD, 13));
			   howtoplay2.setForeground(Color.BLACK);
			   f.add(howtoplay2);
			   
			   
			   
			   JLabel howtoplay3=new JLabel("3. 반시계방향 회전 : Z");
			   howtoplay3.setBounds(70,120,200,40);
			   howtoplay3.setFont(new Font("Monospaced", Font.BOLD, 13));
			   howtoplay3.setForeground(Color.BLACK);
			   f.add(howtoplay3);
			   
			   
			   
			   JLabel howtoplay4=new JLabel("4. 아래로 한번에 내리기 : Spacebar");
			   howtoplay4.setBounds(70,150,300,40);
			   howtoplay4.setFont(new Font("Monospaced", Font.BOLD, 13));
			   howtoplay4.setForeground(Color.BLACK);
			   f.add(howtoplay4);
			   
			   
			   
			   JLabel howtoplay5=new JLabel("5. HOLD : Shift");
			   howtoplay5.setBounds(70,180,200,40);
			   howtoplay5.setFont(new Font("Monospaced", Font.BOLD, 13));
			   howtoplay5.setForeground(Color.BLACK);
			   f.add(howtoplay5);
			   
			   
			   
			   JLabel howtoplay6=new JLabel("");
			   howtoplay6.setBounds(70,200,200,40);
			   howtoplay6.setFont(new Font("Monospaced", Font.BOLD, 13));
			   howtoplay6.setForeground(Color.BLACK);
			   f.add(howtoplay6);
			   
			   
			   
			   // TODO Auto-generated method stub
			   
		   }
		   
		   @Override
		   public void mouseExited(MouseEvent e) { 
			   howToPlayButton.setIcon(howToPlayButtonImage);
			   howToPlayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			   
		   }
		   
		   @Override
		   public void mouseEntered(MouseEvent e) { 
			   howToPlayButton.setIcon(howToPlayButtonEnterImage);
			   howToPlayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		   }
		   
		   @Override
		   public void mouseClicked(MouseEvent e) {
			   // TODO Auto-generated method stub
			   
		   }
	   });
	   add(howToPlayButton);
	   
	   
	   // 
	   settingButton.setBounds(260, 400, 400, 50);
	   settingButton.setBorderPainted(false);
	   settingButton.setContentAreaFilled(false);
	   settingButton.setFocusPainted(false);
	   settingButton.addMouseListener(new MouseListener() {
		   
		   @Override
		   public void mouseReleased(MouseEvent e) {
			   // TODO Auto-generated method stub
			   
		   }
		   
		   @Override
		   public void mousePressed(MouseEvent e) { 
			   Button start = new Button();
			   start.FrameShow();			
		   }
		   
		   @Override
		   public void mouseExited(MouseEvent e) {
			   settingButton.setIcon(settingButtonImage);
			   settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));			   
		   }
		   
		   @Override
		   public void mouseEntered(MouseEvent e) {
			   settingButton.setIcon(settingButtonEnterImage);
			   settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
 
		   }
		   
		   @Override
		   public void mouseClicked(MouseEvent e) {
			   // TODO Auto-generated method stub
			   
		   }
	   });
	   add(settingButton);
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   menuBar.setBounds(0, 0, 1280, 30); 
	   menuBar.addMouseListener(new MouseAdapter() {
		   @Override
		   public void mousePressed(MouseEvent e) {  
			   mouseX = e.getX();
			   mouseY = e.getY();
		   }
		   
	   });
	   
	   menuBar.addMouseMotionListener(new MouseMotionAdapter() { 
		   @Override
		   public void mouseDragged(MouseEvent e) {
			   int x = e.getXOnScreen();
			   int y = e.getYOnScreen();
			   setLocation(x - mouseX, y - mouseY); 
		   }
	   });
	   add(menuBar);
	   
	   
   }
   
   public void paint(Graphics g) {
	   screenImage = createImage(TetrisMain.SCREEN_WIDTH, TetrisMain.SCREEN_HEIGHT);
	   screenGraphic = screenImage.getGraphics();
	   screenDraw(screenGraphic);
	   g.drawImage(screenImage, 0, 0, null);
   }
   public void screenDraw(Graphics g) {
      g.drawImage(background, 0, 0, null);
      if(isMainScreen) {
         this.setVisible(false);
      }
      printComponents(g);
      this.repaint();
   }

}
 