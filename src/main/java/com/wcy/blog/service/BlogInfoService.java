package com.wcy.blog.service;

import com.wcy.blog.dto.BlogBackInfoDTO;
import com.wcy.blog.dto.BlogHomeInfoDTO;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/20/20:38
 */

public interface BlogInfoService {
    BlogHomeInfoDTO getBlogInfo();

    String getAboutMe();

    BlogBackInfoDTO getBlogInfoBack();
}
