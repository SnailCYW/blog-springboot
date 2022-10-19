package com.wcy.blog.strategy;

import com.wcy.blog.dto.ArticleSearchDTO;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/11:56
 */
public interface SearchStrategy {

    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return {@link List <ArticleSearchDTO>} 文章列表
     */
    List<ArticleSearchDTO> searchArticle(String keywords);

}
