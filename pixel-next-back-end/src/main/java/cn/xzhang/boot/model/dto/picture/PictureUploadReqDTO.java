package cn.xzhang.boot.model.dto.picture;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * 图片添加请求
 *
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
@Data
@Schema(description = "图片创建/更新请求")
public class PictureUploadReqDTO {

    @Schema(description = "id")
    private Long id;

}
