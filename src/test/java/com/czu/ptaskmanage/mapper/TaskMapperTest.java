package com.czu.ptaskmanage.mapper;

import com.czu.ptaskmanage.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testInsertTask() {
        Task task = new Task();
        task.setUserId(1);
        task.setTitle("测试任务");
        task.setDescription("这是一个单元测试任务");
        task.setStartTime(LocalDateTime.now());
        task.setEndTime(LocalDateTime.now().plusHours(1));
        task.setRemindTime(LocalDateTime.now().plusMinutes(30));
        task.setStatus(0);
        task.setPriority(1);
        task.setCreateTime(LocalDateTime.now());
        task.setRepeatType("weekly");
        task.setRepeatValue("5");
        task.setRepeatEnd(LocalDateTime.now().plusWeeks(4));

        int result = taskMapper.insertTask(task);
        assertEquals(1, result);
        assertNotNull(task.getTaskId());
    }

    @Test
    public void testSelectTaskById() {
        Task task = taskMapper.selectTaskById(1); // 请确保数据库中存在ID为1的任务
        assertNotNull(task);
        assertEquals(1, task.getTaskId());
    }

    @Test
    public void testUpdateTask() {
        Task task = taskMapper.selectTaskById(1);
        assertNotNull(task);
        task.setTitle("更新后的标题");
        int result = taskMapper.updateTask(task);
        assertEquals(1, result);

        Task updated = taskMapper.selectTaskById(1);
        assertEquals("更新后的标题", updated.getTitle());
    }

    @Test
    public void testSelectTasksByUserId() {
        List<Task> tasks = taskMapper.selectTasksByUserId(1);
        assertNotNull(tasks);
        assertTrue(tasks.size() > 0);
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setUserId(1);
        task.setTitle("待删除任务");
        task.setCreateTime(LocalDateTime.now());
        taskMapper.insertTask(task);

        Integer id = task.getTaskId();
        int result = taskMapper.deleteTaskById(id);
        assertEquals(1, result);

        Task deleted = taskMapper.selectTaskById(id);
        assertNull(deleted);
    }
}
