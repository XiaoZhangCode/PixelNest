package cn.xzhang.boot.model.dto.picture;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * 图片添加请求
 *
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
@Data
@Schema(description = "图片创建/更新请求")
@Valid
public class PictureSaveReqDTO {

    @Schema(description = "id" )
    @NotBlank(message = "id不能为空")
    private Long id;

    @NotEmpty(message = "图片 url不能为空")
    @Schema(description = "图片 url")
    private String url;

    @NotEmpty(message = "图片名称不能为空")
    @Schema(description = "图片名称")
    private String name;

    @NotNull(message = "简介不能为空")
    @Schema(description = "简介",requiredMode = Schema.RequiredMode.REQUIRED)
    private String introduction;

    @NotNull(message = "分类不能为空")
    @Schema(description = "分类",requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @NotNull(message = "标签（JSON 数组）不能为空")
    @Schema(description = "标签（JSON 数组）",requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> tags;

    @NotNull(message = "图片体积不能为空")
    @Schema(description = "图片体积",requiredMode = Schema.RequiredMode.REQUIRED)
    private Long picSize;

    @NotNull(message = "图片宽度不能为空")
    @Schema(description = "图片宽度",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picWidth;

    @NotNull(message = "图片高度不能为空")
    @Schema(description = "图片高度",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picHeight;

    @NotNull(message = "图片宽高比例不能为空")
    @Schema(description = "图片宽高比例",requiredMode = Schema.RequiredMode.REQUIRED)
    private Double picScale;

    @NotNull(message = "图片格式不能为空")
    @Schema(description = "图片格式",requiredMode = Schema.RequiredMode.REQUIRED)
    private String picFormat;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "编辑时间")
    private Date editTime;

}
