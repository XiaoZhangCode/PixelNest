package cn.xzhang.boot.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.xzhang.boot.common.pojo.CommonResult;
import cn.xzhang.boot.common.pojo.PageResult;
import cn.xzhang.boot.constant.UserConstant;
import cn.xzhang.boot.model.dto.picture.PicturePageReqDTO;
import cn.xzhang.boot.model.dto.picture.PictureSaveReqDTO;
import cn.xzhang.boot.model.entity.Picture;
import cn.xzhang.boot.model.vo.picture.PictureSimpleVo;
import cn.xzhang.boot.model.vo.picture.PictureVo;
import cn.xzhang.boot.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.xzhang.boot.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST_PARAMS;

/**
 * 图片管理
 *
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
@Tag(name = "管理后台 - 图片管理")
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private PictureService pictureService;

    /**
     * 创建picture
     *
     * @param pictureReqDTO picture添加请求数据传输对象，包含新增Picture的信息
     * @return 返回操作结果，其中包含新添加picture的ID
     */
    @PostMapping("/add")
    @Operation(summary = "创建图片")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public CommonResult<Long> addPicture(@RequestBody PictureSaveReqDTO pictureReqDTO) {
        if (pictureReqDTO == null) {
            return CommonResult.error(BAD_REQUEST_PARAMS);
        }
        // 调用服务层方法，添加，并获取添加结果
        long result = pictureService.addPicture(pictureReqDTO);
        // 返回添加成功响应结果
        return CommonResult.success(result);
    }

    @PutMapping("/update")
    @Operation(summary = "更新图片信息")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public CommonResult<Boolean> updatePicture(@RequestBody @Valid PictureSaveReqDTO pictureReqDTO) {
        // 检查传入的请求数据是否为空
        if (pictureReqDTO == null) {
            return CommonResult.error(BAD_REQUEST_PARAMS);
        }
        // 调用服务层方法，更新信息，并获取更新结果
        boolean result = pictureService.updatePicture(pictureReqDTO);
        // 返回更新信息成功响应结果
        return CommonResult.success(result);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除图片")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    @Parameter(name = "id", description = "图片ID",required = true)
    public CommonResult<Boolean> deletePicture(@RequestParam("id") Long id) {
        // 检查传入的ID是否为空
        if (id == null) {
            return CommonResult.error(BAD_REQUEST_PARAMS);
        }
        // 调用服务层方法，删除
        boolean result = pictureService.deletePicture(id);
        // 返回删除成功响应结果
        return CommonResult.success(result);
    }

    @GetMapping("/get")
    @Operation(summary = "获取图片")
    @Parameter(name = "id", description = "图片ID",required = true)
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public CommonResult<PictureVo> getPicture(@RequestParam("id") Long id) {
        // 检查传入的ID是否为空
        if (id == null) {
            return CommonResult.error(BAD_REQUEST_PARAMS);
        }
        // 调用服务层方法，获取信息，并返回结果
        return CommonResult.success(pictureService.getPictureVO(pictureService.getById(id)));
    }

    @GetMapping("/get/vo")
    @Operation(summary = "获取图片简要信息")
    @Parameter(name = "id", description = "图片ID",required = true)
    public CommonResult<PictureSimpleVo> getPictureVO(@RequestParam("id") Long id) {
        // 检查传入的ID是否为空
        if (id == null) {
            return CommonResult.error(BAD_REQUEST_PARAMS);
        }
        Picture picture = pictureService.getById(id);
        // 调用服务层方法，获取信息，并返回结果
        return CommonResult.success(pictureService.getSimplePictureVO(picture));
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取图片列表")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public CommonResult<PageResult<PictureVo>> getPicturePage(PicturePageReqDTO picturePageReqDTO) {
        // 调用服务层方法，获取分页信息，并返回结果
        return CommonResult.success(pictureService.getPicturePage(picturePageReqDTO));
    }

}