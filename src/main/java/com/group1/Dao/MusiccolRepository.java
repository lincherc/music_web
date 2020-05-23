package com.group1.Dao;

import com.group1.entity.Musiccol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusiccolRepository extends JpaRepository<Musiccol, Integer> {


    @Query(value = "select * from musiccol inner join music on music_id = music.id where music.ischeckd = 1 and user_id=?1", nativeQuery = true)
    List<Musiccol> findAllByUser(int uid);


    @Query(value = "select distinct user_id from musiccol ", nativeQuery = true)
    List<Integer> getAllUser();

    @Query(value = "select distinct music_id from musiccol ", nativeQuery = true)
    List<Integer> getAllMusic();

    @Query(value = "select * from musiccol inner join music on music_id = music.id where music.ischeckd = 1 and user_id=?1 and music_id=?2", nativeQuery = true)
    Musiccol findMusiccol(int uid, int mid);

    @Query(value = "select music_id from musiccol where user_id=?1", nativeQuery = true)
    List<Integer> findByUID(int uid);

    @Query(value = "select * from musiccol inner join user on musiccol.user_id=user.id where user.name like concat('%',:name1,'%') limit :pageNumber,:pageSize", nativeQuery = true)
    List<Musiccol> findMusiccolByusername(String name1, Integer pageNumber, Integer pageSize);

    @Query(value = "select * from musiccol limit :pageNumber,:pageSize", nativeQuery = true)
    List<Musiccol> findMusiccolByusername(Integer pageNumber, Integer pageSize);


    @Query(value = "select count(*) from musiccol inner join user on musiccol.user_id=user.id where user.name like concat('%',:name1,'%')", nativeQuery = true)
    long queryCount(String name1);
}
