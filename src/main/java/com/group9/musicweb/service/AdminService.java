package com.group9.musicweb.service;

import com.group9.musicweb.Dao.AdminRepository;
import com.group9.musicweb.entity.Admin;
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
