package com.group1.Dao;

import com.group1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByNameAndPwd(String name, String pwd);

    User findByName(String name);

    User findByEmail(String email);

    User findByPhone(String phone);

    User findById(int id);
}
