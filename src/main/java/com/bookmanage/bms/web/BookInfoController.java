package com.bookmanage.bms.web;

import com.bookmanage.bms.service.BookInfoService;
import com.bookmanage.bms.utils.Result;
import com.bookmanage.bms.utils.PageUtils;
import com.bookmanage.bms.entity.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bookInfo")
public class BookInfoController {

    @Autowired
    BookInfoService bookInfoService;

    // 获取图书数量
    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return bookInfoService.getCount();
    }

    // 查询所有图书信息
    @GetMapping(value = "/queryBookInfos")
    public List<BookInfo> queryBookInfos(){
        return bookInfoService.queryBookInfos();
    }

    // 分页搜索查询图书信息
    // params: {page, limit, bookname, bookauthor, booktypeid}
    @GetMapping(value = "/queryBookInfosByPage")
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params){ //@RequestParam 注解用于从请求参数中获取参数值，并将参数封装为一个 Map
        PageUtils.parsePageParams(params);
        int count = bookInfoService.getSearchCount(params);  //获取搜索条件下的总数
        List<BookInfo> bookInfos = bookInfoService.searchBookInfosByPage(params);  // 分页查询
        return Result.getListResultMap(0, "success", count, bookInfos);
    }

    // 添加图书信息
    @PostMapping(value = "/addBookInfo")
    public Integer addBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.addBookInfo(bookInfo);
    }

    // 删除图书信息
    @DeleteMapping(value = "/deleteBookInfo")
    public Integer deleteBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.deleteBookInfo(bookInfo);
    }

    // 批量删除图书
    //出现错误
    @PostMapping(value = "/deleteBookInfos")
    public Integer deleteBookInfos(@RequestBody List<BookInfo> bookInfos){
        return bookInfoService.deleteBookInfos(bookInfos);
    }

    // 更新图书信息
    @PutMapping(value = "/updateBookInfo")
    public Integer updateBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.updateBookInfo(bookInfo);
    }
}
