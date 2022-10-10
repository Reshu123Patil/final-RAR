package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.Role;
import com.app.pojos.User;

public interface UserRepository extends JpaRepository<User,Integer>
{
    @Query("select u from User u where email=:em and password=:pwd")
	Optional<User> findByEmailAndPassword(@Param("em") String email, @Param("pwd") String password);
    
    @Query("select u from User u where u_id=:uid")
	Optional<User> findById(@Param("uid") int u_id);
    
    @Query(value="select * from users where role like %:ADMIN%",nativeQuery = true)
    List<User> findByRole(@Param("ADMIN") Role ADMIN);

	Optional<User> findByEmail(String email);
}