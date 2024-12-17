package cn.xzhang.boot.model.entity;

import cn.xzhang.boot.common.pojo.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
* 图片
* @TableName picture
* @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
*/
@TableName(value ="picture")
@Data
@EqualsAndHashCode(callSuper = true)
public class Picture extends BaseDO implements Serializable {


    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private Long id;

    @Schema(description = "图片 url")
    private String url;

    @Schema(description = "图片名称")
    private String name;

    @Schema(description = "简介",requiredMode = Schema.RequiredMode.REQUIRED)
    private String introduction;

    @Schema(description = "分类",requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @Schema(description = "标签（JSON 数组）",requiredMode = Schema.RequiredMode.REQUIRED)
    private String tags;

    @Schema(description = "图片体积",requiredMode = Schema.RequiredMode.REQUIRED)
    private Long picSize;

    @Schema(description = "图片宽度",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picWidth;

    @Schema(description = "图片高度",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer picHeight;

    @Schema(description = "图片宽高比例",requiredMode = Schema.RequiredMode.REQUIRED)
    private Double picScale;

    @Schema(description = "图片格式",requiredMode = Schema.RequiredMode.REQUIRED)
    private String picFormat;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "编辑时间")
    private java.util.Date editTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}