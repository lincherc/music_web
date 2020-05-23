package com.group1.Dao;

import com.group1.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findTagById(Integer id);

    List<Tag> findAll();

}
