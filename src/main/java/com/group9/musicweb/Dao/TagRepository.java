package com.group9.musicweb.Dao;

import com.group9.musicweb.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findTagById(Integer id);

    List<Tag> findAll();

}
