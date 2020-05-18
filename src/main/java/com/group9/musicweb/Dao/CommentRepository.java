package com.group9.musicweb.Dao;


import com.group9.musicweb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByMusic_Id(int id);

    @Query(value = "select * from comment inner join user on comment.user_id=user.id where user.name like concat('%',:name1,'%') limit :pageNumber,:pageSize", nativeQuery = true)
    List<Comment> findCommentsByusername(String name1, Integer pageNumber, Integer pageSize);

    @Query(value = "select * from comment limit :pageNumber,:pageSize", nativeQuery = true)
    List<Comment> findComments(Integer pageNumber, Integer pageSize);


    @Query(value = "select count(*) from comment inner join user on comment.user_id=user.id where user.name like concat('%',:name1,'%')", nativeQuery = true)
    long queryCount(String name1);
}
