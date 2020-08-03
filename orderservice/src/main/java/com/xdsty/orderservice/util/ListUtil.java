package com.xdsty.orderservice.util;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/10 13:49
 */
public final class ListUtil {

    /**
     * 判断两个list是否相等
     *
     * @param list1 1
     * @param list2 2
     * @param <T>
     * @return boolean
     */
    public static <T> boolean equals(List<T> list1, List<T> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        return list1.containsAll(list2) && list2.containsAll(list1);
    }

}
