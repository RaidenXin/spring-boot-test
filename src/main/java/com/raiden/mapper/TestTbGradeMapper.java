package com.raiden.mapper;

import com.raiden.model.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:20 2022/1/12
 * @Modified By:
 */
@Mapper
public interface TestTbGradeMapper {

    @Select("SELECT ID AS id,USER_NAME AS userName,COURSE AS course,SCORE AS score FROM test_tb_grade WHERE USER_NAME = #{userName}")
    public List<Grade> query(@Param("userName") String userName);
}
