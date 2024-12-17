package cn.xzhang.boot.model.vo.picture;

import cn.xzhang.boot.model.entity.Picture;
import cn.xzhang.boot.model.vo.user.UserVo;
import cn.xzhang.boot.util.BeanUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片VO
 *
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
@Data
@Schema(description = "图片VO")
public class PictureVo implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "图片 url")
    private String url;

    @Schema(description = "图片名称")
    private String name;

    @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
    private String introduction;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @Schema(description = "标签（JSON 数组）", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> tags;

    @Schema(description = "图片体积", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long picSize;

    @Schema(description = "图片宽度", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picWidth;

    @Schema(description = "图片高度", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picHeight;

    @Schema(description = "图片宽高比例", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double picScale;

    @Schema(description = "图片格式", requiredMode = Schema.RequiredMode.REQUIRED)
    private String picFormat;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "编辑时间")
    private java.util.Date editTime;

    @Schema(description = "上传用户的信息")
    private UserVo userVo;


}
