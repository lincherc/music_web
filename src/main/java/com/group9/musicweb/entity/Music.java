package com.group9.musicweb.entity;

import com.group9.musicweb.Dao.TagRepository;
import com.group9.musicweb.util.SpringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String resaddr;
    private String name;
    private String info;
    private String zuozhe;
    private String mvresaddr;
    private String imgresaddr;
    private boolean ischeckd;
    private long play_num;
    private long comment_num;
    private Integer tag_id;

    private String tagName;

    public Music(@NotNull String resaddr, String name, String info, String zuozhe, String mvresaddr, String imgresaddr, boolean ischeckd) {
        this.resaddr = resaddr;
        this.name = name;
        this.info = info;
        this.zuozhe = zuozhe;
        this.mvresaddr = mvresaddr;
        this.imgresaddr = imgresaddr;
        this.ischeckd = ischeckd;
    }

    public Music() {
    }

    /* @OneToOne
    private Tag tag;*/

    /*public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }*/

    public String getTagName() {
        if (!Objects.isNull(tag_id)) {
            return SpringUtils.getBean(TagRepository.class).findTagById(tag_id).getTagName();
        }
        return "暂无";
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public long getPlay_num() {
        return play_num;
    }

    public void setPlay_num(long play_num) {
        this.play_num = play_num;
    }

    public long getComment_num() {
        return comment_num;
    }

    public void setComment_num(long comment_num) {
        this.comment_num = comment_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResaddr() {
        return resaddr;
    }

    public void setResaddr(String resaddr) {
        this.resaddr = resaddr;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getZuozhe() {
        return zuozhe;
    }

    public void setZuozhe(String zuozhe) {
        this.zuozhe = zuozhe;
    }

    public String getMvresaddr() {
        return mvresaddr;
    }

    public void setMvresaddr(String mvresaddr) {
        this.mvresaddr = mvresaddr;
    }

    public String getImgresaddr() {
        return imgresaddr;
    }

    public void setImgresaddr(String imgresaddr) {
        this.imgresaddr = imgresaddr;
    }

    public boolean getIscheckd() {
        return ischeckd;
    }

    public void setIscheckd(boolean ischeckd) {
        this.ischeckd = ischeckd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
