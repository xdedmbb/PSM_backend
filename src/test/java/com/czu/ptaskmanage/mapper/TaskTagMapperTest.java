package com.czu.ptaskmanage.mapper;

import com.czu.ptaskmanage.entity.Tag;
import com.czu.ptaskmanage.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
@Rollback // 保证测试结束后数据不会污染数据库
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskTagMapperTest {

    @Autowired
    private TaskTagMapper taskTagMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TagMapper tagMapper;

    private static Integer taskId;
    private static Integer tagId;

    @Test
    @Order(1)
    public void testPrepareTaskAndTag() {
        Task task = new Task()
                .setUserId(1)
                .setTitle("测试任务");
        taskMapper.insertTask(task);
        taskId = task.getTaskId();
        Assertions.assertNotNull(taskId);
        log.info("插入测试任务：{}", task);

        Tag tag = new Tag()
                .setUserId(1)
                .setTagName("测试标签");
        tagMapper.insert(tag);
        tagId = tag.getTagId();
        Assertions.assertNotNull(tagId);
        log.info("插入测试标签：{}", tag);
    }

    @Test
    @Order(2)
    public void testInsertTaskTagRelation() {
        int rows = taskTagMapper.insertTaskTag(taskId, tagId);
        Assertions.assertEquals(1, rows);
        log.info("插入 task_tag 关联关系成功: taskId={}, tagId={}", taskId, tagId);
    }

    @Test
    @Order(3)
    public void testSelectTagsByTaskId() {
        List<Tag> tags = taskTagMapper.selectTagsByTaskId(taskId);
        Assertions.assertFalse(tags.isEmpty());
        log.info("查询任务对应标签成功：{}", tags);
    }

    @Test
    @Order(4)
    public void testSelectTasksByTagId() {
        List<Task> tasks = taskTagMapper.selectTasksByTagId(tagId);
        Assertions.assertFalse(tasks.isEmpty());
        log.info("查询标签下的任务成功：{}", tasks);
    }

    @Test
    @Order(5)
    public void testDeleteTagsByTaskId() {
        int rows = taskTagMapper.deleteTagsByTaskId(taskId);
        Assertions.assertEquals(1, rows);
        log.info("解除任务与标签的绑定成功：taskId={}", taskId);
    }

    @Test
    public void testAll(){
        testPrepareTaskAndTag();
        testInsertTaskTagRelation();
        testSelectTagsByTaskId();
        testSelectTasksByTagId();
        testDeleteTagsByTaskId();
    }
}
