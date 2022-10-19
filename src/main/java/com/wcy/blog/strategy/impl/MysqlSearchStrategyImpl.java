package com.wcy.blog.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wcy.blog.dao.ArticleDao;
import com.wcy.blog.dto.ArticleDTO;
import com.wcy.blog.dto.ArticleSearchDTO;
import com.wcy.blog.entity.Article;
import com.wcy.blog.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.CommonConst.*;
import static com.wcy.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/11:58
 */
@Service("MysqlSearchStrategyImpl")
public class MysqlSearchStrategyImpl implements SearchStrategy {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        // 判空
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        // 搜索文章
        List<Article> articleList = articleDao.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleContent, Article::getArticleTitle, Article::getId, Article::getIsDelete, Article::getStatus)
                .eq(Article::getIsDelete, FALSE).eq(Article::getStatus, PUBLIC.getStatus())
                .and(item -> item.like(Article::getArticleTitle, keywords)
                                .or()
                                .like(Article::getArticleContent, keywords)
                ));
        // 关键词高亮处理
        List<ArticleSearchDTO> result = articleList.stream().map(item -> {
            String articleContent = item.getArticleContent();
            int index = item.getArticleContent().indexOf(keywords);
            if (index != -1) {
                // 获取关键词前面部分文字
                int preIndex = index > 25 ? index - 25 : 0;
                String preText = item.getArticleContent().substring(preIndex, index);
                // 获取关键词及后面部分文字
                int last = index + keywords.length();
                int postLength = item.getArticleContent().length() - last;
                int postIndex = postLength > 175 ? last + 175 : item.getArticleContent().length();
                String postText = item.getArticleContent().substring(index, postIndex);
                // 关键词高亮
                articleContent = (preText + postText).replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            }
            String articleTitle = item.getArticleTitle().replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            return ArticleSearchDTO
                    .builder()
                    .id(item.getId())
                    .articleTitle(articleTitle)
                    .articleContent(articleContent)
                    .build();
        }).collect(Collectors.toList());
        return result;
    }
}
