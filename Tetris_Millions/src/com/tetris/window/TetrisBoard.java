package com.tetris.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter; // millions
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon; // millions
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane; //millions
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;

import com.tetris.classes.Block;
import com.tetris.classes.TetrisBlock;
import com.tetris.controller.TetrisController;
import com.tetris.main.ApiClient;
import com.tetris.main.GameResultRepo;
import com.tetris.main.Music; // millions
import com.tetris.main.RetrofitApi;
import com.tetris.main.TetrisMain;
import com.tetris.network.DataShip;
import com.tetris.network.GameClient;
import com.tetris.network.GameServer;
import com.tetris.shape.CenterUp;
import com.tetris.shape.LeftTwoUp;
import com.tetris.shape.LeftUp;
import com.tetris.shape.Line;
import com.tetris.shape.Nemo;
import com.tetris.shape.RightTwoUp;
import com.tetris.shape.RightUp;
import com.tetris.window.Button;


/**
 * 
 * @author minshik 네트워크 라이브러리 추가
 *
 */
import retrofit2.*;


public class TetrisBoard extends JPanel implements Runnable, KeyListener, MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;

	private Tetris tetris;
	private GameClient client;

	public static final int BLOCK_SIZE = 20;
	public static final int BOARD_X = 120;
	public static final int BOARD_Y = 50;
	private static int minX = 1, minY = 0;

	public static int maxX = 10;

	private static int maxY = 21;

	private static int down = 50;

	private static int up = 0;
	private static final int MESSAGE_WIDTH = BLOCK_SIZE * 7;
	private static final int MESSAGE_HEIGHT = BLOCK_SIZE * 6;
	public static final int PANEL_WIDTH = 2 * (maxX * BLOCK_SIZE + MESSAGE_WIDTH + BOARD_X);
	public static final int PANEL_HEIGHT = maxY * BLOCK_SIZE + MESSAGE_HEIGHT + BOARD_Y;

	private SystemMessageArea systemMsg = new SystemMessageArea(BLOCK_SIZE * 1, BOARD_Y + BLOCK_SIZE + BLOCK_SIZE * 7,
			BLOCK_SIZE * 5, BLOCK_SIZE * 12);
	private MessageArea messageArea = new MessageArea(this, 0, PANEL_HEIGHT - MESSAGE_HEIGHT,
			PANEL_WIDTH - BLOCK_SIZE * 7, MESSAGE_HEIGHT);
	private JButton btnStart = new JButton("시작하기");
	private JButton btnExit = new JButton("나가기");
	private JButton btnSound1 = new JButton("BGM1");
	private JButton btnSound2 = new JButton("BGM2");
	private JButton btnSound3 = new JButton("BGM3");
	private JCheckBox checkGhost = new JCheckBox("고스트모드", true);
	private JCheckBox checkGrid = new JCheckBox("격자 표시", true);

	private JCheckBox checkEffect = new JCheckBox("효과음", true); // 효과음 checkbox (millions)
	private JCheckBox checkBGM = new JCheckBox("배경음악", true); // 배경음악 checkbox (millions)

	private Integer[] lv = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	private JComboBox<Integer> comboSpeed = new JComboBox<Integer>(lv);
	private int gameSpeed = 1; // hwaaad
	private int initSpeed = 1; // hwaaad
	private String ip;
	private int port;
	private String nickName;
	private Thread th, th2;
	private ArrayList<Block> blockList;
	private ArrayList<TetrisBlock> nextBlocks;
	private TetrisBlock shap;
	private TetrisBlock ghost;
	private TetrisBlock hold;
	private Block[][] map;
	private TetrisController controller;
	private TetrisController controllerGhost;
	public static String timerBuffer; // hwaaad
	public static int oldTime; // hwaaad
	public static int sec; // hwaaad
	public int new_sec;
	public boolean line_add_complete = false; // hwaaad
	private TetrisBlock shap2;// HK
	private ArrayList<Block> blockList2;// HK
	private int EnemyScore;
	private int myScore = 0;
	
	private boolean isPlay = false;
	private boolean isHold = false;
	private boolean usingGhost = true;
	private boolean usingGrid = true;

	public static boolean usingEffect = true; // 효과음 (millions)
	public static boolean usingBGM = true; // 배경음악 (millions)

	private int removeLineCount = 0;
	private int removeLineCombo = 0;

	public ImageIcon icon1;
	public ImageIcon icon2;

	Graphics buff; // 더블버퍼링을 위한 버퍼

	public static Music GameMusic;
	public static Music GameEndSound;
	public int SoundNumber = 1; // 음악 구부늘 위한 번호
	public int FixedSound= 0;
	
	private int pk=0;
	RetrofitApi retrofitApi; // 네트워크 객체 추가 minshik

	public TetrisBoard(Tetris tetris, GameClient client) {
		retrofitApi = ApiClient.getClient_aws().create(RetrofitApi.class); // minshik
		
		
		this.tetris = tetris;
		this.client = client;
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));// 기본크기
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
		this.setFocusable(true);
		btnStart.setBounds(PANEL_WIDTH - BLOCK_SIZE * 7, PANEL_HEIGHT - messageArea.getHeight(), BLOCK_SIZE * 7,
				messageArea.getHeight() / 2);
		btnStart.setFocusable(false);
		btnStart.setEnabled(false);
		btnStart.addActionListener(this);

		btnStart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Music MousePressedSound = new Music("Start.mp3", false);
				MousePressedSound.start();
			}

			public void mouseEntered(MouseEvent e) {
				Music MousePressedSound = new Music("BlockMoveSound.mp3", false);
				MousePressedSound.start();
			}

		}); // 버튼 효과음 millions

		btnExit.setBounds(PANEL_WIDTH - BLOCK_SIZE * 7, PANEL_HEIGHT - messageArea.getHeight() / 2, BLOCK_SIZE * 7,
				messageArea.getHeight() / 2);
		btnExit.setFocusable(false);
		btnExit.addActionListener(this);

		btnExit.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Music MousePressedSound = new Music("Exit.mp3", false);
				MousePressedSound.start();
				comboSpeed.setEnabled(true); // 나가기 눌릴때 다시 활성화 hwaaad
			}

			public void mouseEntered(MouseEvent e) {
				Music MousePressedSound = new Music("BlockMoveSound.mp3", false);
				MousePressedSound.start();
			}

		});

		btnSound1.setBounds(PANEL_WIDTH - BLOCK_SIZE * 7 + 35, 45, 95, 20);
		btnSound1.setFocusable(false);
		btnSound1.addActionListener(this);

		btnSound2.setBounds(PANEL_WIDTH - BLOCK_SIZE * 7 + 35, 65, 95, 20);
		btnSound2.setFocusable(false);
		btnSound2.addActionListener(this);

		btnSound3.setBounds(PANEL_WIDTH - BLOCK_SIZE * 7 + 35, 85, 95, 20);
		btnSound3.setFocusable(false);
		btnSound3.addActionListener(this);

		// 버튼 효과음 millions

		checkGhost.setBounds(PANEL_WIDTH - BLOCK_SIZE * 12 + 35, 5, 95, 20); // 고스트모드 checkbox 왼쪽으로 이동.(millions)
		checkGhost.setBackground(Color.white);
		checkGhost.setForeground(Color.black);
		checkGhost.setFont(new Font("굴림", Font.BOLD, 13));
		checkGhost.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				usingGhost = checkGhost.isSelected();
				TetrisBoard.this.setRequestFocusEnabled(true);
				TetrisBoard.this.repaint();
			}
		});
		checkGrid.setBounds(PANEL_WIDTH - BLOCK_SIZE * 12 + 35, 25, 95, 20); // 격자모드 checkbox 왼쪽으로 이동.(millions)
		checkGrid.setBackground(Color.white);
		checkGrid.setForeground(Color.black);
		checkGrid.setFont(new Font("굴림", Font.BOLD, 13));
		checkGrid.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				usingGrid = checkGrid.isSelected();
				TetrisBoard.this.setRequestFocusEnabled(true);
				TetrisBoard.this.repaint();
			}
		});

		checkEffect.setBounds(PANEL_WIDTH - BLOCK_SIZE * 7 + 35, 5, 95, 20); // 효과음 checkbox 위치 및 디자인(millions)
		checkEffect.setBackground(Color.white);
		checkEffect.setForeground(Color.black);
		checkEffect.setFont(new Font("굴림", Font.BOLD, 13));
		checkEffect.setRequestFocusEnabled(false);
		checkEffect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkEffect.isSelected() == true) {
					usingEffect = true;
				} else {
					usingEffect = false;
				}
				TetrisBoard.this.setRequestFocusEnabled(true);
				TetrisBoard.this.repaint();
			}
		});

		checkBGM.setBounds(PANEL_WIDTH - BLOCK_SIZE * 7 + 35, 25, 95, 20); // 배경음악 checkbox 위치 및 디자인(millions)
		checkBGM.setBackground(Color.white);
		checkBGM.setForeground(Color.black);
		checkBGM.setFont(new Font("굴림", Font.BOLD, 13));
		checkBGM.setRequestFocusEnabled(false);
		checkBGM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBGM.isSelected() == true) {

					usingBGM = true;
					if (isPlay == true) {
						if (GameMusic.isAlive() && GameMusic != null) {
							GameMusic.close();
							if (SoundNumber == 1) {
								GameMusic = new Music("GameMusic1.mp3", true);
								GameMusic.start();
							} else if (SoundNumber == 2) {
								GameMusic = new Music("GameMusic2.mp3", true);
								GameMusic.start();
							} else if (SoundNumber == 3) {
								GameMusic = new Music("GameMusic3.mp3", true);
								GameMusic.start();
							}
						} else {
							if (SoundNumber == 1) {
								GameMusic = new Music("GameMusic1.mp3", true);
								GameMusic.start();
							} else if (SoundNumber == 2) {
								GameMusic = new Music("GameMusic2.mp3", true);
								GameMusic.start();
							} else if (SoundNumber == 3) {
								GameMusic = new Music("GameMusic3.mp3", true);
								GameMusic.start();
							}
						}
					}
				} else {
					if (GameMusic != null && GameMusic.isAlive()) {
						GameMusic.close();

					}
				}

				TetrisBoard.this.setRequestFocusEnabled(true);
				TetrisBoard.this.repaint();
			}
			});
	

		comboSpeed.setBounds(PANEL_WIDTH - BLOCK_SIZE * 13, 5, 45, 20); // 속도 숫자 표시 왼쪽으로 이동.(millions)
		this.add(comboSpeed);

		this.add(systemMsg);
		this.add(messageArea);
		this.add(btnStart);
		this.add(btnExit);
		this.add(btnSound1);
		this.add(btnSound2);
		this.add(btnSound3);
		this.add(checkGhost);
		this.add(checkGrid);

		this.add(checkEffect); // 효과음(millions)
		this.add(checkBGM); /// 배경음악(millions)

		icon1 = new ImageIcon(TetrisMain.class.getResource("../../../Images/gameBackground3.jpg.png"));

	}

	public void startNetworking(String ip, int port, String nickName) {
		this.ip = ip;
		this.port = port;
		this.nickName = nickName;
		this.repaint();
	}

	/**
	 * TODO : 게임시작 게임을 시작한다.
	 */

	public void gameStart(int speed) {
		comboSpeed.setSelectedItem(new Integer(speed));
		gameSpeed = speed; // hwadong
		initSpeed = gameSpeed; // hwadong
		// 돌고 있을 스레드를 정지시킨다.
		if (th != null) {
			try {
				isPlay = false;

				th.join();
				// th2.join(); // hwadong
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//게임 초기 음악 1번으로 설정
		SoundNumber = 1;
		//음악 선택 초기화
		FixedSound = 0; 
		
		if (GameMusic != null && GameMusic.isAlive()) {
			GameMusic.close();
			if (checkBGM.isSelected()) {
				if (SoundNumber == 1) {
					GameMusic = new Music("GameMusic1.mp3", true);
					GameMusic.start();
				} else if (SoundNumber == 2) {
					GameMusic = new Music("GameMusic2.mp3", true);
					GameMusic.start();
				} else if (SoundNumber == 3) {
					GameMusic = new Music("GameMusic3.mp3", true);
					GameMusic.start();
				}

			}
		} else {
			if (checkBGM.isSelected()) {
				if (SoundNumber == 1) {
					GameMusic = new Music("GameMusic1.mp3", true);
					GameMusic.start();
				} else if (SoundNumber == 2) {
					GameMusic = new Music("GameMusic2.mp3", true);
					GameMusic.start();
				} else if (SoundNumber == 3) {
					GameMusic = new Music("GameMusic3.mp3", true);
					GameMusic.start();
				}

			}
		}

		//게임 점수 초기화
		myScore = 0;
		EnemyScore = 0;

		// 맵셋팅
		map = new Block[maxY][maxX];
		blockList = new ArrayList<Block>();
		nextBlocks = new ArrayList<TetrisBlock>();
		blockList2 = new ArrayList<Block>();
		
		/*
		// 정한교 체리 맵 추가 
		{
		for( int i=1; i<6; i ++) {
			for( int k=1; k<11; k++) {
				
				if (i==1 && (k==1 || k==4 || k==5 || k==6 || k==10))
					continue;
				if (i==2 && k==5)
					continue;
				if (i==3 && k==5)
					continue;
				if (i==4 && (k==1 || k==4 || k==5))
					continue;
				if (i == 5 && (k==1 || k==2 || k==3 || k==4 || k==5 || k==6 || k==10))
				    continue;
				
				Block mapblock = new Block(0,0,Color.RED,Color.RED);
				mapblock.setFixGridXY(maxX-k, maxY-i);
				blockList.add(mapblock);
			    map[maxY-i][maxX-k] = mapblock;
			}
		}
		
		// maxX-3, maxX-5 초록블록으로 대체 
				Block mapblock1 = new Block(0,0,Color.GREEN,Color.GREEN);
				mapblock1.setFixGridXY(maxX-3, maxY-5);
				blockList.add(mapblock1);
			    map[maxY-5][maxX-3] = mapblock1;
			    
	    // 초록색 가지 부분
		for( int i=6; i<13; i ++) {
			for( int k=1; k<11; k++) {
				
				if (i==6 && (k!=3 && k!=8))
					continue;
				if (i==7 &&  (k!=3 && k!=4 && k!=7 && k!=8))
					continue;
				if (i==8 && (k!=4 && k!=6 && k!=7))
					continue;
				if (i==9 && (k!=4 && k!=6))
					continue;
				if (i==10 &&  (k!=4 && k!=5 && k!=6))
					continue;
				if (i==11 && k!=4)
					continue;
				if (i==12 && (k!=3 && k!=4 ))
					continue;
				Block mapblock = new Block(0,0,Color.GREEN,Color.GREEN);
				mapblock.setFixGridXY(maxX-k, maxY-i);
				blockList.add(mapblock);
			    map[maxY-i][maxX-k] = mapblock;
			}
		}
	    
		//체리 위에 오렌지색 줄기
		for( int k=1; k<11; k++) {
			int i = 13;
			if (k==7 || k==8 || k==9 || k==10)
				continue;
			Block mapblock = new Block(0,0,Color.orange,Color.orange);
			mapblock.setFixGridXY(maxX-k, maxY-i);
			blockList.add(mapblock);
		    map[maxY-i][maxX-k] = mapblock;
		}
		}
		*/
		
		
		/*
		// 정한교 머쓱타드 맵 추가 
		
		// 머쓱타드 몸통
		for( int i=1; i<12; i ++) {
			for( int k=1; k<11; k++) {
				
				if (i==1 && (k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 ))
					continue;
				if (i==2 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8 ))
					continue;
				if (i==3 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8 && k!=9))
					continue;
				if (i==4 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8 && k!=9))
					continue;
				if (i==5 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8 && k!=9))
					continue;
				if (i==6 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8 && k!=9))
					continue;
				if (i==7 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8 && k!=9))
					continue;
				if (i==8 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8))
					continue;
				if (i==9 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7 && k!=8))
					continue;
				if (i==10 && (k!=1 && k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7))
					continue;
				if (i==11 && (k!=2 && k!=3 && k!=4 && k!=5 && k!=6 && k!=7))
					continue;
				Block mapblock = new Block(0,0,Color.decode("0XFFD700"),Color.decode("0XFFD700"));
				mapblock.setFixGridXY(maxX-k, maxY-i);
				blockList.add(mapblock);
			    map[maxY-i][maxX-k] = mapblock;

			}
		}
		
		// 머쓱타드 모자
		
		for( int i=12; i<14; i ++) {
			for( int k=2; k<8; k++) {
				
				Block mapblock = new Block(0,0,Color.decode("0XF5F5DC"),Color.decode("0XF5F5DC"));
				mapblock.setFixGridXY(maxX-k, maxY-i);
				blockList.add(mapblock);
			    map[maxY-i][maxX-k] = mapblock;
			}
		}
		
		// 머쓱타드 입
		{
		for (int k=4; k<8 ; k++) {
			int i=5;
			Block mapblock = new Block(0,0,Color.decode("0X696969"),Color.decode("0X696969"));
		    mapblock.setFixGridXY(maxX-k, maxY-i);
		    blockList.add(mapblock);
	        map[maxY-i][maxX-k] = mapblock;
		}
		
		
		Block mapblock = new Block(0,0,Color.decode("0X696969"),Color.decode("0X696969"));
	    mapblock.setFixGridXY(maxX-8, maxY-6);
	    blockList.add(mapblock);
        map[maxY-6][maxX-8] = mapblock;
   
		
        Block mapblock1 = new Block(0,0,Color.decode("0X696969"),Color.decode("0X696969"));
	    mapblock1.setFixGridXY(maxX-3, maxY-6);
	    blockList.add(mapblock1);
        map[maxY-6][maxX-3] = mapblock1;
     
		
        // 머쓱타드 눈
        for (int i=8; i<10; i++) {
        	for(int k=1;k<10;k++) {
            	
            	if(i==8 && (k!=2 && k!=4 && k!=6 && k!=8 ))
            		continue;
            	if(i==9 && (k!=3 && k!=7))	
            		continue;
                Block mapblock2 = new Block(0,0,Color.decode("0X696969"),Color.decode("0X696969"));
    	        mapblock2.setFixGridXY(maxX-k, maxY-i);
    	        blockList.add(mapblock2);
                map[maxY-i][maxX-k] = mapblock2;
           	}
        }
        
        // 머쓱타드 땀
        Block mapblock3 = new Block(0,0,Color.decode("0XFFFAFA"),Color.decode("0XFFFAFA"));
	    mapblock3.setFixGridXY(maxX-10, maxY-10);
	    blockList.add(mapblock3);
        map[maxY-10][maxX-10] = mapblock3;
        
        Block mapblock4 = new Block(0,0,Color.decode("0XFFFAFA"),Color.decode("0XFFFAFA"));
	    mapblock4.setFixGridXY(maxX-9, maxY-11);
	    blockList.add(mapblock4);
        map[maxY-11][maxX-9] = mapblock4;
        
        Block mapblock5 = new Block(0,0,Color.decode("0XFFFAFA"),Color.decode("0XFFFAFA"));
	    mapblock5.setFixGridXY(maxX-10, maxY-9);
	    blockList.add(mapblock5);
        map[maxY-9][maxX-10] = mapblock5;
        
        Block mapblock6 = new Block(0,0,Color.decode("0XFFFAFA"),Color.decode("0XFFFAFA"));
	    mapblock6.setFixGridXY(maxX-9, maxY-10);
	    blockList.add(mapblock6);
        map[maxY-10][maxX-9] = mapblock6;
		
*/
		

		// 도형셋팅
		shap = getRandomTetrisBlock();
		ghost = getBlockClone(shap, true);
		hold = null;
		isHold = false;
		controller = new TetrisController(shap, maxX - 1, maxY - 1, map, minX-1, minY-1);
		controllerGhost = new TetrisController(ghost, maxX - 1, maxY - 1, map, minX-1, minY-1);
		this.showGhost();
		for (int i = 0; i < 5; i++) {
			nextBlocks.add(getRandomTetrisBlock());
		}

		// 스레드 셋팅
		isPlay = true;
		th = new Thread(this);
		th.start();
		// th2.start();// hwadong
		stopwatch(1); // hwadong
		comboSpeed.setEnabled(false); // 시작버튼 눌리면 enable(false)
	}

	// TODO : paint
	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight() + 1);

		/*
		*  g.setColor(new Color(196, 240, 180)); g.fillRect(0, 0,
		*  2*(2*BOARD_X+maxX*BLOCK_SIZE)+50, BOARD_Y);
		*  
		*  g.setColor(new Color(196, 240, 180)); g.fillRect(0, BOARD_Y,
		*  2*(2*BOARD_X+maxX*BLOCK_SIZE)+50, maxY*BLOCK_SIZE);
		*/
		

		g.drawImage(icon1.getImage(), 0, 0, null);

		g.setColor(Color.black);
		
		// IP 출력
		g.drawString("ip : " + ip + "     port : " + port, 20, 20);

		// NickName 출력
		g.drawString("닉네임 : " + nickName, 20, 40);

		// 속도
		Font font = g.getFont();
		g.setFont(new Font("굴림", Font.BOLD, 13));
		g.drawString("속도", PANEL_WIDTH - BLOCK_SIZE * 15, 20);
		g.setFont(font);
		// grid hwadong
		g.setColor(Color.lightGray);
		g.fillRect(BOARD_X + BLOCK_SIZE * minX, BOARD_Y, maxX * BLOCK_SIZE + 1, maxY * BLOCK_SIZE + 1);
		g.setColor(Color.white);
		g.fillRect(BLOCK_SIZE * minX, BOARD_Y + BLOCK_SIZE, BLOCK_SIZE * 5, BLOCK_SIZE * 5);
		g.fillRect(BOARD_X + BLOCK_SIZE * minX + (maxX + 1) * BLOCK_SIZE + 1, BOARD_Y + BLOCK_SIZE, BLOCK_SIZE * 5,
				BLOCK_SIZE * 5);
		g.fillRect(BOARD_X + BLOCK_SIZE * minX + (maxX + 1) * BLOCK_SIZE + 1, BOARD_Y + BLOCK_SIZE + BLOCK_SIZE * 7,
				BLOCK_SIZE * 5, BLOCK_SIZE * 12);

		// HOLD NEXT 출력
		g.setColor(Color.black);
		g.setFont(new Font(font.getFontName(), font.getStyle(), 20));
		g.drawString("H O L D", BLOCK_SIZE + 12, BOARD_Y + BLOCK_SIZE + BLOCK_SIZE * 5 + 20);
		g.drawString("N E X T", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 12,
				BOARD_Y + BLOCK_SIZE + BLOCK_SIZE * 5 + 20);
		g.setFont(font);

		// Myscore 출력 hwadong
		g.setColor(Color.black);
		g.setFont(new Font(font.getFontName(), font.getStyle(), 20));
		g.drawString("MY SCORE", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 120, BOARD_Y + 50);
		g.drawString(" " + myScore, BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 160, BOARD_Y + 80);
		
		// now bgm 출력
		g.setColor(Color.black);
		g.setFont(new Font(font.getFontName(), font.getStyle(), 15));
		g.drawString("Now : ", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 460, BOARD_Y + 80);
		if(SoundNumber ==1) {
			g.drawString("BGM1", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 500, BOARD_Y + 80);
		} else if(SoundNumber ==2) {
			g.drawString("BGM2", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 500, BOARD_Y + 80);
		} else if(SoundNumber ==3) {
			g.drawString("BGM3", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 500, BOARD_Y + 80);
		} 
		

		
		//사소한 자리이동 jehun
		// LEVEL 출력 hwadong
		g.setColor(Color.black);
		g.setFont(new Font(font.getFontName(), font.getStyle(), 20));
		g.drawString("L E V E L", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 130, BOARD_Y + 110);
		g.drawString(" " + gameSpeed, BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 160, BOARD_Y + 140);
		
		secToMMSS(  ((int) System.currentTimeMillis() / 1000) - oldTime  );
		g.setColor(Color.black);
		g.setFont(new Font(font.getFontName(), font.getStyle(), 20));
		g.drawString("T I M E", BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 140, BOARD_Y + 170);
		g.drawString(" " + timerBuffer, BOARD_X + BLOCK_SIZE + (maxX + 1) * BLOCK_SIZE + 1 + 140, BOARD_Y + 200);
		
		// 그리드 표시 hwadong
		g.setColor(Color.BLACK);
		g.drawLine(BOARD_X + BLOCK_SIZE * minX, BOARD_Y + BLOCK_SIZE * ( minY),
				BOARD_X + (maxX + minX) * BLOCK_SIZE, BOARD_Y + BLOCK_SIZE * ( minY));
		g.drawLine(BOARD_X + BLOCK_SIZE * minX, BOARD_Y + BLOCK_SIZE * (maxY + minY),
				BOARD_X + (maxX + minX) * BLOCK_SIZE, BOARD_Y + BLOCK_SIZE * (maxY + minY));
		g.drawLine(BOARD_X + BLOCK_SIZE * ( minX), BOARD_Y + BLOCK_SIZE * minY,
				BOARD_X + BLOCK_SIZE * ( minX), BOARD_Y + BLOCK_SIZE * (minY + maxY));
		g.drawLine(BOARD_X + BLOCK_SIZE * (maxX + minX), BOARD_Y + BLOCK_SIZE * minY,
				BOARD_X + BLOCK_SIZE * (maxX + minX), BOARD_Y + BLOCK_SIZE * (minY + maxY));
		
		if (usingGrid) {
			g.setColor(Color.DARK_GRAY);
			for (int i = 1; i < maxY; i++)
				g.drawLine(BOARD_X + BLOCK_SIZE * minX, BOARD_Y + BLOCK_SIZE * (i + minY),
						BOARD_X + (maxX + minX) * BLOCK_SIZE, BOARD_Y + BLOCK_SIZE * (i + minY));
			for (int i = 1; i < maxX; i++)
				g.drawLine(BOARD_X + BLOCK_SIZE * (i + minX), BOARD_Y + BLOCK_SIZE * minY,
						BOARD_X + BLOCK_SIZE * (i + minX), BOARD_Y + BLOCK_SIZE * (minY + maxY));
			for (int i = 1; i < 5; i++)
				g.drawLine(BLOCK_SIZE * minX, BOARD_Y + BLOCK_SIZE * (i + 1), BLOCK_SIZE * (minX + 5) - 1,
						BOARD_Y + BLOCK_SIZE * (i + 1));
			for (int i = 1; i < 5; i++)
				g.drawLine(BLOCK_SIZE * (minY + i + 1), BOARD_Y + BLOCK_SIZE, BLOCK_SIZE * (minY + i + 1),
						BOARD_Y + BLOCK_SIZE * (minY + 6) - 1);
			for (int i = 1; i < 5; i++)
				g.drawLine(BOARD_X + BLOCK_SIZE * minX + (maxX + 1) * BLOCK_SIZE + 1, BOARD_Y + BLOCK_SIZE * (i + 1),
						BOARD_X + BLOCK_SIZE * minX + (maxX + 1) * BLOCK_SIZE + BLOCK_SIZE * 5,
						BOARD_Y + BLOCK_SIZE * (i + 1));
			for (int i = 1; i < 5; i++)
				g.drawLine(BOARD_X + BLOCK_SIZE * minX + (maxX + 1 + i) * BLOCK_SIZE + 1, BOARD_Y + BLOCK_SIZE,
						BOARD_X + BLOCK_SIZE * minX + BLOCK_SIZE + BLOCK_SIZE * (10 + i) + 1,
						BOARD_Y + BLOCK_SIZE * 6 - 1);
		}
        

		int x = 0, y = 0, newY = 0;
		if (hold != null) {
			x = 0;
			y = 0;
			newY = 3;
			x = hold.getPosX();
			y = hold.getPosY();
			hold.setPosX(-4 + minX);
			hold.setPosY(newY + minY);
			hold.drawBlock(g);
			hold.setPosX(x);
			hold.setPosY(y);
		}

		if (nextBlocks != null) {
			x = 0;
			y = 0;
			newY = 3;
			for (int i = 0; i < nextBlocks.size(); i++) {
				TetrisBlock block = nextBlocks.get(i);
				x = block.getPosX();
				y = block.getPosY();
				block.setPosX(13 + minX);
				block.setPosY(newY + minY);
				if (newY == 3)
					newY = 6;
				block.drawBlock(g);
				block.setPosX(x);
				block.setPosY(y);
				newY += 3;
			}
		}

		if (blockList != null) {
			x = 0;
			y = 0;
			for (int i = 0; i < blockList.size(); i++) {
				Block block = blockList.get(i);
				x = block.getPosGridX();
				y = block.getPosGridY();
				block.setPosGridX(x + minX);
				block.setPosGridY(y + minY);
				block.drawColorBlock(g);
				block.setPosGridX(x);
				block.setPosGridY(y);
			}
		}

		if (ghost != null) {

			if (usingGhost) {
				x = 0;
				y = 0;
				x = ghost.getPosX();
				y = ghost.getPosY();
				ghost.setPosX(x + minX);
				ghost.setPosY(y + minY);
				ghost.drawBlock(g);
				ghost.setPosX(x);
				ghost.setPosY(y);
			}
		}

		if (shap != null) {
			x = 0;
			y = 0;
			x = shap.getPosX();
			y = shap.getPosY();
			shap.setPosX(x + minX);
			shap.setPosY(y + minY);
			shap.drawBlock(g);
			shap.setPosX(x);
			shap.setPosY(y);
		}

		try {
			drawBlockShap(shap2, g);
			drawBlockDeposit(blockList2, g);
			drawEnemyScore(EnemyScore, g);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} // repaint_drawBlock
	}

	/* 상대 블록 그리기 HK */

	public void drawBlockShap(TetrisBlock shap, Graphics g) {
		if (shap != null) {
			int x = 0, y = 0;
			x = shap.getPosX();
			y = shap.getPosY();
			shap.setPosX(x + 24);
			shap.setPosY(y);
			shap.drawBlock(g);
			shap.setPosX(x);
			shap.setPosY(y);
		}
	}

	public void drawBlockShap(TetrisBlock shap) {
		drawBlockShap(shap, getGraphics());
	}// drawBlockShap

	public void drawBlockDeposit(ArrayList<Block> blockList, Graphics g) {
		if (blockList != null) {

			int x = 0, y = 0;
			for (int i = 0; i < blockList.size(); i++) {
				Block block = blockList.get(i);
				x = block.getPosGridX();
				y = block.getPosGridY();
				block.setPosGridX(x + 24);
				block.setPosGridY(y);
				block.drawColorBlock(g);
				block.setPosGridX(x);
				block.setPosGridY(y);
			}
		}
	}

	public void drawBlockDeposit(ArrayList<Block> blockList) {
		drawBlockDeposit(blockList, getGraphics());
	}// drawBlockDeposit
	
	
	//상대 점수 그리기 millions 미완성
	
	public void drawEnemyScore(int EnemyScore, Graphics g) {
	
		}
	
	public void drawEnemyScore(int EnemyScore) {
		drawEnemyScore(EnemyScore, getGraphics());
		
	}

	@Override
	public void run() {
		// int countMove = (21 - (int) comboSpeed.getSelectedItem()) * 5;
		int countMove = (21 - (int) gameSpeed) * 5;
		int countDown = 0;
		int countUp = up;

		while (isPlay) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (countDown != 0) {
				countDown--;
				if (countDown == 0) {

					if (controller != null && !controller.moveDown())
						this.fixingTetrisBlock();
				}
				this.repaint();

			}

			countMove--;
			if (countMove == 0) {
				// countMove = (21 - (int) comboSpeed.getSelectedItem()) * 5;
				countMove = (21 - (int) gameSpeed) * 5;
				if (controller != null && !controller.moveDown())
					countDown = down;
				else
					this.showGhost();
			}

			if (countUp != 0) {
				countUp--;
				if (countUp == 0) {
					countUp = up;
					addBlockLine(1);
				}
			}
			// 시간별 난이도 조절
			
			sec = secToMMSS(  ((int) System.currentTimeMillis() / 1000) - oldTime  );
			new_sec = (int)System.currentTimeMillis();
			if (gameSpeed >= 15 && sec % 15 == 14 && line_add_complete == false) {
				addBlockLine(1);
				line_add_complete = true;
			}
			if(sec % 15 == 0) 
				line_add_complete = false;
			this.repaint();
		} // while()
	}// run()

	/**
	 * 맵(보이기, 논리)을 상하로 이동한다.
	 * 
	 * @param lineNumber
	 * @param num        -1 or 1
	 */
	public void dropBoard(int lineNumber, int num) {

		// 맵을 떨어트린다.
		this.dropMap(lineNumber, num);

		// 좌표바꿔주기(1만큼증가)
		this.changeTetrisBlockLine(lineNumber, num);

		// 다시 체크하기
		this.checkMap();

		// 고스트 다시 뿌리기
		this.showGhost();
		
		System.out.println("내 점수 myScore : " + myScore  +" / (int)myScore / 500 >= (gameSpeed - initSpeed + 1)) 의 값  : " + String.valueOf(((int)myScore / 500 >= (gameSpeed - initSpeed + 1))));
		System.out.println("Myscore/500 : " + myScore / 500);
		System.out.println("gameSpeed : " + gameSpeed);
		System.out.println("initSpeed : " + initSpeed);
		System.out.println("(gameSpeed - initSpeed + 1) : " + (gameSpeed - initSpeed + 1));
	
		
		
		// hwadong
		if (((int)myScore / 500 >= (gameSpeed - initSpeed + 1)) && gameSpeed < 20) {
			++gameSpeed;
			changeSpeed(gameSpeed);
			System.out.println("myScore : " + myScore + "/ gameSpeed : " + gameSpeed);
			this.repaint();
			
		}
		/*if (gameSpeed == 20 && isPlay) {
			try {
				addBlockLine(1);
				Thread.sleep(3000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}

	/**
	 * lineNumber의 위쪽 라인들을 모두 num칸씩 내린다.
	 * 
	 * @param lineNumber
	 * @param num        칸수 -1,1
	 */
	private void dropMap(int lineNumber, int num) {
		if (num == 1) {
			// 한줄씩 내리기
			for (int i = lineNumber; i > 0; i--) {
				for (int j = 0; j < map[i].length; j++) {
					map[i][j] = map[i - 1][j];
				}
			}

			// 맨 윗줄은 null로 만들기
			for (int j = 0; j < map[0].length; j++) {
				map[0][j] = null;
			}
		} else if (num == -1) {
			// 한줄씩 올리기
			for (int i = 1; i <= lineNumber; i++) {
				for (int j = 0; j < map[i].length; j++) {
					map[i - 1][j] = map[i][j];
				}
			}

			// removeLine은 null로 만들기
			for (int j = 0; j < map[0].length; j++) {
				map[lineNumber][j] = null;
			}
		}
	}

	/**
	 * lineNumber의 위쪽 라인들을 모두 num만큼 이동시킨다.
	 * 
	 * @param lineNumber
	 * @param num        이동할 라인
	 */
	private void changeTetrisBlockLine(int lineNumber, int num) {
		int y = 0, posY = 0;
		for (int i = 0; i < blockList.size(); i++) {
			y = blockList.get(i).getY();
			posY = blockList.get(i).getPosGridY();
			if (y <= lineNumber)
				blockList.get(i).setPosGridY(posY + num);
		}
	}

	/**
	 * 테트리스 블럭을 고정시킨다.
	 */
	private void fixingTetrisBlock() {
		synchronized (this) {
			if (stop) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		boolean isCombo = false;
		removeLineCount = 0;

		// drawList 추가
		for (Block block : shap.getBlock()) {
			blockList.add(block);
		}

		// check
		isCombo = checkMap();
		
		
		// 1줄파괴시 50점 추가 jehun
		if(pk == 1) {
			myScore += 50;
		} else if (pk == 2) {
			myScore += 100;
		} else if(pk == 3) {
			myScore += 200;
		} else if(pk == 4) {
			myScore += 300;
		} else if(pk >= 5) {
			myScore +=400;
		}
		pk = 0;


		if (isCombo) {
			removeLineCombo++;
			
		}
		else
			removeLineCombo = 0;

		// 콜백메소드
		this.getFixBlockCallBack(blockList, removeLineCombo, removeLineCount);

		// 다음 테트리스 블럭을 가져온다.
		this.nextTetrisBlock();

		// 홀드가능상태로 만들어준다.
		isHold = false;
	}// fixingTetrisBlock()

	/**
	 * 
	 * @return true-지우기성공, false-지우기실패
	 */
	private boolean checkMap() {
		boolean isCombo = false;
		int count = 0;
		Block mainBlock;

		for (int i = 0; i < blockList.size(); i++) {
			mainBlock = blockList.get(i);

			// map에 추가
			if (mainBlock.getY() < 0 || mainBlock.getY() >= maxY)
				continue;

			if (mainBlock.getY() < maxY && mainBlock.getX() < maxX)
				map[mainBlock.getY()][mainBlock.getX()] = mainBlock;
			
			if (mainBlock.getY() == 1 && mainBlock.getX() > 2 && mainBlock.getX() < 7) {
				this.gameEndCallBack();
				break; //줄이 꽉 찼을 경우에 게임을 종료한다. 
			}
			// 블록이 차오를 경우 음악 전환
			else if (mainBlock.getY() == 13 && checkBGM.isSelected() == true && SoundNumber == 1 && FixedSound == 0) { 
				SoundNumber = 2;  
				if (GameMusic.isAlive() && GameMusic != null) {
		               GameMusic.close();
		               GameMusic = new Music("GameMusic2.mp3", true);
		               GameMusic.start();
		            } else {
		               GameMusic = new Music("GameMusic2.mp3", true);
		               GameMusic.start();
		            } // 블록 8줄 쌓였을 경우 && 체크박스 활성화 && 1번 사운드 재생 && 음악선택 버튼 안눌러야함
			}else if (mainBlock.getY() == 6 && checkBGM.isSelected() == true && SoundNumber == 2 && FixedSound == 0) { 
				SoundNumber = 3;  
				if (GameMusic.isAlive() && GameMusic != null) {
		               GameMusic.close();
		               GameMusic = new Music("GameMusic3.mp3", true);
		               GameMusic.start();
		            } else {
		               GameMusic = new Music("GameMusic3.mp3", true);
		               GameMusic.start();
		            } // 블록 15줄 쌓였을 경우 && 체크박스 활성화 && 2번 사운드 재생 && 음악선택 버튼 안눌러야함
			}
			

			// 1줄개수 체크
			count = 0;
			for (int j = 0; j < maxX; j++) {
				if (map[mainBlock.getY()][j] != null)
					count++;

			}

			// block의 해당 line을 지운다.
			if (count == maxX) {
				removeLineCount++;
				// myScore += 100; //hwadong
				EnemyScore += 50;
				this.removeBlockLine(mainBlock.getY());
				isCombo = true;
				
				pk += 1;
			}

		}
		return isCombo;
	}

	/**
	 * 테트리스 블럭 리스트에서 테트리스 블럭을 받아온다.
	 */
	public void nextTetrisBlock() {
		shap = nextBlocks.get(0);
		this.initController();
		nextBlocks.remove(0);
		nextBlocks.add(getRandomTetrisBlock());
	}

	private void initController() {
		controller.setBlock(shap);
		ghost = getBlockClone(shap, true);
		controllerGhost.setBlock(ghost);
	}

	/**
	 * lineNumber 라인을 삭제하고, drawlist에서 제거하고, map을 아래로 내린다.
	 * 
	 * @param lineNumber 삭제라인
	 */
	private void removeBlockLine(int lineNumber) {
		if(checkEffect.isSelected() == true) {
			new Music("Clear.mp3", false).start();
		}
		// 1줄을 지워줌
		for (int j = 0; j < maxX; j++) {
			for (int s = 0; s < blockList.size(); s++) {
				Block b = blockList.get(s);
				if (b == map[lineNumber][j])
					blockList.remove(s);
			}
			map[lineNumber][j] = null;
		} // for(j)

		this.dropBoard(lineNumber, 1);
		//this.myScore+= 50; // hwaaad
	}

	/**
	 * TODO : 게임종료콜벡 게임이 종료되면 실행되는 메소드
	 */
	public void gameEndCallBack() {

		// client.gameover();
		this.isPlay = false;
		if (GameMusic != null && GameMusic.isAlive()) {
			GameMusic.close();
		}
		GameEndSound = new Music("GameOver.mp3", false);
		GameEndSound.start();
//		ImageIcon popupicon = new ImageIcon(TetrisMain.class.getResource("../../../Images/GAMEOVER.PNG"));
//		JOptionPane.showMessageDialog(null, null, "The End", JOptionPane.ERROR_MESSAGE, popupicon);
		stopwatch(0);
		comboSpeed.setEnabled(true); // combobox 잠금 hwadong
		
		getResult(TetrisMain.userId, TetrisMain.GameMode, myScore);
	}

	/**
	 * 고스트블럭을 보여준다.
	 */
	private void showGhost() {
		ghost = getBlockClone(shap, true);
		controllerGhost.setBlock(ghost);
		controllerGhost.moveQuickDown(shap.getPosY(), true);
	}

	/**
	 * 랜덤으로 테트리스 블럭을 생성하고 반환한다.
	 * 
	 * @return 테트리스 블럭
	 */
	public TetrisBlock getRandomTetrisBlock() {
		switch ((int) (Math.random() * 7)) {
		case TetrisBlock.TYPE_CENTERUP:
			return new CenterUp(4, 1);
		case TetrisBlock.TYPE_LEFTTWOUP:
			return new LeftTwoUp(4, 1);
		case TetrisBlock.TYPE_LEFTUP:
			return new LeftUp(4, 1);
		case TetrisBlock.TYPE_RIGHTTWOUP:
			return new RightTwoUp(4, 1);
		case TetrisBlock.TYPE_RIGHTUP:
			return new RightUp(4, 1);
		case TetrisBlock.TYPE_LINE:
			return new Line(4, 1);
		case TetrisBlock.TYPE_NEMO:
			return new Nemo(4, 1);
		}
		return null;
	}

	/**
	 * tetrisBlock과 같은 모양으로 고스트의 블럭모양을 반환한다.
	 * 
	 * @param tetrisBlock 고스트의 블럭모양을 결정할 블럭
	 * @return 고스트의 블럭모양을 반환
	 */
	public TetrisBlock getBlockClone(TetrisBlock tetrisBlock, boolean isGhost) {
		TetrisBlock blocks = null;
		switch (tetrisBlock.getType()) {
		case TetrisBlock.TYPE_CENTERUP:
			blocks = new CenterUp(4, 1);
			break;
		case TetrisBlock.TYPE_LEFTTWOUP:
			blocks = new LeftTwoUp(4, 1);
			break;
		case TetrisBlock.TYPE_LEFTUP:
			blocks = new LeftUp(4, 1);
			break;
		case TetrisBlock.TYPE_RIGHTTWOUP:
			blocks = new RightTwoUp(4, 1);
			break;
		case TetrisBlock.TYPE_RIGHTUP:
			blocks = new RightUp(4, 1);
			break;
		case TetrisBlock.TYPE_LINE:
			blocks = new Line(4, 1);
			break;
		case TetrisBlock.TYPE_NEMO:
			blocks = new Nemo(4, 1);
			break;
		}
		if (blocks != null && isGhost) {
			blocks.setGhostView(isGhost);
			blocks.setPosX(tetrisBlock.getPosX());
			blocks.setPosY(tetrisBlock.getPosY());
			blocks.rotation(tetrisBlock.getRotationIndex());
		}
		return blocks;
	}

	/**
	 * TODO : 콜백메소드 테트리스 블럭이 고정될 때 자동 호출 된다.
	 * 
	 * @param removeCombo   현재 콤보 수
	 * @param removeMaxLine 한번에 지운 줄수
	 */
	public void getFixBlockCallBack(ArrayList<Block> blockList, int removeCombo, int removeMaxLine) {
		if (removeCombo < 3) {
			if (removeMaxLine == 3)
				client.addBlock(1);
			else if (removeMaxLine == 4)
				client.addBlock(3);
		} else if (removeCombo < 10) {
			if (removeMaxLine == 3)
				client.addBlock(2);
			else if (removeMaxLine == 4)
				client.addBlock(4);
			else
				client.addBlock(1);
		} else {
			if (removeMaxLine == 3)
				client.addBlock(3);
			else if (removeMaxLine == 4)
				client.addBlock(5);
			else
				client.addBlock(2);
		}
	}

	/**
	 * 블럭을 홀드시킨다.
	 */
	public void playBlockHold() {
		if (isHold)
			return;

		if (hold == null) {
			new Music("Save.mp3", false).start();
			hold = getBlockClone(shap, false);
			this.nextTetrisBlock();
		} else {
			new Music("Save.mp3", false).start();
			TetrisBlock tmp = getBlockClone(shap, false);
			shap = getBlockClone(hold, false);
			hold = getBlockClone(tmp, false);
			this.initController();
		}

		isHold = true;
	}

	/**
	 * 가장 밑에 줄에 블럭을 생성한다.
	 * 
	 * @param numOfLine
	 */
	boolean stop = false;

	public void addBlockLine(int numOfLine) {
		stop = true;
		// 내리기가 있을 때까지 대기한다.
		// 내리기를 모두 실행한 후 다시 시작한다.
		Block block;
		int rand = (int) (Math.random() * maxX);
		for (int i = 0; i < numOfLine; i++) {
			this.dropBoard(maxY - 1, -1);
			for (int col = 0; col < maxX; col++) {
				if (col != rand) {
					block = new Block(0, 0, Color.GRAY, Color.GRAY);
					block.setPosGridXY(col, maxY - 1);
					blockList.add(block);
					map[maxY - 1][col] = block;
				}
			}
			// 만약 내려오는 블럭과 겹치면 블럭을 위로 올린다.
			boolean up = false;
			for (int j = 0; j < shap.getBlock().length; j++) {
				Block sBlock = shap.getBlock(j);
				if (map[sBlock.getY()][sBlock.getX()] != null) {
					up = true;
					break;
				}
			}
			if (up) {
				controller.moveDown(-1);
			}
		}

		this.showGhost();
		this.repaint();
		synchronized (this) {
			stop = false;
			this.notify();
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		Button button = new Button();
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			messageArea.requestFocus();
		}
		if (!isPlay)
			return;
		if (e.getKeyCode() == button.getLeft_key()) {
			if (usingEffect)
				new Music("Left.mp3", false).start(); // millions
			controller.moveLeft();
			controllerGhost.moveLeft();
		} else if (e.getKeyCode() == button.getRight_key()) {
			if (usingEffect)
				new Music("Right.mp3", false).start(); // millions
			controller.moveRight();
			controllerGhost.moveRight();
		} else if (e.getKeyCode() == button.getDown_key()) {
			if (usingEffect)
				new Music("Down.mp3", false).start(); // millions
			controller.moveDown();
		} else if (e.getKeyCode() == button.getUp_key()) {
			if (usingEffect)
				new Music("Rotation.mp3", false).start(); // millions
			controller.nextRotationLeft(); //  clockwise
			controllerGhost.nextRotationLeft();
		}else if (e.getKeyCode() == button.getZ_key()) {
			if (usingEffect)
				new Music("Rotation.mp3", false).start();
			controller.nextRotationRight();
			controllerGhost.nextRotationRight();
		}else if (e.getKeyCode() == button.getSpace_key()) {
			controller.moveQuickDown(shap.getPosY(), true);
			this.fixingTetrisBlock();

			if (usingEffect)
				new Music("Space.mp3", false).start(); // millions

		} else if (e.getKeyCode() == button.getShift_key()) {
			playBlockHold();
		}

		if (this.client != null) {
			this.getClient().drawBlockShap(controller.getBlock());// HK
			this.getClient().drawBlockDeposit(blockList);// HK
		}
		this.showGhost();
		this.repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		this.requestFocus();
	}

	public void mouseReleased(MouseEvent e) {
	}

	// 게임 뮤직 키고 끄기 millions
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {

			if (client != null) {
				client.gameStart((int) comboSpeed.getSelectedItem());
			} else {
				this.gameStart((int) comboSpeed.getSelectedItem());
			}
		} else if (e.getSource() == btnExit) {

			if (GameMusic != null && GameMusic.isAlive()) {
				GameMusic.close();
			}

			if (client != null) {
				if (tetris.isNetwork()) {
					client.closeNetwork(tetris.isServer());
				}
			} else {
				System.exit(0);
			}

		} else if (e.getSource() == btnSound1) {
			FixedSound = 1;
			SoundNumber = 1;
			if (isPlay == true  && checkBGM.isSelected() == true) {
				if (GameMusic.isAlive() && GameMusic != null) {
					GameMusic.close();
					GameMusic = new Music("GameMusic1.mp3", true);
					GameMusic.start();
				} else {
					GameMusic = new Music("GameMusic1.mp3", true);
					GameMusic.start();
				}
			}
		}

		else if (e.getSource() == btnSound2 ) {
			FixedSound = 1;
			SoundNumber = 2;
			if (isPlay == true  && checkBGM.isSelected() == true) {
				if (GameMusic.isAlive() && GameMusic != null) {
					GameMusic.close();
					GameMusic = new Music("GameMusic2.mp3", true);
					GameMusic.start();
				} else {
					GameMusic = new Music("GameMusic2.mp3", true);
					GameMusic.start();
				}
			}

		} else if (e.getSource() == btnSound3) {
			FixedSound = 1;
			SoundNumber = 3;
			if (isPlay == true  && checkBGM.isSelected() == true) {
				if (GameMusic.isAlive() && GameMusic != null) {
					GameMusic.close();
					GameMusic = new Music("GameMusic3.mp3", true);
					GameMusic.start();
				} else {
					GameMusic = new Music("GameMusic3.mp3", true);
					GameMusic.start();
				}
			}
		}
	}

	public void setDeposit(ArrayList<Block> blockList2) {
		this.blockList2 = blockList2;
	}

	public void setShap(TetrisBlock shap) {
		this.shap2 = shap;
	}
	
	public void setEnemyScore(int EnemyScore) {
		this.EnemyScore = EnemyScore;
	}

	public boolean isPlay() {
		return isPlay;
	}

	public void setPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public JButton getBtnExit() {
		return btnExit;
	}

	public JButton getBtnSound1() {
		return btnSound1;
	}

	public JButton getBtnSound2() {
		return btnSound2;
	}

	public JButton getBtnSound3() {
		return btnSound3;
	}

	public void setClient(GameClient client) {
		this.client = client;
	}

	public void printSystemMessage(String msg) {
		systemMsg.printMessage(msg);
	}

	public void printMessage(String msg) {
		messageArea.printMessage(msg);
	}

	public GameClient getClient() {
		return client;
	}

	public void changeSpeed(Integer speed) {
		comboSpeed.setSelectedItem(speed);
	}

	public void clearMessage() {
		messageArea.clearMessage();
		systemMsg.clearMessage();
	}
	public static void stopwatch(int onOff) {
		if(onOff == 1) // 타이머 켜기
			oldTime = (int) System.currentTimeMillis() / 1000;
		if(onOff == 0) // 타이머 끄기
			secToMMSS(  ((int) System.currentTimeMillis() / 1000) - oldTime  );
	}
	public static int secToMMSS(int secs) {
	    int hour, min, sec;

	    sec  = secs % 60;
	    min  = secs / 60 % 60;
	    // hour = secs / 3600;

	    timerBuffer = String.format("%02d:%02d", min, sec);
	    return sec;
	}
	/*public static void pause() {
	    try {
	      System.in.read();
	    } catch (IOException e) { }
	}*/
	
	
	public void getResult(String userId, int GameMode, int Score) {
		Call<GameResultRepo> call = retrofitApi.add_point(userId, GameMode, Score);
		call.enqueue(new Callback<GameResultRepo>() {
			
			@Override
			public void onResponse(Call<GameResultRepo> arg0, Response<GameResultRepo> response) {
				if(!response.isSuccessful()) {  
					System.out.println(getClass().getName() + " / " + response.message());
					return;
				}
				
				
				// 서버로 부터 온 데이터들이 gameResultRepo에 담김
				GameResultRepo gameResultRepo = response.body(); //repo 가져오는 건 모드 스트링임
				System.out.println(gameResultRepo.toString());
							
				// 게임 결과창을 띄어줌
				new GameResultInfoWindow(myScore, Integer.valueOf(gameResultRepo.getMode()), Integer.valueOf(gameResultRepo.getRanking()), gameResultRepo.getInfo());
				
			}
			
			@Override
			public void onFailure(Call<GameResultRepo> arg0, Throwable e) {
				System.out.println(getClass().getName()+ " / " + e.getMessage());
			}
		});
	}

}
