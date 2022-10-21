package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dao.ArticleDao;
import com.wcy.blog.dto.CategoryBackDTO;
import com.wcy.blog.dto.CategoryDTO;
import com.wcy.blog.dto.CategoryOptionDTO;
import com.wcy.blog.entity.Article;
import com.wcy.blog.entity.Category;
import com.wcy.blog.exception.BizException;
import com.wcy.blog.service.CategoryService;
import com.wcy.blog.dao.CategoryDao;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.CategoryVO;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author Snail
* @description 针对表【tb_category】的数据库操作Service实现
* @createDate 2022-09-25 22:59:17
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category>
    implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public PageResult<CategoryBackDTO> listCategoriesBack(ConditionVO condition) {
        Integer count = categoryDao.selectCount(null);
        List<CategoryBackDTO> categoryBackDTOList = categoryDao.listCategoriesBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(count, categoryBackDTOList);
    }

    @Override
    public List<CategoryOptionDTO> searchCategories(ConditionVO condition) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(condition.getKeywords())) {
            queryWrapper.like(Category::getCategoryName, condition.getKeywords());
        }
        List<Category> categoryList = categoryDao.selectList(queryWrapper);
        List<CategoryOptionDTO> categoryOptionDTOList = BeanCopyUtils.copyList(categoryList, CategoryOptionDTO.class);
        return categoryOptionDTOList;
    }

    @Override
    public PageResult<CategoryDTO> listCategories() {
        Integer count = categoryDao.selectCount(null);
        List<CategoryDTO> categoryDTOList = categoryDao.listCategories(PageUtils.getLimitCurrent(), PageUtils.getSize());
        return new PageResult<>(count, categoryDTOList);
    }

    @Override
    public void addOrUpdateCategory(CategoryVO categoryVO) {
        Category existCategory = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            throw new BizException("分类名已存在");
        }
        this.saveOrUpdate(BeanCopyUtils.copyObject(categoryVO, Category.class));
    }

    @Override
    public void deleteCategories(List<Integer> categoryIdList) {
        Integer count = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, categoryIdList));
        if (count > 0) {
            throw new BizException("标签下存在文章");
        }
        categoryDao.deleteBatchIds(categoryIdList);
    }
}




