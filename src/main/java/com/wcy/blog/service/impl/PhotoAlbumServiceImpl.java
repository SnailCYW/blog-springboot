package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.PhotoAlbum;
import com.wcy.blog.service.PhotoAlbumService;
import com.wcy.blog.dao.PhotoAlbumDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_photo_album(相册)】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumDao, PhotoAlbum>
    implements PhotoAlbumService{

}




