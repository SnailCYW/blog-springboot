package com.wcy.blog.dao;

import com.wcy.blog.dto.CategoryBackDTO;
import com.wcy.blog.dto.CategoryDTO;
import com.wcy.blog.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Snail
 * @description 针对表【tb_category】的数据库操作Mapper
 * @createDate 2022-09-25 22:58:52
 * @Entity com.wcy.blog.entity.Category
 */
public interface CategoryDao extends BaseMapper<Category> {

    List<CategoryBackDTO> listCategoriesBack(@Param("current") Long current,
                                             @Param("size") Long size,
                                             @Param("condition") ConditionVO condition);

    List<CategoryDTO> listCategories(@Param("current") Long current,
                                     @Param("size") Long size);

    List<CategoryDTO> listCategoryDTO();

}




