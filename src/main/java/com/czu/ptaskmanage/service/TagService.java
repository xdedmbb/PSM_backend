package com.czu.ptaskmanage.service;

import com.czu.ptaskmanage.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTagsByUserId(Integer userId);
    Tag getTagById(Integer tagId);
    int addTag(Tag tag);
    int updateTag(Tag tag);
    int deleteTag(Integer tagId);
}
