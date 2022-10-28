package com.wcy.blog.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
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
import com.wcy.blog.enums.FileExtEnum;
import com.wcy.blog.enums.FilePathEnum;
import com.wcy.blog.exception.BizException;
import com.wcy.blog.service.*;
import com.wcy.blog.dao.ArticleDao;
import com.wcy.blog.strategy.context.ArticleImportStrategyContext;
import com.wcy.blog.strategy.context.SearchStrategyContext;
import com.wcy.blog.strategy.context.UploadStrategyContext;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.CommonUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.util.UserUtils;
import com.wcy.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.CommonConst.ARTICLE_SET;
import static com.wcy.blog.constant.CommonConst.FALSE;
import static com.wcy.blog.constant.RedisPrefixConst.*;
import static com.wcy.blog.enums.ArticleStatusEnum.DRAFT;
import static com.wcy.blog.enums.ArticleStatusEnum.PUBLIC;
import static com.wcy.blog.enums.FilePathEnum.ARTICLE;

/**
 * @author Snail
 * @description 针对表【tb_article】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:17
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

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
    private ArticleTagService articleTagService;
    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private TagService tagService;
    @Autowired
    private HttpSession session;
    @Autowired
    private SearchStrategyContext searchStrategyContext;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;
    @Autowired
    private ArticleImportStrategyContext articleImportStrategyContext;

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
    public ArticleVO getArticleBackById(Integer articleId) {
        ArticleVO articleVo = articleDao.getArticleBackById(articleId);
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
        return ArticlePreviewListDTO.builder().articlePreviewDTOList(articlePreviewDTOList).name(name).build();
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
    public void addOrUpdateArticle(ArticleVO articleVO) {
        // 查询博客配置信息
        CompletableFuture<WebsiteConfigVO> webConfig = CompletableFuture.supplyAsync(() -> blogInfoService.getWebsiteConfig());

        // 保存文章分类
        Category category = saveArticleCategory(articleVO);
        // 保存或修改文章
        Article article = BeanCopyUtils.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        // 设定默认文章封面
        if (StrUtil.isBlank(article.getArticleCover())){
            try {
                article.setArticleCover(webConfig.get().getArticleCover());
            } catch (Exception e) {
                throw new BizException("设定默认文章封面失败");
            }
        }
        article.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(article);
        // 保存文章标签
        saveArticleTag(articleVO, article.getId());
    }

    @Override
    public void deleteOrRestoreArticle(DeleteVO deleteVO) {
        articleDao.update(new Article(), new LambdaUpdateWrapper<Article>()
                .set(Article::getIsDelete, deleteVO.getIsDelete())
                .set(Article::getIsTop, FALSE)
                .in(Article::getId, deleteVO.getIdList()));
    }

    @Override
    public void deleteArticle(List<Integer> articleIdList) {
        articleTagDao.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleIdList));
        articleDao.deleteBatchIds(articleIdList);
    }

    @Override
    public List<String> exportArticles(List<Integer> articleIdList) {
        // 查询文章信息
        List<Article> articleList = articleDao.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleTitle, Article::getArticleContent)
                .in(Article::getId, articleIdList));
        // 写入文件并上传
        List<String> urlList = new ArrayList<>();
        for (Article article : articleList) {
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(article.getArticleContent().getBytes())) {
                String url = uploadStrategyContext.executeUploadStrategy(article.getArticleTitle() + FileExtEnum.MD.getExtName(), inputStream, FilePathEnum.MD.getPath());
                urlList.add(url);
            } catch (Exception e) {
                log.error(StrUtil.format("导入文章失败,堆栈:{}", ExceptionUtil.stacktraceToString(e)));
                throw new BizException("导出文章失败");
            }
        }
        return urlList;
    }

    @Override
    public String uploadImages(MultipartFile file) {
        return uploadStrategyContext.executeUploadStrategy(file, ARTICLE.getPath());
    }

    @Override
    public void importArticles(MultipartFile file, String type) {
        articleImportStrategyContext.importArticles(file, type);
    }

    @Override
    public void updateArticleWithTop(ArticleTopVO articleTopVO) {
        articleDao.update(new Article(), new LambdaUpdateWrapper<Article>()
                .set(Article::getIsTop, articleTopVO.getIsTop())
                .eq(Article::getId, articleTopVO.getId()));
    }

    @Override
    public void likeArticle(Integer articleId) {
        // 判断是否点赞
        String articleLikeKey = ARTICLE_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();
        if (redisService.sIsMember(articleLikeKey, articleId)) {
            // 点过赞则删除文章id
            redisService.sRemove(articleLikeKey, articleId);
            // 文章点赞量-1
            redisService.hDecr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加文章id
            redisService.sAdd(articleLikeKey, articleId);
            // 文章点赞量+1
            redisService.hIncr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }

    private void saveArticleTag(ArticleVO articleVO, Integer articleId) {
        // 编辑文章则删除文章所有标签
        if (Objects.nonNull(articleVO.getId())) {
            articleTagDao.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleVO.getId()));
        }
        // 添加文章标签
        List<String> tagNameList = articleVO.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<Tag> existTagList = tagService.list(new LambdaQueryWrapper<Tag>().in(Tag::getTagName, tagNameList));
            List<String> existTagNameList = existTagList.stream().map(Tag::getTagName).collect(Collectors.toList());
            List<Integer> existTagIdList = existTagList.stream().map(Tag::getId).collect(Collectors.toList());
            // 对比新增不存在的标签
            tagNameList.removeAll(existTagNameList);
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream().map(item -> Tag.builder().tagName(item).build()).collect(Collectors.toList());
                tagService.saveBatch(tagList);
                List<Integer> tagIdList = tagList.stream().map(Tag::getId).collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }
            // 提取标签id绑定文章
            List<ArticleTag> articleTagList = existTagIdList.stream().map(item -> ArticleTag.builder()
                    .articleId(articleId)
                    .tagId(item)
                    .build())
                    .collect(Collectors.toList());
            articleTagService.saveBatch(articleTagList);
        }
    }

    private Category saveArticleCategory(ArticleVO articleVo) {
        // 判断分类是否存在
        Category category = categoryDao.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getCategoryName, articleVo.getCategoryName()));
        if (Objects.isNull(category) && !articleVo.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder().categoryName(articleVo.getCategoryName()).build();
            categoryDao.insert(category);
        }
        return category;
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




