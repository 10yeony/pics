package com.devils.pics.service;

import java.util.ArrayList;
import java.util.List;

import com.devils.pics.domain.ExceptionDate;
import com.devils.pics.domain.RepeatDate;
import com.devils.pics.domain.Reservation;
import com.devils.pics.domain.Schedule;
import com.devils.pics.domain.Studio;

public interface StudioReserveService {
	public ArrayList<ExceptionDate> getExceptionDate(Schedule schedule); //예외 일정  
	public ArrayList<RepeatDate> getRepeatDate(int stuId); 	// 영업 시간 및 날짜 주기
	public int AddReservation(Reservation reservation); // 예약하기
	public int AddExceptionDates(ExceptionDate exceptionDate);//예외 일정 추가
	public List<Reservation> getReservation(Schedule schedule); // 예약 중복 체크, 예약 이력 불러오기(스튜디오/고객/스튜디오&고객  기준)
	public int UpdateReservation(Reservation reservation); //예약 내역 업데이트
	public int UpdateExceptionDate(ExceptionDate exceptionDate); //예약 불가 테이블 업데이트
	public int DeleteReservations(int resId); //예약 취소
	public int DeleteExceptionDates(List<ExceptionDate> exceptionDate); //예약 불가 테이블 삭제
	public Schedule getSchedule(Schedule schedule); //스케쥴 전부가져오기
	public List<Reservation> getExpiredReservation(int custId); //지난예약
	public List<Reservation> getWillReservation(int custId);//앞으로 남은 예약
	public List<Reservation> getMonthReservation(Reservation reservation);//지난 예약중 n월의예약
}
