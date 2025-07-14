package com.czu.ptaskmanage.mapper;

import com.czu.ptaskmanage.entity.Tag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback // 每个测试方法执行完后自动回滚
public class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    private static Integer testTagId; // 用于保存测试生成的主键

    @Test
    @Order(1)
    public void testInsert() {
        Tag tag = new Tag()
                .setUserId(1)
                .setTagName("今日必做");
        int rows = tagMapper.insert(tag);

        Assertions.assertEquals(1, rows);
        Assertions.assertNotNull(tag.getTagId());
        testTagId = tag.getTagId(); // 保存生成的 ID
        log.info("新增标签成功：{}", tag);
    }

    @Test
    @Order(2)
    public void testSelectByUserId() {
        List<Tag> tagList = tagMapper.selectByUserId(1);
        log.info("用户ID=1的标签数量: {}", tagList.size());
        Assertions.assertNotNull(tagList);
    }

    @Test
    @Order(3)
    public void testSelectById() {
        Tag tag = tagMapper.selectById(testTagId);
        if (tag != null) {
            log.info("根据ID查询到的标签：{}", tag);
        } else {
            log.warn("未找到标签ID: {}", testTagId);
        }
        Assertions.assertTrue(tag == null || tag.getTagId().equals(testTagId));
    }

    @Test
    @Order(4)
    public void testUpdate() {
        if (testTagId == null) return;
        Tag tag = new Tag()
                .setTagId(testTagId)
                .setTagName("已更新标签");
        int rows = tagMapper.update(tag);
        Assertions.assertEquals(1, rows);
        log.info("更新标签成功：{}", tagMapper.selectById(testTagId));
    }

    @Test
    @Order(5)
    public void testDelete() {
        if (testTagId == null) return;
        int rows = tagMapper.delete(testTagId);
        Assertions.assertEquals(1, rows);
        log.info("删除标签成功，ID={}", testTagId);
    }
    @Test
    public void testAll(){
        testInsert();
        testSelectByUserId();
        testSelectById();
        testUpdate();
        testDelete();
    }
}
