package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Category;
import com.wcy.blog.service.CategoryService;
import com.wcy.blog.dao.CategoryDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_category】的数据库操作Service实现
* @createDate 2022-09-25 22:59:17
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category>
    implements CategoryService{

}




