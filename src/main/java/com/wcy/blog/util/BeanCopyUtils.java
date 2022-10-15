package com.wcy.blog.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/9:13
 */

public class BeanCopyUtils {

    /**
     * 类属性复制
     *
     * @param source 源实例
     * @param target 目标类
     * @return {@link T} 目标实例
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T result = null;
        try {
            result = target.newInstance();
            if (null != source) {
                BeanUtils.copyProperties(source, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 以目标类为元素拷贝集合
     *
     * @param sourceList 源集合
     * @param clazz 目标类
     * @return {@link List<T>} 集合
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (null != sourceList && sourceList.size() > 0) {
            for (Object obj : sourceList) {
                result.add(copyObject(obj, clazz));
            }
        }
        return result;
    }


}
