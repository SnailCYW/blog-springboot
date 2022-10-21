package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dao.ArticleTagDao;
import com.wcy.blog.dao.CategoryDao;
import com.wcy.blog.dao.TagDao;
import com.wcy.blog.dto.*;
import com.wcy.blog.entity.Article;
import com.wcy.blog.entity.ArticleTag;
import com.wcy.blog.entity.Category;
import com.wcy.blog.entity.Tag;
import com.wcy.blog.service.ArticleService;
import com.wcy.blog.dao.ArticleDao;
import com.wcy.blog.service.RedisService;
import com.wcy.blog.strategy.context.SearchStrategyContext;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.CommonUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.ArticleVo;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

import static com.wcy.blog.constant.CommonConst.ARTICLE_SET;
import static com.wcy.blog.constant.CommonConst.FALSE;
import static com.wcy.blog.constant.RedisPrefixConst.ARTICLE_LIKE_COUNT;
import static com.wcy.blog.constant.RedisPrefixConst.ARTICLE_VIEWS_COUNT;
import static com.wcy.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * @author Snail
 * @description 针对表【tb_article】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:17
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article>
        implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ArticleTagDao articleTagDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private HttpSession session;
    @Autowired
    private SearchStrategyContext searchStrategyContext;

    @Override
    public PageResult<ArticleBackDTO> listArticleBack(ConditionVO condition) {
        Integer count = articleDao.articleBackCount(condition);
        if (count <= 0) {
            return new PageResult<>();
        }
        List<ArticleBackDTO> articleBackDTOList = articleDao.listArticleBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);
        Map<String, Object> likeCountMap = redisService.hGetAll(ARTICLE_LIKE_COUNT);
        articleBackDTOList.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
        });
        return new PageResult<>(count, articleBackDTOList);
    }

    @Override
    public ArticleVo getArticleBackById(Integer articleId) {
        ArticleVo articleVo = articleDao.getArticleBackById(articleId);
        return articleVo;
    }

    @Override
    public List<ArticleHomeDTO> listHomeArticles() {
        List<ArticleHomeDTO> articleHomeDTOList = articleDao.listHomeArticles(PageUtils.getLimitCurrent(), PageUtils.getSize());
        return articleHomeDTOList;
    }

    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<Article> articlePage = articleDao.selectPage(page, new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
                .orderByDesc(Article::getCreateTime)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus()));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>((int) articlePage.getTotal(), archiveDTOList);
    }

    @Override
    public ArticlePreviewListDTO listArticleByCondition(ConditionVO condition) {
        List<ArticlePreviewDTO> articlePreviewDTOList = articleDao.listArticleByCondition(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        String name = null;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getCategoryName)
                    .eq(Category::getId, condition.getCategoryId())).getCategoryName();
        } else {
            name = tagDao.selectOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getTagName)
                    .eq(Tag::getId, condition.getTagId())).getTagName();
        }
        return ArticlePreviewListDTO.builder().articlePreviewDTO(articlePreviewDTOList).name(name).build();
    }

    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // 更新浏览量
        updateArticleViewsCount(articleId);

        ArticleDTO article = articleDao.getArticleById(articleId);
        Article last = articleDao.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleCover, Article::getArticleTitle, Article::getId)
                .eq(Article::getIsDelete, FALSE).eq(Article::getStatus, PUBLIC.getStatus())
                .lt(Article::getCreateTime, article.getCreateTime())
                .orderByAsc(Article::getCreateTime)
                .last("limit 1"));
        Article next = articleDao.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleCover, Article::getArticleTitle, Article::getId)
                .eq(Article::getIsDelete, FALSE).eq(Article::getStatus, PUBLIC.getStatus())
                .gt(Article::getCreateTime, article.getCreateTime())
                .orderByDesc(Article::getCreateTime)
                .last("limit 1"));
        List<Article> newestArticles = articleDao.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleCover, Article::getArticleTitle, Article::getCreateTime, Article::getId)
                .eq(Article::getIsDelete, FALSE).eq(Article::getStatus, PUBLIC.getStatus())
                .ne(Article::getId, articleId)
                .orderByDesc(Article::getCreateTime)
                .last("limit 0, 5"));
        List<ArticleRecommendDTO> recommendDTOS = articleDao.selectRecommendArticles(articleId);

        ArticleDTO articleDTO = BeanCopyUtils.copyObject(article, ArticleDTO.class);

        articleDTO.setRecommendArticleList(recommendDTOS);
        articleDTO.setNewestArticleList(BeanCopyUtils.copyList(newestArticles, ArticleRecommendDTO.class));

        articleDTO.setLastArticle(BeanCopyUtils.copyObject(last, ArticlePaginationDTO.class));
        articleDTO.setNextArticle(BeanCopyUtils.copyObject(next, ArticlePaginationDTO.class));

        articleDTO.setViewsCount(redisService.zScore(ARTICLE_VIEWS_COUNT, articleId).intValue());
        articleDTO.setLikeCount((Integer) redisService.hGet(ARTICLE_LIKE_COUNT, articleId.toString()));

        return articleDTO;
    }

    @Override
    public List<ArticleSearchDTO> searchArticles(ConditionVO condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateArticle(ArticleVo articleVo) {
        Category category = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, articleVo.getCategoryName()));
        List<Tag> tagList = tagDao.selectList(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .in(Tag::getTagName, articleVo.getTagNameList()));
        List<ArticleTag> articleTags = articleTagDao.selectList(new LambdaQueryWrapper<ArticleTag>()
                .select(ArticleTag::getId)
                .eq(ArticleTag::getArticleId, Optional.ofNullable(articleVo.getId()).orElse(0)));

        if (Objects.nonNull(articleTags) && articleTags.size() > 0) {
            for (ArticleTag articleTag : articleTags) {
                articleTagDao.delete(new LambdaQueryWrapper<ArticleTag>()
                        .eq(ArticleTag::getId, articleTag.getId()));
            }
        }

        Article article = BeanCopyUtils.copyObject(articleVo, Article.class);
        article.setCategoryId(category.getId());

        saveOrUpdate(article);

    }

    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    private void updateArticleViewsCount(Integer articleId) {
        Set<Integer> articleSet = CommonUtils.castSet(
                Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElseGet(HashSet::new), Integer.class
        );
        // 判断是否第一次访问
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }
}




