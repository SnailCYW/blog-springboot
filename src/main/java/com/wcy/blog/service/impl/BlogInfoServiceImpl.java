package com.wcy.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wcy.blog.dao.*;
import com.wcy.blog.dto.*;
import com.wcy.blog.entity.Article;
import com.wcy.blog.entity.Tag;
import com.wcy.blog.entity.WebsiteConfig;
import com.wcy.blog.service.BlogInfoService;
import com.wcy.blog.service.PageService;
import com.wcy.blog.service.RedisService;
import com.wcy.blog.service.UniqueViewService;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.IpUtils;
import com.wcy.blog.vo.BlogInfoVO;
import com.wcy.blog.vo.PageVO;
import com.wcy.blog.vo.WebsiteConfigVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.CommonConst.*;
import static com.wcy.blog.constant.RedisPrefixConst.*;
import static com.wcy.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/20/20:38
 */
@Slf4j
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private WebsiteConfigDao websiteConfigDao;
    @Autowired
    private UniqueViewService uniqueViewService;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PageService pageService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public BlogHomeInfoDTO getBlogInfo() {
        // 查询文章数量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus()));
        // 查询分类数量
        Integer categoryCount = categoryDao.selectCount(null);
        // 查询标签数量
        Integer tagCount = tagDao.selectCount(null);
        // 查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        // 查询网站配置
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();
        // 查询页面图片
        List<PageVO> pageVOList = pageService.listPages();


        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewsCount)
                .websiteConfig(websiteConfig)
                .pageList(pageVOList)
                .build();
    }

    @Override
    public String getAboutMe() {
        Object value = redisService.get(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Override
    public BlogBackInfoDTO getBlogInfoBack() {
        // 查询文章数量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus()));
        // 查询用户数量
        Integer userCount = userAuthDao.selectCount(null);
        // 查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        // 查询留言量
        Integer messageCount = messageDao.selectCount(null);
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewList = uniqueViewService.listUniqueViews();
        // 查询文章统计
        List<ArticleStatisticsDTO> articleStatisticsList = articleDao.listArticleStatistics();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryDao.listCategoryDTO();
        // 查询标签数据
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagDao.selectList(null), TagDTO.class);
        // 查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
        List<ArticleRankDTO> articleRankDTOList = CollectionUtils.isNotEmpty(articleMap) ? getArticleRank(articleMap) : null;
        return BlogBackInfoDTO.builder()
                .articleCount(articleCount)
                .articleRankDTOList(articleRankDTOList)
                .articleStatisticsList(articleStatisticsList)
                .categoryDTOList(categoryDTOList)
                .messageCount(messageCount)
                .tagDTOList(tagDTOList)
                .uniqueViewDTOList(uniqueViewList)
                .userCount(userCount)
                .viewsCount(Integer.parseInt(viewsCount))
                .build();
    }

    private List<ArticleRankDTO> getArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleDao.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle)
                .in(Article::getId, articleIdList))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }

    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        // 先尝试从缓存获取数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            // 缓存获取不到，从数据库中加载
            String config = websiteConfigDao.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;

    }

    @Override
    public void updateAboutMe(BlogInfoVO blogInfoVO) {
        redisService.set(ABOUT, blogInfoVO.getAboutContent());
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        // 修改数据库网站配置
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(1)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigDao.updateById(websiteConfig);
        // 删除redis缓存
        redisService.set(WEBSITE_CONFIG, websiteConfig.getConfig());
    }

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断过去是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }
}
