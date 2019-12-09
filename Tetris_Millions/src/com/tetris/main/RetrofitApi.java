package com.tetris.main;

import retrofit2.*;
import retrofit2.http.*;

/*
 *  설명 추가 해야함 
 *  누가 만들었는 지
 *  날짜 도 추가해야함
 * 
 */
public interface RetrofitApi {
	
	
	/**
	 * 로그인 요청
	 * @param user_id 사용자 아이디
	 * @param user_pwd 사용자 비밀번호
	 * @return
	 */
	@FormUrlEncoded
	@POST("php-react/login-user.php")
	Call<Response> login_call(
			@Field("id") String user_id,
			@Field("pwd") String user_pwd
	);
	
	
	/**
	 *  아이디 체크
	 *  
	 */
	@GET("php-react/check-id.php")
	Call<Response> check_id(
			@Query("id") String user_id
	);
	
	
	/**
	 * 회원가입 요청
	 * @param user_id 사용자 아이디
	 * @param user_pwd 사용자 비밀번호
	 * @return
	 */
	@FormUrlEncoded
	@POST("php-react/add-user.php")
	Call<Response> regi_call(
		@Field("id") String user_id,
		@Field("pwd") String user_pwd
	);
	
	
	/**
	 * 점수 등록
	 * 게임의 종류는 일반모드(점수), 맵모드(시간), 타임어택(점수)
	 * @param user_id 사용자 아이디
	 * @param mode_ 게임 모드 1 : 일반모드 / 2: 타임어택 / 3. 맵모드
	 * @param score_ 스코어 점수
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
