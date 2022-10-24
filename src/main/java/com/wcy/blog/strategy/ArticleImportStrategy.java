package com.wcy.blog.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文章导入策略
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/20:29
 */
public interface ArticleImportStrategy {

    /**
     * 导入文章
     *
     * @param file 文件
     */
    void importArticles(MultipartFile file);
}
