package com.group1.service;

import com.group1.Dao.TagRepository;
import com.group1.entity.Tag;
import com.group1.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> queryAllTag() {
        return tagRepository.findAll();
    }

    public TableData getAllTag(String name, Integer pageSize, Integer pageNumber) {
        List<Tag> newsList = tagRepository.findAll();
        return new TableData(pageNumber, (long) (newsList.size()), newsList);
    }
}

