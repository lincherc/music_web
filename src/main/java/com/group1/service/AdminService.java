package com.group1.service;

import com.group1.Dao.AdminRepository;
import com.group1.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin checkAdmin(String nickname, String pwd) {
        return adminRepository.findByNicknameAndPwd(nickname, pwd);
    }
}
