package cn.xzhang.boot.mapper;

import cn.xzhang.boot.common.pojo.PageResult;
import cn.xzhang.boot.core.mapper.BaseMapperPlus;
import cn.xzhang.boot.model.dto.picture.PicturePageReqDTO;
import cn.xzhang.boot.model.entity.Picture;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Objects;

/**
 * @author XiaoZhangCode
 * @description 针对表【picture(图片表)】的数据库操作Mapper
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
public interface PictureMapper extends BaseMapperPlus<Picture> {

    default PageResult<Picture> selectPage(PicturePageReqDTO picturePageReqDTO) {
        return selectPage(picturePageReqDTO, new LambdaQueryWrapper<Picture>()
                .eq(Objects.nonNull(picturePageReqDTO.getUrl()), Picture::getUrl, picturePageReqDTO.getUrl())
                .eq(Objects.nonNull(picturePageReqDTO.getName()), Picture::getName, picturePageReqDTO.getName())
                .eq(Objects.nonNull(picturePageReqDTO.getIntroduction()), Picture::getIntroduction, picturePageReqDTO.getIntroduction())
                .eq(Objects.nonNull(picturePageReqDTO.getCategory()), Picture::getCategory, picturePageReqDTO.getCategory())
                .eq(Objects.nonNull(picturePageReqDTO.getTags()), Picture::getTags, picturePageReqDTO.getTags())
                .eq(Objects.nonNull(picturePageReqDTO.getPicFormat()), Picture::getPicFormat, picturePageReqDTO.getPicFormat())
                .orderByDesc(Picture::getCreateTime)

        );
    }

}




