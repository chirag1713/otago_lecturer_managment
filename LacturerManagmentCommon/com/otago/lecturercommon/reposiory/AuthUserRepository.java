package com.otago.lecturercommon.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otago.lecturercommon.entity.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    @Query(nativeQuery = true, value = "SELECT PASSWORD(:password)")
    public String getUserPassword(@Param("password") String password);

    public AuthUser findByUserIdAndPassword(int userId, String password);

    @Query("SELECT u FROM AuthUser u WHERE u.userId = :userid AND u.password = PASSWORD(:password)")
    public AuthUser getUserByUserIdAndPassword(@Param("userid") int userId, @Param("password") String password);

    public AuthUser findByUserId(int userId);
}
