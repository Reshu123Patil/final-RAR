package com.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.pojos.Car;
import com.app.pojos.CarStatus;
import java.util.List;

public interface CarRepository extends CrudRepository<Car, Integer>{

   //List<Car> findByIsDeleted(boolean deleted);
    
    //List<Car> findByStatus(CarStatus status);

//    @Query("select b.isDeleted from Car b where c_id=:cid ")
//	 Car findCarById(@Param("cid") int cid);
//    
//    @Modifying
//    @Query("update Car u set u.isDeleted = false where c_id=:cid")
//    void findCarById(@Param("cid") int cid);
	//List<Car> findByIsDeleted(boolean deleted);
    
	   // List<Car> findByStatus(CarStatus status,CarStatus status1);

//	    @Query("select b.isDeleted from Car b where c_id=:cid ")
//		 Car findCarById(@Param("cid") int cid);
	//    
//	    @Modifying
//	    @Query("update Car u set u.isDeleted = false where c_id=:cid")
//	    void findCarById(@Param("cid") int cid);
	    
//	    @Query(value="select c from cars c where status=:AVAILABLE or status=:UNAVAILABLE",nativeQuery=true)
//	    List<Car> findByStatus(@Param("AVAILABLE") CarStatus AVAILABLE,@Param("UNAVAILABLE") CarStatus UNAVAILABLE);

//		List<Car> findByStatus(CarStatus available, CarStatus unavailable);
	    
//	    @Query(value="select CarStatus.* from cars  as CarStatus where CarStatus.status like :AVAILABLE or CarStatus.status like :UNAVAILABLE ",nativeQuery = true)
//	    List<Car> findByStatus(CarStatus AVAILABLE,CarStatus UNAVAILABLE);
		
		@Query(value="select * from cars where status like %:AVAILABLE% or status like %:UNAVAILABLE% ",nativeQuery = true)
	    List<Car> findByStatus(@Param("AVAILABLE") CarStatus available,@Param("UNAVAILABLE") CarStatus unavailable);

		
		//@Query(value="select * from Users u where u.first_name like %:keyword% or u.last_name like %:keyword%", nativeQuery=true)

	
}