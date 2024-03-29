package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Page;
import com.wcy.blog.service.PageService;
import com.wcy.blog.dao.PageDao;
import com.wcy.blog.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_page(页面)】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class PageServiceImpl extends ServiceImpl<PageDao, Page>
    implements PageService{

    @Autowired
    private PageDao pageDao;

    @Override
    public List<PageVO> listPages() {
        return pageDao.listPages();
    }

    @Override
    public void addOrUpdatePage(PageVO pageVO) {
        Page page = Page.builder()
                .id(pageVO.getId())
                .pageCover(pageVO.getPageCover())
                .pageLabel(pageVO.getPageLabel())
                .pageName(pageVO.getPageName())
                .build();
        this.saveOrUpdate(page);
    }

    @Override
    public void deletePage(Integer pageId) {
        pageDao.deleteById(pageId);
    }
}




