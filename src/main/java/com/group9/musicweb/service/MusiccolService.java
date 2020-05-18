package com.group9.musicweb.service;

import com.group9.musicweb.Dao.MusiccolRepository;
import com.group9.musicweb.entity.Musiccol;
import com.group9.musicweb.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusiccolService {
    @Autowired
    private MusiccolRepository musiccolRepository;

    public TableData getallMusiccol(String name, Integer pageSize, Integer pageNumber) {
        List<Musiccol> newsList;
        if (name != null && name != "") {
            newsList = musiccolRepository.findMusiccolByusername(name, pageNumber, pageSize);
        } else {
            newsList = musiccolRepository.findMusiccolByusername(pageNumber, pageSize);
        }
        long total = musiccolRepository.queryCount(name);
        TableData data = new TableData(pageNumber, total, newsList);
        return data;
    }

    /* 获取所有评论，以表格形式返回 */
}
