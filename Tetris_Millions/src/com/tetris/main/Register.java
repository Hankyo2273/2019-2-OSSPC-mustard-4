package com.tetris.main;

import java.awt.*; // GUI 클래스 임포트 awt

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*; // GUI 클래스 임포트 swing

import com.tetris.main.Response;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.*;


public class Register implements ActionListener{
	JFrame frame; 
	
	/**
	 * "테트리스 회원가입" 이라는 제목의 Frame title 추가
	 * 패널 
	 *	
	 */
	
	//Creating objects
    JLabel nameLabel=new JLabel("아이디");
    JLabel passwordLabel=new JLabel("비밀번호");
    JLabel confirmPasswordLabel=new JLabel("비밀번호 확인");
    
    JTextField nameTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JPasswordField confirmPasswordField=new JPasswordField();
    JButton registerButton=new JButton("회원가입");
    JButton resetButton=new JButton("RESET");
    JButton checkButton=new JButton("아이디 중복확인");
    
    // 아이디 체크 유무
    Boolean isCheck;
    
    
    
    // 레트로핏
    RetrofitApi retrofitApi;
	
	
	public Register() {
		isCheck = false;
		createRegisterWindowForm();
		addComponentsToFrame();
		setLocationAndSize();
		actionEvent();
		
		
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://" + IPClass.ServerIp +"/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		
		retrofitApi = retrofit.create(RetrofitApi.class);
				
	}
	
	
	public void createRegisterWindowForm() 
	{
		//Setting properties of JFrame
        frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setTitle("테트리스 회원가입");
        frame.setBounds(40,40,500, 300);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true); // 보임
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // close 닫기  창만
        frame.setResizable(true); // 사이즈 조절 못함
        frame.setLocationRelativeTo(null);
        
	}
	
	
	public void addComponentsToFrame()
    {
        //Adding components to Frame
        frame.add(nameLabel);
        frame.add(passwordLabel);
        frame.add(confirmPasswordLabel);
        frame.add(nameTextField);
        frame.add(passwordField);
        frame.add(confirmPasswordField);
        frame.add(registerButton);
        frame.add(resetButton);
        frame.add(checkButton);
    }	
	
	public void setLocationAndSize()
    {
        //Setting Location and Size of Each Component
        nameLabel.setBounds(20,20,40,70);
        nameTextField.setBounds(180,43,165,23); // 180 에 폭 165
        checkButton.setBounds(345, 43 , 130, 23);
        
        
        
        passwordLabel.setBounds(20,70,100,70);
        passwordField.setBounds(180,93,165,23);
        
        
        
        confirmPasswordLabel.setBounds(20,120,140,70);
        confirmPasswordField.setBounds(180,143,165,23);
        
        
        registerButton.setBounds(70,200,100,35);
        resetButton.setBounds(220,200,100,35);
    }
	
	public void actionEvent()
	{
		registerButton.addActionListener(this);
		resetButton.addActionListener(this);
		checkButton.addActionListener(this);
	}
	





	@Override
	public void actionPerformed(ActionEvent e) {
	
		// 1. 회원가입버튼 
		if(e.getSource()==registerButton) 
		{
			// 아이디가 입력되지 않았을 경우
			if(String.valueOf(nameTextField.getText()).equalsIgnoreCase("")) {
				
			
				JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
			}
			// 비밀번호 칸이 비어졌다면
			else if(String.valueOf(passwordField.getPassword()).equalsIgnoreCase("") || String.valueOf(confirmPasswordField.getPassword()).equalsIgnoreCase(""))
			{
				if(String.valueOf(passwordField.getPassword()).equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요");
				}else if(String.valueOf(confirmPasswordField.getPassword()).equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "한번 더 비밀번호를 입력해주세요");
				}
			}
			// 비밀번호와 비밀번호 확인이 다르다면
			else if(!String.valueOf(passwordField.getPassword()).equalsIgnoreCase(String.valueOf(confirmPasswordField.getPassword()))) {
				JOptionPane.showMessageDialog(null, "비밀번호가 서로 다릅니다. 다시 입력해주세요");
			}
			else{
				
				if(isCheck) { // 아이디 체크 완료 했을 경우에만 실행
					addUser(nameTextField.getText().toString(), String.valueOf(passwordField.getPassword()));
					return;
				}
				
				JOptionPane.showMessageDialog(null, "아이디 중복확인 필수");
			}
			
			
			// 2. 아이디 중복확인
		}else if(e.getSource() == checkButton) {
			/**
			 *  서버와 통신
			 *  
			 */
			if(nameTextField.getText().toString().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요");
				return;
			}
			
			
			CheckId(nameTextField.getText().toString());
		}
	}
	
	/**
	 * 아이디 체크 
	 * @param user_id 입력한 아이디
	 */
	public void CheckId(String user_id) {
		Call<Response> call = retrofitApi.check_id(user_id);
		call.enqueue(new Callback<Response>() {
			
			@Override
			public void onResponse(Call<Response> arg0, retrofit2.Response<Response> response) {
				if(!response.isSuccessful()) { // 접속 실패
					System.out.println("회원가입 : " + response.body());
					return;
				}
				
				
				
				Response response2 = response.body();
				// 해당 아이디를 사용할 수 있는 경우
				if(response2.getSuccess().equalsIgnoreCase("ok")) {
					JOptionPane.showMessageDialog(null, "사용가능한 아이디 입니다");
					isCheck = true; // 아이디 체크 확인
				}else {// 해당 아이디를 사용할 수 없는 경우
					JOptionPane.showMessageDialog(null, "해당 아이디는 사용하실 수 없습니다");
				}
			}
			
			@Override
			public void onFailure(Call<Response> arg0, Throwable response) {
				System.out.println("회원가입 : " + response.getMessage());
			}
		});
	}

	
	
	/**
	 * 회원가입
	 * @param user_id 입력한 아이디
	 * @param user_pwd 입력한 비밀번호
	 */
	public void addUser(String user_id, String user_pwd) {
		Call<Response> call = retrofitApi.regi_call(user_id, user_pwd);
		call.enqueue(new Callback<Response>() {
			
			@Override
			public void onResponse(Call<Response> arg0, retrofit2.Response<Response> response) {
				if(!response.isSuccessful()) {
					System.out.println("회원가입 : " + response.body());
					return;
				}
				
				
				Response response3 = response.body();
				if(response3.getSuccess().equalsIgnoreCase("ok")) { // 
					JOptionPane.showMessageDialog(null, "회원가입이 정상적으로 완료되었습니다. 가입한 아이디로 로그인 해주세요");
				}else { // 회원가입 안되었을 경우
					
				}
				
			}
			
			@Override
			public void onFailure(Call<Response> arg0, Throwable response) {
				System.out.println("회원가입 실패 : " + response.getMessage());
			}
		});
				
	}
	
}
