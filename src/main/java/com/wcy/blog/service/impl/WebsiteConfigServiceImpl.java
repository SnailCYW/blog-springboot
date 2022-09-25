package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.WebsiteConfig;
import com.wcy.blog.service.WebsiteConfigService;
import com.wcy.blog.dao.WebsiteConfigDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_website_config】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigDao, WebsiteConfig>
    implements WebsiteConfigService{

}




