package com.bookmanage.bms.web;

import com.bookmanage.bms.exception.NotEnoughException;
import com.bookmanage.bms.exception.OperationFailureException;
import com.bookmanage.bms.entity.Borrow;
import com.bookmanage.bms.service.BookInfoService;
import com.bookmanage.bms.service.BorrowService;
import com.bookmanage.bms.utils.Result;
import com.bookmanage.bms.utils.PageUtils;
import com.bookmanage.bms.entity.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/borrow")
public class BorrowController {

    @Autowired
    BorrowService borrowService;
    @Autowired
    BookInfoService bookInfoService;

    // 分页查询借阅信息 params: {page, limit, userid, bookid}
    @RequestMapping(value = "/queryBorrowsByPage")
    public Map<String, Object> queryBorrowsByPage(@RequestParam Map<String, Object> params){
        PageUtils.parsePageParams(params);
        int count = borrowService.getSearchCount(params);
        List<Borrow> borrows = borrowService.searchBorrowsByPage(params);
        return Result.getListResultMap(0, "success", count, borrows);
    }

    // 添加借阅
    @RequestMapping(value = "/addBorrow")
    public Integer addBorrow(@RequestBody Borrow borrow){
        return borrowService.addBorrow(borrow);
    }

    // 获得数量
    @RequestMapping(value = "/getCount")
    public Integer getCount(){
        return borrowService.getCount();
    }

    // 删除借阅
    @RequestMapping(value = "/deleteBorrow")
    public Integer deleteBorrow(@RequestBody Borrow borrow){
        return borrowService.deleteBorrow(borrow);
    }

    // 批量删除借阅
    @PostMapping(value = "/deleteBorrows")
    public Integer deleteBorrows(@RequestBody List<Borrow> borrows){
        return borrowService.deleteBorrows(borrows);
    }

    // 更新借阅
    @RequestMapping(value = "/updateBorrow")
    public Integer updateBorrow(@RequestBody Borrow borrow){
        return borrowService.updateBorrow(borrow);
    }

    // 借书操作 *
    @RequestMapping(value = {"/borrowBook", "/reader/borrowBook"})
    @Transactional
    public Integer borrowBook(Integer userid, Integer bookid){
        try{
            // 首先查询该书的情况
            BookInfo theBook = bookInfoService.queryBookInfoById(bookid);

            if(theBook == null) {  // 图书不存在
                throw new NullPointerException("图书" + bookid + "不存在");
            } else if(theBook.getIsborrowed() == 1) {  // 已经被借
                throw new NotEnoughException("图书" + bookid + "库存不足（已经被借走）");
            }

            // 添加一条记录到borrow表
            Borrow borrow = new Borrow();
            borrow.setUserid(userid);
            borrow.setBookid(bookid);
            borrow.setBorrowtime(new Date(System.currentTimeMillis())); //设置为当前时间为借阅时间
            Integer res1 = borrowService.addBorrow2(borrow);
            if(res1 == 0) throw new OperationFailureException("图书" + bookid + "添加借阅记录失败");

            // 更新图书表的isBorrowed
            BookInfo bookInfo = new BookInfo(); //创建要更新的图书信息对象
            bookInfo.setBookid(bookid); //传参 指定要更新的图书
            bookInfo.setIsborrowed((byte) 1); //进行标记 1表示已借出
            Integer res2 = bookInfoService.updateBookInfo(bookInfo); //传入 bookInfo 对象进行图书信息更新
            if(res2 == 0) throw new OperationFailureException("图书" + bookid + "更新被借信息失败"); //对操作失败的异常抛出



        } catch (Exception e) { //抛出异常
            System.out.println("发生异常，进行手动回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    // 还书
    @RequestMapping(value = {"/returnBook", "/reader/returnBook"})
    @Transactional
    public Integer returnBook(Integer borrowid, Integer bookid){
        try {
            // 查询该书的情况
            BookInfo theBook = bookInfoService.queryBookInfoById(bookid);
            // 查询借书的情况
            Borrow theBorrow = borrowService.queryBorrowsById(borrowid);

            if(theBook == null) {  // 图书不存在
                throw new NullPointerException("图书" + bookid + "不存在");
            } else if(theBorrow == null) {   //借阅记录不存在
                throw new NullPointerException("借书记录" + bookid + "不存在");
            } else if(theBorrow.getReturntime() != null) {  // 已经还过书
                throw new NotEnoughException("图书" + bookid + "已经还过了");
            }

            // 更新图书表的isBorrowed
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookid(bookid);
            bookInfo.setIsborrowed((byte) 0);
            Integer res2 = bookInfoService.updateBookInfo(bookInfo);
            if(res2 == 0) throw new OperationFailureException("图书" + bookid + "更新借阅信息失败");

            // 更新Borrow表，更新时间
            Borrow borrow = new Borrow();
            borrow.setBorrowid(borrowid);
            borrow.setReturntime(new Date(System.currentTimeMillis()));
            Integer res1 = borrowService.updateBorrow2(borrow);
            if(res1 == 0) throw new OperationFailureException("图书" + bookid + "更新借阅记录失败");

        } catch (Exception e) {
            System.out.println("发生异常，进行手动回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //将当前事务标记为回滚状态
            e.printStackTrace(); //打印异常信息
            return 0;
        }
        return 1; //操作成功
    }

}
