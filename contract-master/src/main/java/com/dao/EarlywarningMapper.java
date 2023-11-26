package com.dao;

import com.pojo.Earlywarning;
import com.pojo.EarlywarningExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EarlywarningMapper {
    long countByExample(EarlywarningExample example);

    int deleteByExample(EarlywarningExample example);

    int deleteByPrimaryKey(String id);

    int insert(Earlywarning record);

    int insertSelective(Earlywarning record);

    List<Earlywarning> selectByExample(EarlywarningExample example);

    Earlywarning selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Earlywarning record, @Param("example") EarlywarningExample example);

    int updateByExample(@Param("record") Earlywarning record, @Param("example") EarlywarningExample example);

    int updateByPrimaryKeySelective(Earlywarning record);

    int updateByPrimaryKey(Earlywarning record);
}