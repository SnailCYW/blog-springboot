package com.wcy.blog.strategy.context;

import com.wcy.blog.dto.ArticleSearchDTO;
import com.wcy.blog.strategy.SearchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.wcy.blog.enums.SearchModeEnum.getStrategy;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/11:55
 */
@Slf4j
@Service
public class SearchStrategyContext {

    @Value("${search.mode}")
    private String searchMode;

    @Autowired
    private Map<String, SearchStrategy> searchStrategyMap;

    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        String strategy = getStrategy(searchMode);
        SearchStrategy searchStrategy = searchStrategyMap.get(strategy);
        log.info("searchMode: "+searchMode+"searchStrategy: "+searchStrategy);
        return searchStrategy.searchArticle(keywords);
    }

}
