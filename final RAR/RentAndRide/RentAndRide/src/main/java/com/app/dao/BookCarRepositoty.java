package com.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.pojos.Booking;
import com.app.pojos.Car;

@Repository
public interface BookCarRepositoty  extends JpaRepository<Booking, Integer>{
	
	 @Query("select b from Booking b where uid.id=:u_id")
     List<Booking> findByUserId(@Param("u_id") int userId);
	
	 @Query("select b.carid from Booking b where booking_id=:bid ")
	 Car findCarByBookingId(@Param("bid") int bid);
	 
	 @Modifying
	 @Query("delete from Booking b where booking_id=:bid")
	 void deleteBooking(@Param("bid") int bid);
	 
	 //select count(*) from car_booking where booking_status="Success" and car_id=1 and start_datetime between "2022-10-02 05:29:00" and "2022-10-04 05:30:00";
	 /*
	   select count(*) from car_booking where booking_status="Success" and car_id=3 and start_datetime between "2022-10-04 07:31:00" and "2022-10-05 09:30:00"
		or
		end_datetime between "2022-10-04 07:31:00" and "2022-10-05 09:30:00";
	   */
	 @Query (value = "select count(*) from car_booking  where car_id=:cid and start_datetime between :st and :et or end_datetime between :st and :et",nativeQuery=true )
		int findAvailable(@Param("cid") int c,@Param("st") String strDate1,@Param("et") String strDate2);
}