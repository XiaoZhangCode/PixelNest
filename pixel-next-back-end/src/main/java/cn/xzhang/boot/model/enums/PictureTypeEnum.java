package cn.xzhang.boot.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片文件类型枚举
 *
 * @author <a href="https://github.com/XiaoZhangCode">XiaoZhangCode</a>
 */
@AllArgsConstructor
@Getter
public enum PictureTypeEnum {

    JPG("jpg", "jpg"),
    PNG("png", "png"),
    GIF("gif", "gif"),
    BMP("bmp", "bmp"),
    WEBP("webp", "webp");

    // 枚举实例的文本描述和对应的值
    private final String text;
    private final String value;

    /**
     * 获取所有枚举值的字符串表示列表。
     * @return 包含所有枚举实例值的字符串列表。
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据字符串值获取对应的枚举实例。
     * @param value 要查找的枚举实例的字符串值。
     * @return 如果找到匹配的枚举实例则返回之，否则返回null。
     */
    public static PictureTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (PictureTypeEnum anEnum : PictureTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
