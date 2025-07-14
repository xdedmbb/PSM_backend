package com.czu.ptaskmanage.service.impl;

import com.czu.ptaskmanage.entity.Tag;
import com.czu.ptaskmanage.mapper.TagMapper;
import com.czu.ptaskmanage.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagsByUserId(Integer userId) {
        return tagMapper.selectByUserId(userId);
    }

    @Override
    public Tag getTagById(Integer tagId) {
        return tagMapper.selectById(tagId);
    }

    @Override
    public int addTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.update(tag);
    }

    @Override
    public int deleteTag(Integer tagId) {
        return tagMapper.delete(tagId);
    }
}
