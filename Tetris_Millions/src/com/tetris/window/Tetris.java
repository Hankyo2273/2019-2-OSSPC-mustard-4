package com.tetris.window;

import static com.tetris.window.Sound.GameMusic;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.tetris.network.GameClient;
import com.tetris.network.GameServer;

public class Tetris extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private GameServer server;
	private GameClient client;
	
	
	private TetrisBoard board = new TetrisBoard(this,client); // 게임 보드 시작
	
	private JMenuItem itemServerStart = new JMenuItem("서버로 접속하기");
	private JMenuItem itemClientStart = new JMenuItem("클라이언트로 접속하기");
	private JMenuItem itemKeySet = new JMenuItem("설정하기");	//"키 조작" 하위 menu인 "설정하기" 삽입.(millions)
	
	private boolean isNetwork;
	private boolean isServer;

	

	public Tetris() {
		JMenuBar mnBar = new JMenuBar(); 
		JMenu mnGame = new JMenu("게임하기");
		JMenu stGame = new JMenu("키 조작");	 //상단에 "키 조작" menu 삽입. (millions)
		
		
		mnGame.add(itemServerStart); 
		mnGame.add(itemClientStart);
		stGame.add(itemKeySet);				//millions
		
//		mnBar.add(mnGame); min 
//		mnBar.add(stGame); min					//millions
		
		
		this.setJMenuBar(mnBar); // 메뉴세팅
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().add(board);
		
		this.setResizable(false);
		this.pack();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((size.width-this.getWidth())/2,(size.height-this.getHeight())/2);
		this.setVisible(true);
		
		itemServerStart.addActionListener(this);
		itemClientStart.addActionListener(this);
		
		itemKeySet.addActionListener(this);				//millions
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if(client!=null ){
					
					if(isNetwork){
						client.closeNetwork(isServer);
					}
				}else{
					System.exit(0);
				}
				
			}
			
		});
		
		start();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		String ip=null;
		int port=0;
		String nickName=null;
		
		// 1. 서버로 시작하기 버튼을 눌렀을 경우
		if(e.getSource() == itemServerStart){  
			
			// 포트번호를 입력받는 다이얼로그를 띄우고 
			String sp = JOptionPane.showInputDialog("port번호를 입력해주세요","9500");
			
			// 포트번호를 입력받는다. 
			if(sp!=null && !sp.equals(""))port = Integer.parseInt(sp);  
			
			// 닉네임을 입력받는 다이얼로그도 띄우고 닉네임을 입력받는다.
			// 닉네임을 입력하지 않으면 null 이다.
			nickName = JOptionPane.showInputDialog("닉네임을 입력해주세요","이름없음");
			
			
			
			// 포트번호를 입력받게 되면
			if(port!=0){ // 포트에 입력값이 들어왔고 ( 사실 이렇게 예외처리 하면 안됨...)
				/**
				 *  게임 서버를 만들지 않았다면 아까 입력 받았던  포트번호를 넘겨주어 게임서버를 만든다.
				 *  startServer()메소드로 서버를 실행시킨다.
				 *  만든 server에서는 넘겨준 포트번호로 서버소켓을 만들고 스레드로 서버를 동작시킨다.
				 */
				if(server == null) server = new GameServer(port);
				server.startServer();
				
				
				try {
					
					// 현재 접속 local_ip 이걸 바꾸? 
					ip = InetAddress.getLocalHost().getHostAddress();
//					ip = ;
//					ip = "10.80.19.19";
				} 
				catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				if(ip!=null){// 현재 컴퓨터의 local_ip 정보를 받으면 , 사실상 무조건 받기 떄문에 무조건 실행됨
					
					// 클라이언트 객체 생성 및 소켓만듬
					client = new GameClient(this,ip,port,nickName);
					
					if(client.start()){ // 클라이언트 소켓 생성과 I/O 처리까지 완료되었고 서버소켓과 연결도 잘 되었을 경우
						
						
						// 보드에 표시하는 부분
						
						itemServerStart.setEnabled(false); // 예외처리 (서버로 접속하기 버튼 비활성화)
						itemClientStart.setEnabled(false); // 예외처리 (클라이언트로 접속하기 버튼 비활성화)
						
						board.setClient(client);
						board.getBtnStart().setEnabled(true);
						board.startNetworking(ip, port, nickName);
						
						
						isNetwork = true;
						isServer = true;
						
					}
					
				}
				
			}
			
			
		}else if(e.getSource() == itemClientStart){
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			
			ip = JOptionPane.showInputDialog("IP를 입력해주세요.",ip);
			String sp = JOptionPane.showInputDialog("port번호를 입력해주세요","9500");
			if(sp!=null && !sp.equals(""))port = Integer.parseInt(sp);
			nickName = JOptionPane.showInputDialog("닉네임을 입력해주세요","이름없음");

		
			if(ip!=null){														// "설정하기"를 누르면 실행되는 program(millions)
				client = new GameClient(this,ip,port,nickName);
				if(client.start()){
					itemServerStart.setEnabled(false);
					itemClientStart.setEnabled(false);
					board.setClient(client);
					board.startNetworking(ip, port, nickName);
					isNetwork = true;
				}
			}		}else if(e.getSource() == itemKeySet) {
				  Button start = new Button();
				  start.FrameShow();
		}
	}

	public void closeNetwork(){
		if(GameMusic != null && GameMusic.isAlive()) {
			GameMusic.close();
		isNetwork = false;
		client = null;
		itemServerStart.setEnabled(true);
		itemClientStart.setEnabled(true);
		board.setPlay(false);
		board.setClient(null);
		}else {
			isNetwork = false;
			client = null;
			itemServerStart.setEnabled(true);
			itemClientStart.setEnabled(true);
			board.setPlay(false);
			board.setClient(null);
		}
	}

	public JMenuItem getItemServerStart() {return itemServerStart;}
	public JMenuItem getItemClientStart() {return itemClientStart;}
	public TetrisBoard getBoard(){return board;}
	public void gameStart(int speed){board.gameStart(speed);}
	public boolean isNetwork() {return isNetwork;}
	public void setNetwork(boolean isNetwork) {this.isNetwork = isNetwork;}
	public void printSystemMessage(String msg){board.printSystemMessage(msg);}
	public void printMessage(String msg){board.printMessage(msg);}
	public boolean isServer() {return isServer;}
	public void setServer(boolean isServer) {this.isServer = isServer;}
	public void changeSpeed(Integer speed) {board.changeSpeed(speed);}
	public void start() {
		String ip=null;
		int port=0;
		String nickName=null;
		
		// 1. 서버로 시작하기 버튼을 눌렀을 경우
			
			// 포트번호를 입력받는 다이얼로그를 띄우고 
			String sp = "9500";
			
			// 포트번호를 입력받는다. 
			if(sp!=null && !sp.equals(""))port = Integer.parseInt(sp);  
			
			// 닉네임을 입력받는 다이얼로그도 띄우고 닉네임을 입력받는다.
			// 닉네임을 입력하지 않으면 null 이다.
			
			
			
			// 포트번호를 입력받게 되면
			if(port!=0){ // 포트에 입력값이 들어왔고 ( 사실 이렇게 예외처리 하면 안됨...)
				/**
				 *  게임 서버를 만들지 않았다면 아까 입력 받았던  포트번호를 넘겨주어 게임서버를 만든다.
				 *  startServer()메소드로 서버를 실행시킨다.
				 *  만든 server에서는 넘겨준 포트번호로 서버소켓을 만들고 스레드로 서버를 동작시킨다.
				 */
				if(server == null) server = new GameServer(port);
				server.startServer();
				
				
				try {
					
					// 현재 접속 local_ip 이걸 바꾸? 
					ip = InetAddress.getLocalHost().getHostAddress();
//					ip = ;
//					ip = "10.80.19.19";
				} 
				catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				if(ip!=null){// 현재 컴퓨터의 local_ip 정보를 받으면 , 사실상 무조건 받기 떄문에 무조건 실행됨
					
					// 클라이언트 객체 생성 및 소켓만듬
					client = new GameClient(this,ip,port,nickName);
					
					if(client.start()){ // 클라이언트 소켓 생성과 I/O 처리까지 완료되었고 서버소켓과 연결도 잘 되었을 경우
						
						
						// 보드에 표시하는 부분
						
						itemServerStart.setEnabled(false); // 예외처리 (서버로 접속하기 버튼 비활성화)
						itemClientStart.setEnabled(false); // 예외처리 (클라이언트로 접속하기 버튼 비활성화)
						
						board.setClient(client);
						board.getBtnStart().setEnabled(true);
						board.startNetworking(ip, port, nickName);
						
						
						isNetwork = true;
						isServer = true;
						
					}
					
				}
				
			}
			
			
		
	}
}
