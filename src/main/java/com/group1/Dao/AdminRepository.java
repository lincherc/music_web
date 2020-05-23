package com.group1.Dao;

import com.group1.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByNicknameAndPwd(String nickname, String pwd);

}
