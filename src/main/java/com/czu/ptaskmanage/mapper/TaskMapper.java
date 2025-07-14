package com.czu.ptaskmanage.mapper;

import com.czu.ptaskmanage.entity.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskMapper {
    // 插入任务
    @Insert("INSERT INTO task(user_id, title, description, start_time, end_time, remind_time, status,\n" +
            "                         priority, create_time, repeat_type, repeat_value, repeat_end)\n" +
            "        VALUES(#{userId}, #{title}, #{description}, #{startTime}, #{endTime}, #{remindTime},\n" +
            "               #{status}, #{priority}, #{createTime}, #{repeatType}, #{repeatValue}, #{repeatEnd})")
    @Options(useGeneratedKeys = true, keyProperty = "taskId")
    int insertTask(Task task);

    // 根据 ID 删除任务
    @Delete("DELETE FROM task WHERE task_id = #{id}")
    int deleteTaskById(Integer id);

    // 更新任务
    @Update("UPDATE task SET\n" +
            "            title = #{title},\n" +
            "            description = #{description},\n" +
            "            start_time = #{startTime},\n" +
            "            end_time = #{endTime},\n" +
            "            remind_time = #{remindTime},\n" +
            "            status = #{status},\n" +
            "            priority = #{priority},\n" +
            "            repeat_type = #{repeatType},\n" +
            "            repeat_value = #{repeatValue},\n" +
            "            repeat_end = #{repeatEnd}\n" +
            "        WHERE task_id = #{taskId}")
    int updateTask(Task task);

    // 根据用户ID查询该用户的全部任务
    @Select("SELECT * FROM task WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Task> selectTasksByUserId(Integer userId);

    // 根据任务ID查询单个任务
    @Select("SELECT * FROM task WHERE task_id = #{id}")
    Task selectTaskById(Integer id);
}
