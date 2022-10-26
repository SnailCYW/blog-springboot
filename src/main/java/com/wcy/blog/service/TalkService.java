package com.wcy.blog.service;

import com.wcy.blog.dto.TalkBackDTO;
import com.wcy.blog.dto.TalkDTO;
import com.wcy.blog.entity.Talk;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.TalkVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_talk】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface TalkService extends IService<Talk> {

    PageResult<TalkBackDTO> listTalksBack(ConditionVO condition);

    void addOrUpdateTalk(TalkVO talkVO);

    void deleteTalks(List<Integer> talkIdList);

    String uploadTalkImages(MultipartFile file);

    TalkBackDTO listTalksBackById(Integer talkId);

    List<String> listHomeTalks();

    PageResult<TalkDTO> listTalks();

    TalkDTO getTalkById(Integer talkId);

    void likeTalk(Integer talkId);
}
