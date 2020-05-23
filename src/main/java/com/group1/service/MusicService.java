package com.group1.service;

import com.group1.Dao.MusicRepository;
import com.group1.entity.Music;
import com.group1.util.ResultRequest;
import com.group1.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    public void saveMusic(Music music) {
        musicRepository.save(music);
    }

    public List<Music> searchMusic(String key) {
        List<Music> l1 = musicRepository.searchLikeMusic(key);
        List<Music> l2 = musicRepository.findAllByZuozhe(key);
        l1.remove(l2);
        l1.addAll(l2);
        return l1;
    }

    public Music findMusicById(int id) {
        return musicRepository.findMusicById(id);
    }

    public List<Music> getallcheckedmusic() {
        return musicRepository.getALLcheckedmusic();
    }


    public TableData getUnCheckedMusic(String name, Integer pageSize, Integer pageNumber) {
        List<Music> newsList;
        if (name != null && name != "") {
            newsList = musicRepository.findUnCheckedMusicList(name, pageNumber, pageSize);
        } else {
            newsList = musicRepository.findUnCheckedMusicList(pageNumber, pageSize);
        }
        long total = musicRepository.queryUnCheckedCount(name);
        TableData data = new TableData(pageNumber, total, newsList);
        return data;
    }

    public TableData getCheckedMusic(String name, Integer pageSize, Integer pageNumber) {
        List<Music> newsList;
        if (name != null && name != "") {
            newsList = musicRepository.findCheckedMusicList(name, pageNumber, pageSize);
        } else {
            newsList = musicRepository.findCheckedMusicList(pageNumber, pageSize);
        }
        long total = musicRepository.queryCheckedCount(name);
        TableData data = new TableData(pageNumber, total, newsList);
        return data;
    }

    public ResultRequest checkedPass(Integer tagId, Integer id, String info) {
        ResultRequest result = new ResultRequest();
        try {
            Music music = musicRepository.findMusicById(id);
            music.setTag_id(tagId);
            music.setIscheckd(true);
            music.setInfo(info);
            musicRepository.save(music);
            result.setState(true);
            result.setMessage("审核成功！");
        } catch (Exception e) {
            result.setMessage("审核失败");
            result.setState(false);
        }
        return result;
    }

    public List<Music> getALLcheckedmusicByTag_id(int tid) {
        return musicRepository.getALLcheckedmusicByTag_id(tid);
    }

    public List<Music> getSortedMusic() {
        return musicRepository.getsortedMusicList();
    }

    public void deleteUncheckMusic(List<Music> list) {
        list.removeIf(music -> !music.getIscheckd());
    }

    public ResultRequest deleteMusic(Integer id) {
        ResultRequest result = new ResultRequest();
        try {
            System.out.println("id:asdasdasdasd");
            System.out.println(id);
            musicRepository.deleteById(id);
            result.setState(true);
            result.setMessage("删除成功！");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setMessage("删除失败");
            result.setState(false);
        }
        return result;
    }
}
