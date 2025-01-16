package cn.xzhang.boot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.xzhang.boot.common.exception.ServiceException;
import cn.xzhang.boot.common.pojo.PageResult;
import cn.xzhang.boot.constant.UserConstant;
import cn.xzhang.boot.manager.CosManager;
import cn.xzhang.boot.manager.FileManager;
import cn.xzhang.boot.mapper.PictureMapper;
import cn.xzhang.boot.model.dto.file.UploadPictureResult;
import cn.xzhang.boot.model.dto.picture.PicturePageReqDTO;
import cn.xzhang.boot.model.dto.picture.PictureSaveReqDTO;
import cn.xzhang.boot.model.dto.picture.PictureUploadReqDTO;
import cn.xzhang.boot.model.entity.Picture;
import cn.xzhang.boot.model.entity.User;
import cn.xzhang.boot.model.vo.picture.PictureSimpleVo;
import cn.xzhang.boot.model.vo.picture.PictureVo;
import cn.xzhang.boot.model.vo.user.UserVo;
import cn.xzhang.boot.service.PictureService;
import cn.xzhang.boot.service.UserService;
import cn.xzhang.boot.util.BeanUtils;
import cn.xzhang.boot.util.ThrowUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.xzhang.boot.common.exception.enums.GlobalErrorCodeConstants.*;
import static cn.xzhang.boot.common.exception.util.ServiceExceptionUtil.exception;
import static cn.xzhang.boot.model.enums.FileUploadBizEnum.PICTURE;

/**
 * 针对表【picture(图片表)】的数据库操作Service实现
 *
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private FileManager fileManager;

    @Resource
    private UserService userService;


    @Override
    public PictureVo  uploadPicture(MultipartFile multipartFile, PictureUploadReqDTO uploadReqDTO, User loginUser) {
        ThrowUtils.throwIf(loginUser == null, FORBIDDEN);
        // 用于判断是新增还是更新图片
        Long pictureId = null;
        if (Objects.nonNull(uploadReqDTO)) {
            pictureId = uploadReqDTO.getId();
        }
        // 如果是更新图片，需要校验图片是否存在
        if (Objects.nonNull(pictureId)) {
            boolean exists = this.lambdaQuery()
                    .eq(Picture::getId, pictureId)
                    .exists();
            ThrowUtils.throwIf(!exists, CUSTOMER_ERROR, "图片不存在");
        }
        // 上传图片，得到信息
        // 按照用户 id 划分目录
        String uploadPathPrefix = String.format(PICTURE.getValue() + "/%s", loginUser.getId());
        UploadPictureResult uploadPictureResult = fileManager.uploadPicture(multipartFile, uploadPathPrefix);
        // 构造要入库的图片信息
        Picture picture = BeanUtils.toBean(uploadPictureResult, Picture.class);
        picture.setName(uploadPictureResult.getPicName());
        picture.setCreator(loginUser.getId().toString());
        // 如果 pictureId 不为空，表示更新，否则是新增
        if (Objects.nonNull(pictureId)) {
            // 如果是更新，需要补充 id 和编辑时间
            picture.setId(pictureId);
            picture.setEditTime(new Date());

            // TODO 删除旧图片
            //cosManager.deleteObject();
        }
        boolean result = this.saveOrUpdate(picture);
        ThrowUtils.throwIf(!result, CUSTOMER_ERROR, "图片上传失败");
        PictureVo pictureVo = BeanUtils.toBean(picture, PictureVo.class);
        pictureVo.setUserVo(BeanUtils.toBean(loginUser, UserVo.class));
        return pictureVo;
    }

    /**
     * 添加新图片
     *
     * @param pictureReqDTO 图片信息请求DTO
     * @return 添加成功返回图片id
     */
    @Override
    public long addPicture(PictureSaveReqDTO pictureReqDTO) {
        Picture picture = BeanUtils.toBean(pictureReqDTO, Picture.class);
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
        // 判断下数据是否存在
        boolean exists = this.lambdaQuery()
                .eq(Picture::getId, pictureReqDTO.getId())
                .exists();
        if (!exists) {
            throw exception(CUSTOMER_ERROR, "图片不存在！");
        }
        Picture picture = BeanUtils.toBean(pictureReqDTO, Picture.class);
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
        // 判断下当前登录用户是否是管理员或者是本人 否则无权限删除
        User loginUser = userService.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        Picture picture = this.getById(id);
        if (picture == null) {
            throw exception(CUSTOMER_ERROR, "图片不存在！");
        }
        if (!UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole())
                &&
                !picture.getCreator().equals(loginUser.getId().toString())) {
            throw exception(FORBIDDEN);
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
        PictureSimpleVo pictureSimpleVo = BeanUtils.toBean(picture, PictureSimpleVo.class);
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
        List<Picture> list = pageResult.getList();
        // 获取所有用户id
        List<String> userIds = list.stream().map(Picture::getCreator).collect(Collectors.toList());
        List<User> userList = userService.lambdaQuery()
                .in(User::getId, userIds)
                .list();
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user));

        List<PictureVo> pictureVos = list.stream().map(picture -> {
            PictureVo pictureVo = BeanUtils.toBean(picture, PictureVo.class);
            long userId = Long.parseLong(picture.getCreator());
            if (userMap.containsKey(userId)) {
                pictureVo.setUserVo(BeanUtils.toBean(userMap.get(userId), UserVo.class));
            }
            return pictureVo;
        }).collect(Collectors.toList());
        return new PageResult<>(pictureVos, pageResult.getTotal());
    }

    @Override
    public PictureVo getPictureVO(Picture picture) {
        if (picture == null) {
            return null;
        }
        PictureVo pictureVo = BeanUtils.toBean(picture, PictureVo.class);
        pictureVo.setUserVo(BeanUtils.toBean(userService.getById(Long.parseLong(picture.getCreator())), UserVo.class));
        return pictureVo;
    }

    @Override
    public Boolean editPicture(PictureSaveReqDTO pictureReqDTO) {
        return null;
    }

}



