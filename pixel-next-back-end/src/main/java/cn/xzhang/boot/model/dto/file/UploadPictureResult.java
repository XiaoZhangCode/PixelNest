package cn.xzhang.boot.model.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author code_zhang
 * @Date 2024/12/17 13:20
 * @Description 图片上传结果返回
 */
@Data
@Schema(description = "图片上传结果返回")
public class UploadPictureResult {

    @NotEmpty(message = "图片 url不能为空")
    @Schema(description = "图片 url")
    private String url;

    @NotEmpty(message = "图片名称不能为空")
    @Schema(description = "图片名称")
    private String picName;

    @NotNull(message = "图片体积不能为空")
    @Schema(description = "图片体积", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long picSize;

    @NotNull(message = "图片宽度不能为空")
    @Schema(description = "图片宽度", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picWidth;

    @NotNull(message = "图片高度不能为空")
    @Schema(description = "图片高度", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picHeight;

    @NotNull(message = "图片宽高比例不能为空")
    @Schema(description = "图片宽高比例", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double picScale;

    @NotNull(message = "图片格式不能为空")
    @Schema(description = "图片格式", requiredMode = Schema.RequiredMode.REQUIRED)
    private String picFormat;

}
