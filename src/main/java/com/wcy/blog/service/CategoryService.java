package com.wcy.blog.service;

import com.wcy.blog.dto.CategoryBackDTO;
import com.wcy.blog.dto.CategoryDTO;
import com.wcy.blog.dto.CategoryOptionDTO;
import com.wcy.blog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.CategoryVO;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_category】的数据库操作Service
* @createDate 2022-09-25 22:59:17
*/
public interface CategoryService extends IService<Category> {

    PageResult<CategoryBackDTO> listCategoriesBack(ConditionVO condition);

    List<CategoryOptionDTO> searchCategories(ConditionVO condition);

    PageResult<CategoryDTO> listCategories();

    void addOrUpdateCategory(CategoryVO categoryVO);

    void deleteCategories(List<Integer> categoryIdList);
}
