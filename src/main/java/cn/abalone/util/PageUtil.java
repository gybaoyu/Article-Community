package cn.abalone.util;

import cn.abalone.entity.Article;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Abalone
 * CreateTime: 2021/1/29 17:24
 */

public class PageUtil<T> {

    public ThreeTuple<List,Integer,Integer> startPage(@NotNull List<T> list,
                                Integer pageNum,
                                Integer pageSize) {
        if (pageNum != 0 && pageSize != 0) {
            int count = list.size(); // 记录总数
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
            return new ThreeTuple<>(list.subList(fromIndex, toIndex),count,pageCount);//从a~b的数据
        } else throw new RuntimeException("分页页码不能为0,每页的数据量也不能设置为0!");
    }
}
