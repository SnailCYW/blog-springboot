package com.wcy.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wcy.blog.entity.Talk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/26/19:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalkBackDTO {

    private String avatar;

    private String content;

    private LocalDateTime createTime;

    private Integer id;

    private String images;

    private List<String> imgList;

    private Integer isTop;

    private String nickname;

    private Integer status;

}
