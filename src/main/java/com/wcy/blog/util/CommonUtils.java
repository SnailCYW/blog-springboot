package com.wcy.blog.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/13/22:01
 */
public class CommonUtils {

    /**
     * 检测邮箱是否合法
     * @param username 用户名
     * @return 合法状态
     */
    public static boolean checkEmail(String username) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher matcher = p.matcher(username);
        //进行正则匹配
        return matcher.matches();
    }

    /**
     * 获取括号内的内容
     * @param str
     * @return 括号内容
     */
    public static String getBracketsContent(String str) {
        return str.substring(str.indexOf("(")+1, str.indexOf(")"));
    }

    /**
     * 生成6位随机验证码
     * @return 验证码
     */
    public static String getRandomCode() {
        Random random = new Random();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 转换list
     * @param obj   obj
     * @param clazz clazz
     * @return  {@link List<T>}
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
        }
        return result;
    }

    /**
     * 转换set
     * @param obj   obj
     * @param clazz clazz
     * @return  {@link Set<T>}
     */
    public static <T> Set<T> castSet(Object obj, Class<T> clazz) {
        Set<T> result = new HashSet<T>();
        if (obj instanceof Set<?>) {
            for (Object o : (Set<?>) obj) {
                result.add(clazz.cast(o));
            }
        }
        return result;
    }
}
