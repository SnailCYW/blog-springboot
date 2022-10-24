package com.wcy.blog.strategy.context;

import com.wcy.blog.enums.MarkdownTypeEnum;
import com.wcy.blog.strategy.ArticleImportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/20:25
 */
@Service
public class ArticleImportStrategyContext {

    @Autowired
    private Map<String, ArticleImportStrategy> articleImportStrategyMap;

    public void importArticles(MultipartFile file, String type) {
        articleImportStrategyMap.get(MarkdownTypeEnum.getMarkdownType(type)).importArticles(file);
    }

}
