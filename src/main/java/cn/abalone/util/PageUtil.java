package cn.abalone.util;

import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2021/1/29 17:24
 */

public class PageUtil<T> {
    public List<T> startPage(List<T> list, Integer pageNum,
                                     Integer pageSize) {
        if (list.size() == 0) {
            return null;
        }
        Integer count = list.size(); // 记录总数
        int pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引
        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        return list.subList(fromIndex, toIndex);
    }
}
