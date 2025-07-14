package com.czu.ptaskmanage.mapper;

import org.apache.ibatis.annotations.*;

import com.czu.ptaskmanage.entity.Tag;
import com.czu.ptaskmanage.entity.Task;

import java.util.List;

@Mapper
public interface TaskTagMapper {

    // 根据任务ID查询其所有标签
    @Select("        SELECT t.* FROM tag t\n" +
            "        JOIN task_tag tt ON t.tag_id = tt.tag_id\n" +
            "        WHERE tt.task_id = #{taskId}")
    List<Tag> selectTagsByTaskId(@Param("taskId") Integer taskId);

    // 根据标签ID查询其所有任务
    @Select("        SELECT ta.* FROM task ta\n" +
            "        JOIN task_tag tt ON ta.task_id = tt.task_id\n" +
            "        WHERE tt.tag_id = #{tagId}")
    List<Task> selectTasksByTagId(@Param("tagId") Integer tagId);

    // 插入关系
    @Insert("        INSERT INTO task_tag(task_id, tag_id)\n" +
            "        VALUES (#{taskId}, #{tagId})")
    int insertTaskTag(@Param("taskId") Integer taskId, @Param("tagId") Integer tagId);

    // 删除关系（某个任务的所有标签）
    @Delete("DELETE FROM task_tag WHERE task_id = #{taskId}")
    int deleteTagsByTaskId(Integer taskId);
}
