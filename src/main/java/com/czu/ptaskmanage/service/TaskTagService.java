package com.czu.ptaskmanage.service;

import com.czu.ptaskmanage.entity.Tag;
import com.czu.ptaskmanage.entity.Task;

import java.util.List;

public interface TaskTagService {
    void bindTagsToTask(Integer taskId, List<Integer> tagIds); // 增/改
    List<Tag> getTagsByTaskId(Integer taskId);                 // 查
    List<Task> getTasksByTagId(Integer tagId);                 // 查
    void removeTagsByTaskId(Integer taskId);                   // 删
}
