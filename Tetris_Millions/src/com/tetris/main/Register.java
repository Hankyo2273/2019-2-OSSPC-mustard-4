package com.tetris.main;

import java.awt.*; // GUI Ŭ���� ����Ʈ awt

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*; // GUI Ŭ���� ����Ʈ swing

import com.tetris.main.Response;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.*;


public class Register implements ActionListener{
	JFrame frame; 
	
	/**
	 * "��Ʈ���� ȸ������" �̶�� ������ Frame title �߰�
	 * �г� 
	 *	
	 */
	
	//Creating objects
    JLabel nameLabel=new JLabel("���̵�");
    JLabel passwordLabel=new JLabel("��й�ȣ");
    JLabel confirmPasswordLabel=new JLabel("��й�ȣ Ȯ��");
    
    JTextField nameTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JPasswordField confirmPasswordField=new JPasswordField();
    JButton registerButton=new JButton("ȸ������");
    JButton resetButton=new JButton("RESET");
    JButton checkButton=new JButton("���̵� �ߺ�Ȯ��");
    
    // ���̵� üũ ����
    Boolean isCheck;
    
    
    
    // ��Ʈ����
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
        frame.setTitle("��Ʈ���� ȸ������");
        frame.setBounds(40,40,500, 300);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true); // ����
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // close �ݱ�  â��
        frame.setResizable(true); // ������ ���� ����
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
        nameTextField.setBounds(180,43,165,23); // 180 �� �� 165
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
	
		// 1. ȸ�����Թ�ư 
		if(e.getSource()==registerButton) 
		{
			// ���̵� �Էµ��� �ʾ��� ���
			if(String.valueOf(nameTextField.getText()).equalsIgnoreCase("")) {
				
			
				JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���");
			}
			// ��й�ȣ ĭ�� ������ٸ�
			else if(String.valueOf(passwordField.getPassword()).equalsIgnoreCase("") || String.valueOf(confirmPasswordField.getPassword()).equalsIgnoreCase(""))
			{
				if(String.valueOf(passwordField.getPassword()).equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���");
				}else if(String.valueOf(confirmPasswordField.getPassword()).equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "�ѹ� �� ��й�ȣ�� �Է����ּ���");
				}
			}
			// ��й�ȣ�� ��й�ȣ Ȯ���� �ٸ��ٸ�
			else if(!String.valueOf(passwordField.getPassword()).equalsIgnoreCase(String.valueOf(confirmPasswordField.getPassword()))) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� �ٸ��ϴ�. �ٽ� �Է����ּ���");
			}
			else{
				
				if(isCheck) { // ���̵� üũ �Ϸ� ���� ��쿡�� ����
					addUser(nameTextField.getText().toString(), String.valueOf(passwordField.getPassword()));
					return;
				}
				
				JOptionPane.showMessageDialog(null, "���̵� �ߺ�Ȯ�� �ʼ�");
			}
			
			
			// 2. ���̵� �ߺ�Ȯ��
		}else if(e.getSource() == checkButton) {
			/**
			 *  ������ ���
			 *  
			 */
			if(nameTextField.getText().toString().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(null, "���̵� �Է��ϼ���");
				return;
			}
			
			
			CheckId(nameTextField.getText().toString());
		}
	}
	
	/**
	 * ���̵� üũ 
	 * @param user_id �Է��� ���̵�
	 */
	public void CheckId(String user_id) {
		Call<Response> call = retrofitApi.check_id(user_id);
		call.enqueue(new Callback<Response>() {
			
			@Override
			public void onResponse(Call<Response> arg0, retrofit2.Response<Response> response) {
				if(!response.isSuccessful()) { // ���� ����
					System.out.println("ȸ������ : " + response.body());
					return;
				}
				
				
				
				Response response2 = response.body();
				// �ش� ���̵� ����� �� �ִ� ���
				if(response2.getSuccess().equalsIgnoreCase("ok")) {
					JOptionPane.showMessageDialog(null, "��밡���� ���̵� �Դϴ�");
					isCheck = true; // ���̵� üũ Ȯ��
				}else {// �ش� ���̵� ����� �� ���� ���
					JOptionPane.showMessageDialog(null, "�ش� ���̵�� ����Ͻ� �� �����ϴ�");
				}
			}
			
			@Override
			public void onFailure(Call<Response> arg0, Throwable response) {
				System.out.println("ȸ������ : " + response.getMessage());
			}
		});
	}

	
	
	/**
	 * ȸ������
	 * @param user_id �Է��� ���̵�
	 * @param user_pwd �Է��� ��й�ȣ
	 */
	public void addUser(String user_id, String user_pwd) {
		Call<Response> call = retrofitApi.regi_call(user_id, user_pwd);
		call.enqueue(new Callback<Response>() {
			
			@Override
			public void onResponse(Call<Response> arg0, retrofit2.Response<Response> response) {
				if(!response.isSuccessful()) {
					System.out.println("ȸ������ : " + response.body());
					return;
				}
				
				
				Response response3 = response.body();
				if(response3.getSuccess().equalsIgnoreCase("ok")) { // 
					JOptionPane.showMessageDialog(null, "ȸ�������� ���������� �Ϸ�Ǿ����ϴ�. ������ ���̵�� �α��� ���ּ���");
				}else { // ȸ������ �ȵǾ��� ���
					
				}
				
			}
			
			@Override
			public void onFailure(Call<Response> arg0, Throwable response) {
				System.out.println("ȸ������ ���� : " + response.getMessage());
			}
		});
				
	}
	
}
