package com.group1.service;

import com.group1.Dao.UserlogRepository;
import com.group1.entity.Userlog;
import com.group1.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserlogService {
    @Autowired
    private UserlogRepository userlogRepository;

    public List<Userlog> queryUserlog(int userid) {
        return userlogRepository.QueryUserlog(userid);
    }

    public TableData getallUserlog(String name, Integer pageSize, Integer pageNumber) {
        List<Userlog> newsList;
        if (name != null && name != "") {
            newsList = userlogRepository.QueryallUserlog(name, pageNumber, pageSize);
        } else {
            newsList = userlogRepository.QueryallUserlog(pageNumber, pageSize);
        }
        long total = userlogRepository.queryCount(name);
        TableData data = new TableData(pageNumber, total, newsList);
        return data;
    }

    public void saveUserlog(Userlog userlog) {
        userlogRepository.save(userlog);
    }
}
