package cn.xzhang.boot.service;

import cn.xzhang.boot.common.pojo.PageResult;
import cn.xzhang.boot.model.dto.file.UploadPictureResult;
import cn.xzhang.boot.model.dto.picture.PicturePageReqDTO;
import cn.xzhang.boot.model.dto.picture.PictureSaveReqDTO;
import cn.xzhang.boot.model.dto.picture.PictureUploadReqDTO;
import cn.xzhang.boot.model.entity.Picture;
import cn.xzhang.boot.model.entity.User;
import cn.xzhang.boot.model.vo.picture.PictureSimpleVo;
import cn.xzhang.boot.model.vo.picture.PictureVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 * @description 针对表【picture(图片表)】的数据库操作Service
 */
public interface PictureService extends IService<Picture> {


    /**
     * 上传图片
     *
     * @param multipartFile 文件
     * @param uploadReqDTO  上传参数
     * @param loginUser     登录用户
     * @return 上传结果
     */
    PictureVo uploadPicture(MultipartFile multipartFile, PictureUploadReqDTO uploadReqDTO, User loginUser);


    /**
     * 添加图片信息。
     *
     * @param pictureReqDTO 图片添加请求数据传输对象，包含要添加的图片的所有必要信息。
     * @return 返回添加操作的自增ID，用于标识此次添加操作。
     */
    long addPicture(PictureSaveReqDTO pictureReqDTO);

    /**
     * 更新图片信息。
     *
     * @param pictureReqDTO 包含图片更新信息的请求DTO（数据传输对象）。该对象应包含需要更新的图片属性。
     * @return boolean 返回true如果图片信息更新成功，返回false如果更新失败或遇到错误。
     */
    boolean updatePicture(PictureSaveReqDTO pictureReqDTO);


    /**
     * 删除图片
     *
     * @param id 图片的唯一标识符
     * @return boolean 返回操作是否成功。true表示删除成功，false表示删除失败。
     */
    boolean deletePicture(Long id);

    /**
     * 根据Picture对象获取对应的PictureVo对象。
     *
     * @param picture 一个包含图片信息的Picture对象。
     * @return 返回一个包含图片信息的PictureVo对象。
     */
    PictureSimpleVo getSimplePictureVO(Picture picture);

    /**
     * 获取图片页面信息
     *
     * @param picturePageReqDTO 包含分页和筛选条件的图片请求数据传输对象
     * @return 返回图片页面的结果，包括图片列表和分页信息
     */
    PageResult<PictureVo> getPicturePage(PicturePageReqDTO picturePageReqDTO);

    /**
     * 根据Picture对象获取对应的PictureVo对象。
     *
     * @param picture 一个包含图片信息的Picture对象。
     * @return 返回一个包含图片信息的PictureVo对象。
     */
    PictureVo getPictureVO(Picture picture);


    Boolean editPicture(PictureSaveReqDTO pictureReqDTO);
}
