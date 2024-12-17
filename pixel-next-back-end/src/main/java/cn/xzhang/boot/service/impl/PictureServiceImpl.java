package cn.xzhang.boot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.xzhang.boot.common.exception.ServiceException;
import cn.xzhang.boot.common.pojo.PageResult;
import cn.xzhang.boot.mapper.PictureMapper;
import cn.xzhang.boot.model.dto.picture.PicturePageReqDTO;
import cn.xzhang.boot.model.dto.picture.PictureSaveReqDTO;
import cn.xzhang.boot.model.entity.Picture;
import cn.xzhang.boot.model.vo.picture.PictureSimpleVo;
import cn.xzhang.boot.model.vo.picture.PictureVo;
import cn.xzhang.boot.service.PictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static cn.xzhang.boot.common.exception.enums.GlobalErrorCodeConstants.*;
import static cn.xzhang.boot.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 针对表【picture(图片表)】的数据库操作Service实现
 *
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Resource
    private PictureMapper pictureMapper;

    /**
     * 添加新图片
     *
     * @param pictureReqDTO 图片信息请求DTO
     * @return 添加成功返回图片id
     */
    @Override
    public long addPicture(PictureSaveReqDTO pictureReqDTO) {
        Picture picture = new Picture();
        BeanUtil.copyProperties(pictureReqDTO, picture);
        if (!this.save(picture)) {
            throw exception(ADD_FAIL);
        }
        return picture.getId();
    }

    /**
     * 更新图片信息
     *
     * @param pictureReqDTO 图片信息更新请求DTO
     * @return 更新成功返回true
     */
    @Override
    public boolean updatePicture(PictureSaveReqDTO pictureReqDTO) {
        if (pictureReqDTO.getId() == null) {
            throw exception(BAD_REQUEST);
        }
        Picture picture = new Picture();
        BeanUtil.copyProperties(pictureReqDTO, picture);
        boolean b = this.updateById(picture);
        if (!b) {
            throw exception(UPDATE_FAIL);
        }
        return true;
    }

    /**
     * 删除图片
     *
     * @param id 图片id
     * @return 删除成功返回true
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public boolean deletePicture(Long id) {
        if (id == null) {
            throw exception(BAD_REQUEST);
        }
        boolean b = this.removeById(id);
        if (!b) {
            throw exception(DELETE_FAIL);
        }
        return true;
    }

    /**
     * 将图片对象转换为图片VO对象
     *
     * @param picture 图片对象
     * @return 返回图片VO对象
     */
    @Override
    public PictureSimpleVo getSimplePictureVO(Picture picture) {
        if (picture == null) {
            return null;
        }
        PictureSimpleVo pictureSimpleVo = new PictureSimpleVo();
        BeanUtil.copyProperties(picture, pictureSimpleVo);
        return pictureSimpleVo;
    }

    /**
     * 获取图片分页信息
     *
     * @param picturePageReqDTO 图片分页请求DTO
     * @return 返回图片分页结果
     */
    @Override
    public PageResult<PictureVo> getPicturePage(PicturePageReqDTO picturePageReqDTO) {
        PageResult<Picture> pageResult = pictureMapper.selectPage(picturePageReqDTO);
        if (pageResult.getList() == null) {
            return PageResult.empty();
        }
        List<PictureVo> pictureVos = pageResult.getList().stream().map(picture -> {
            PictureVo pictureVo = new PictureVo();
            BeanUtil.copyProperties(picture, pictureVo);
            return pictureVo;
        }).collect(Collectors.toList());
        return new PageResult<>(pictureVos, pageResult.getTotal());
    }

    @Override
    public PictureVo getPictureVO(Picture picture) {
        if (picture == null) {
            return null;
        }
        PictureVo pictureVo = new PictureVo();
        BeanUtil.copyProperties(picture, pictureVo);
        return pictureVo;
    }

}



