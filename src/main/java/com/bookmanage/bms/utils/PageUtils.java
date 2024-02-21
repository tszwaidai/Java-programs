package com.bookmanage.bms.utils;

import java.util.Map;


/**
 * 分页工具
 *
 * */
public class PageUtils {

    public static void parsePageParams(Map<String, Object> params) {
        int page = Integer.parseInt((String) params.get("page"));
        int size = Integer.parseInt((String) params.get("limit"));
        page = Math.max(page, 1);
        params.put("begin", (page - 1) * size);
        params.put("size", size);
    }

}
