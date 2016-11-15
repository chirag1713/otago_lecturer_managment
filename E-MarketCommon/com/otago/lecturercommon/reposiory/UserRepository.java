package com.otago.lecturercommon.reposiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otago.lecturercommon.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByMobile(String mobileNo);

	public User findByEmail(String email);

	@Query(nativeQuery = true, value = "SELECT email "
			+ " FROM User WHERE (email LIKE '%@gmail.com' OR email LIKE '%@googlemail.com') "
			+ " AND REPLACE(SUBSTRING_INDEX (email,'@',1),'.','') = REPLACE(SUBSTRING_INDEX (:email ,'@',1),'.','')")
	public List<String> availaleGmailUser(@Param("email") String email);

}
