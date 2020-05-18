package com.group9.musicweb.Dao;

import com.group9.musicweb.entity.Userlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserlogRepository extends JpaRepository<Userlog, Integer> {
    @Query(value = "select * from userlog where user_id = ?1 order by add_time desc", nativeQuery = true)
    List<Userlog> QueryUserlog(int userid);


    @Query(value = "select * from userlog inner join user on user_id = user.id where user.name like concat('%',:name1,'%') limit :pageNumber,:pageSize", nativeQuery = true)
    List<Userlog> QueryallUserlog(String name1, Integer pageNumber, Integer pageSize);

    @Query(value = "select * from userlog limit :pageNumber,:pageSize ", nativeQuery = true)
    List<Userlog> QueryallUserlog(Integer pageNumber, Integer pageSize);

    @Query(value = "select count(*) from userlog inner join user on user_id = user.id where user.name like concat('%',:name1,'%')", nativeQuery = true)
    long queryCount(String name1);

}