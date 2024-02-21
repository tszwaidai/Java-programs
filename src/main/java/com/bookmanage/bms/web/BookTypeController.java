package com.bookmanage.bms.web;

import com.bookmanage.bms.entity.BookType;
import com.bookmanage.bms.service.BookTypeService;
import com.bookmanage.bms.utils.Result;
import com.bookmanage.bms.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bookType")
public class BookTypeController {

    @Autowired
    BookTypeService bookTypeService;

    // 获得数量
    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return bookTypeService.getCount();
    }

    // 查询所有类型
    @GetMapping(value = {"/queryBookTypes", "/reader/queryBookTypes"})
    public List<BookType> queryBookTypes(){
        return bookTypeService.queryBookTypes();
    }

    // 分页查询图书类型 params: {page, limit, booktypename}
    @GetMapping(value = "/queryBookTypesByPage")
    public Map<String, Object> queryBookTypesByPage(@RequestParam Map<String, Object> params){
        PageUtils.parsePageParams(params);
        int count = bookTypeService.getSearchCount(params);
        List<BookType> bookTypes = bookTypeService.searchBookTypesByPage(params);
        return Result.getListResultMap(0, "success", count, bookTypes); //查询结果封装成响应的格式并返回
    }

    // 添加类型
    @PostMapping(value = "/addBookType")
    public Integer addBookType(@RequestBody BookType bookType){
        return bookTypeService.addBookType(bookType);
    }

    // 删除类型
    @DeleteMapping(value = "/deleteBookType")
    public Integer deleteBookType(@RequestBody BookType bookType){
        return bookTypeService.deleteBookType(bookType);
    }

    // 批量删除类型
    @PostMapping(value = "/deleteBookTypes")
    public Integer deleteBookTypes(@RequestBody List<BookType> bookTypes){
        return bookTypeService.deleteBookTypes(bookTypes);
    }

    // 更新类型
    @PutMapping(value = "/updateBookType")
    public Integer updateBookType(@RequestBody BookType bookType){
        return bookTypeService.updateBookType(bookType);
    }
}
