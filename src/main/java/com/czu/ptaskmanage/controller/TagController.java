package com.czu.ptaskmanage.controller;

import com.czu.ptaskmanage.entity.Tag;
import com.czu.ptaskmanage.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "标签管理", description = "提供标签的增删改查接口")
public class TagController {

    @Resource
    private TagService tagService;

    @Operation(summary = "根据用户ID获取标签列表")
    @GetMapping("/user/{userId}")
    public List<Tag> getTagsByUserId(@PathVariable Integer userId) {
        return tagService.getTagsByUserId(userId);
    }

    @Operation(summary = "根据标签ID获取标签")
    @GetMapping("/{tagId}")
    public Tag getTagById(@PathVariable Integer tagId) {
        return tagService.getTagById(tagId);
    }

    @Operation(summary = "新增标签")
    @PostMapping
    public int addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @Operation(summary = "更新标签")
    @PutMapping
    public int updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

    @Operation(summary = "删除标签")
    @DeleteMapping("/{tagId}")
    public int deleteTag(@PathVariable Integer tagId) {
        return tagService.deleteTag(tagId);
    }
}
