package com.group9.musicweb.Dao;

import com.group9.musicweb.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByNicknameAndPwd(String nickname, String pwd);

}
