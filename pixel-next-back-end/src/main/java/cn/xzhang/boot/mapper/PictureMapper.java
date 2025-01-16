package cn.xzhang.boot.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.xzhang.boot.common.pojo.PageResult;
import cn.xzhang.boot.core.mapper.BaseMapperPlus;
import cn.xzhang.boot.model.dto.picture.PicturePageReqDTO;
import cn.xzhang.boot.model.entity.Picture;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Objects;

/**
 * @author XiaoZhangCode
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 * @description 针对表【picture(图片表)】的数据库操作Mapper
 */
public interface PictureMapper extends BaseMapperPlus<Picture> {

    default PageResult<Picture> selectPage(PicturePageReqDTO picturePageReqDTO) {
        LambdaQueryWrapper<Picture> lambdaQueryWrapper = new LambdaQueryWrapper<Picture>()
                .like(Objects.nonNull(picturePageReqDTO.getName()), Picture::getName, picturePageReqDTO.getName())
                .like(Objects.nonNull(picturePageReqDTO.getIntroduction()), Picture::getIntroduction, picturePageReqDTO.getIntroduction())
                .like(Objects.nonNull(picturePageReqDTO.getCategory()), Picture::getCategory, picturePageReqDTO.getCategory())
                .like(Objects.nonNull(picturePageReqDTO.getPicFormat()), Picture::getPicFormat, picturePageReqDTO.getPicFormat())
                .orderByDesc(Picture::getCreateTime);
        if (CollUtil.isNotEmpty(picturePageReqDTO.getTags())) {
            for (String tag : picturePageReqDTO.getTags()) {
                lambdaQueryWrapper.like(Picture::getTags, "\"" + tag + "\"");
            }
        }
        return selectPage(picturePageReqDTO, lambdaQueryWrapper);
    }

}




