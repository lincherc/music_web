package com.group1.service;

import com.group1.Dao.CommentRepository;
import com.group1.entity.Comment;
import com.group1.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findCommentsByMusic_Id(int id) {
        return commentRepository.findCommentsByMusic_Id(id);
    }

    public TableData getallComment(String name, Integer pageSize, Integer pageNumber) {
        List<Comment> newsList;
        if (name != null && name != "") {
            newsList = commentRepository.findCommentsByusername(name, pageNumber, pageSize);
        } else {
            newsList = commentRepository.findComments(pageNumber, pageSize);
        }
        long total = commentRepository.queryCount(name);
        TableData data = new TableData(pageNumber, total, newsList);
        return data;
    }
}
