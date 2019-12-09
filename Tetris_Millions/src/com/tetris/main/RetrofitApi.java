package com.tetris.main;

import retrofit2.*;
import retrofit2.http.*;

/*
 *  ���� �߰� �ؾ��� 
 *  ���� ������� ��
 *  ��¥ �� �߰��ؾ���
 * 
 */
public interface RetrofitApi {
	
	
	/**
	 * �α��� ��û
	 * @param user_id ����� ���̵�
	 * @param user_pwd ����� ��й�ȣ
	 * @return
	 */
	@FormUrlEncoded
	@POST("php-react/login-user.php")
	Call<Response> login_call(
			@Field("id") String user_id,
			@Field("pwd") String user_pwd
	);
	
	
	/**
	 *  ���̵� üũ
	 *  
	 */
	@GET("php-react/check-id.php")
	Call<Response> check_id(
			@Query("id") String user_id
	);
	
	
	/**
	 * ȸ������ ��û
	 * @param user_id ����� ���̵�
	 * @param user_pwd ����� ��й�ȣ
	 * @return
	 */
	@FormUrlEncoded
	@POST("php-react/add-user.php")
	Call<Response> regi_call(
		@Field("id") String user_id,
		@Field("pwd") String user_pwd
	);
	
	
	/**
	 * ���� ���
	 * ������ ������ �Ϲݸ��(����), �ʸ��(�ð�), Ÿ�Ӿ���(����)
	 * @param user_id ����� ���̵�
	 * @param mode_ ���� ��� 1 : �Ϲݸ�� / 2: Ÿ�Ӿ��� / 3. �ʸ��
	 * @param score_ ���ھ� ����
	 * @return
	 */
	@FormUrlEncoded
	@POST("/php-react/add-point.php")
	Call<GameResultRepo> add_point(
		@Field("user_id") String user_id,
		@Field("mode") int mode_,
		@Field("score") int score_,
		@Field("time") String time_
	);
	
	
}
