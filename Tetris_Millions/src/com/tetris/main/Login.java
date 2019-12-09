package com.tetris.main;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import com.tetris.main.Response;
import com.tetris.window.GameResultInfoWindow;
import com.tetris.window.Tetris;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends JFrame implements ActionListener{
	
	    JLabel lbl,la1,la2,la3;
	    JTextField id;
	    JPasswordField passwd;
	    JPanel idPanel,paPanel,loginPanel;
	    JButton b1,b2;
	    
	    RetrofitApi retrofitApi;
	    
	    
	    
	 
	    public Login()
	    {
	          setTitle("로그인");
	          // FlowLayout사용
	          setLayout( new FlowLayout() );
	          // Border로 영역 생성
	          EtchedBorder eborder =  new EtchedBorder();
	          // 레이블 생성     
	          lbl = new JLabel( "아이디와 패스워드를 입력해 주세요" );
	          // 레이블에 영역 만들기
	          lbl.setBorder(eborder);
	          // 레이블 추가
	          add( lbl );
	          // id패널과 pw 패널생성
	          idPanel = new JPanel();
	          paPanel = new JPanel();
	          la3 = new JLabel("아이디");
	          la2 = new JLabel("패스워드");
	          // id텍스트필드와 pw텍스트 필드 선언
	          id = new JTextField(10);
	          passwd = new JPasswordField(10);
	          idPanel.add(la3);
	          idPanel.add(id);
	          paPanel.add( la2 );
	          paPanel.add( passwd );
	          // 로그인과 회원가입을 위한 패널 생성
	          loginPanel = new JPanel();
	          b1 = new JButton("로그인");
	          b2 = new JButton("회원가입");
	          loginPanel.add( b1 );
	          loginPanel.add( b2 );
	          
	          
	          add(idPanel);
	          add(paPanel);
	          add(loginPanel);
	          // 3행 20열 영역의 텍)스트에어리어 
	          setSize( 250, 200)  ;
	          setVisible(true);
	          
	          setLocationRelativeTo(null);
	          setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // min
	          
	          
	          
	          /**
	           *  이벤트 리스너 등록(로그인, 회원가입 버튼)
	           */
	          
	          b1.addActionListener(this);
	          b2.addActionListener(this);
	          
	          
	          
	          Retrofit retrofit = new Retrofit.Builder()
	        		  .baseUrl("http://" + IPClass.ServerIp + "/")
	        		  .addConverterFactory(GsonConverterFactory.create())
	        		  .build();
	          
	          retrofitApi = retrofit.create(RetrofitApi.class);
	          
	          
	          
	    }


	    
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == b2)// 회원가입 버튼 누름
			{
				new Register();
			}
			else { // 로그인 버튼 누름
				/**
				 *  예외처리
				 *  아이디를 입력하지 않았을 경우
				 *
				 */
				
				// 아이디가 아무것도 입력하지 않았을 경우
				if(String.valueOf(id.getText()).equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null,"아이디를 입력하세요");
					return;
					
				// 비밀번호 입력하지 않았을 경우
				}else if(String.valueOf(passwd.getPassword()).equalsIgnoreCase(""))
				{
					// 비밀번호 입력하라는 다이얼로그 띄우고
					JOptionPane.showMessageDialog(null,"비밀번호를 입력하세요");
					return;
					
				// 만약 모든 값을 입력했을 경우 서버와 연동을 통해 로그인 과정 진행
				}else {
					// 만든 RESTApi endpoint 로 해당 아이디와 비밀번호를 보냄
					LoginCheck(String.valueOf(id.getText()), String.valueOf(passwd.getPassword()));
					
				}
			}
			
			
			
	
		}
		
		
		
		/**
		 *  통신 테스트
		 */
		public void LoginCheck(String id, String pwd) {
			Call<Response> call = retrofitApi.login_call(id, pwd);
			call.enqueue(new Callback<Response>() {
				
				@Override
				public void onResponse(Call<Response> arg0, retrofit2.Response<Response> response) {
					// TODO Auto-generated method stub
					if(!response.isSuccessful()) { // 접속 실패
						System.out.println("접속 실패 : " + response.body());
					}
					
					
					System.out.println("접속 성공 : " + response.body());
					System.out.println("접속 성공 : " + response.code());
					Response response2 = response.body();
					if(response2.getSuccess().equalsIgnoreCase("ok")) { 
						JOptionPane.showMessageDialog(null,"로그인성공");
						TetrisMain.userId = id;// 로그인 성공시 전역변수로 아이디를 저장함
						TetrisMain.isLogin = true; // 로그인 유무 저장
						dispose();// 로그인 창 끔
						new ModeSelectionWindow(); // 모드 선택창 띄움
					}else {
						 
						JOptionPane.showMessageDialog(null,"존재하지 않는 회원이거나 잘못된 아이디와 비밀번호를 입력하였습니다.");
							
					}
				}
				
				@Override
				public void onFailure(Call<Response> arg0, Throwable response) {
					System.out.println("접속 실채? : " + response.getMessage());
				}
			});
		}
	    
	    
}