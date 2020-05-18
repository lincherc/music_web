package com.group9.musicweb.Dao;

import com.group9.musicweb.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {


    @Query(value = "select * from music where name like CONCAT('%',?1,'%') and ischeckd = 1", nativeQuery = true)
    List<Music> searchLikeMusic(String key);


    List<Music> findAllByZuozhe(String zuozhe);

    Music findMusicById(int id);

    @Query(value = "select * from music where ischeckd = 1", nativeQuery = true)
    List<Music> getALLcheckedmusic();

    Music findById(int id);

    @Query(value = "select * from music a where ischeckd = 0 and a.name like concat('%',:name1,'%') limit :pageNumber,:pageSize", nativeQuery = true)
    List<Music> findUnCheckedMusicList(String name1, Integer pageNumber, Integer pageSize);

    @Query(value = "select * from music a where ischeckd = 0 limit :pageNumber,:pageSize", nativeQuery = true)
    List<Music> findUnCheckedMusicList(Integer pageNumber, Integer pageSize);

    @Query(value = "select count(*) from music a where ischeckd =   0 and a.name like concat('%',:name1,'%')", nativeQuery = true)
    long queryUnCheckedCount(String name1);

    @Query(value = "select * from music a where ischeckd = 1 and a.name like concat('%',:name1,'%') limit :pageNumber,:pageSize", nativeQuery = true)
    List<Music> findCheckedMusicList(String name1, Integer pageNumber, Integer pageSize);

    @Query(value = "select * from music a where ischeckd = 1 limit :pageNumber,:pageSize", nativeQuery = true)
    List<Music> findCheckedMusicList(Integer pageNumber, Integer pageSize);

    @Query(value = "select count(*) from music a where ischeckd = 1 and a.name like concat('%',:name1,'%')", nativeQuery = true)
    long queryCheckedCount(String name1);

    @Query(value = "select * from music where ischeckd = 1 and tag_id=?1", nativeQuery = true)
    List<Music> getALLcheckedmusicByTag_id(int tid);

    @Query(value = "select * from music where ischeckd = 1 order by play_num desc", nativeQuery = true)
    List<Music> getsortedMusicList();
}
