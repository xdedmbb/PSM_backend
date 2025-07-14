package com.czu.ptaskmanage.mapper;

import com.czu.ptaskmanage.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {

    @Select("SELECT * FROM tag WHERE user_id = #{userId}")
    List<Tag> selectByUserId(Integer userId);

    @Select("SELECT * FROM tag WHERE tag_id = #{tagId}")
    Tag selectById(Integer tagId);

    @Insert("        INSERT INTO tag(user_id, tag_name)\n" +
            "        VALUES (#{userId}, #{tagName})")
    @Options(useGeneratedKeys = true, keyProperty = "tagId")
    int insert(Tag tag);

    @Update("        UPDATE tag\n" +
            "        SET tag_name = #{tagName}\n" +
            "        WHERE tag_id = #{tagId}")
    int update(Tag tag);

    @Delete("DELETE FROM tag WHERE tag_id = #{tagId}")
    int delete(Integer tagId);
}
