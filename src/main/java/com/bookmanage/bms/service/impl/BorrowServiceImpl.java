package com.bookmanage.bms.service.impl;

import com.bookmanage.bms.mapper.BorrowMapper;
import com.bookmanage.bms.entity.Borrow;
import com.bookmanage.bms.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public Integer getCount() {
        return borrowMapper.selectCount();
    }

    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return borrowMapper.selectCountBySearch(params);
    }

    @Override
    public List<Borrow> searchBorrowsByPage(Map<String, Object> params) {
        List<Borrow> borrows = borrowMapper.selectBySearch(params);
        // 添加string类型的时间显示
        for(Borrow borrow : borrows) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(borrow.getBorrowtime() != null) borrow.setBorrowtimestr(simpleDateFormat.format(borrow.getBorrowtime()));
            if(borrow.getReturntime() != null) borrow.setReturntimestr(simpleDateFormat.format(borrow.getReturntime()));
        }
        return borrows;
    }

    //将前端传递过来的字符串类型的借阅时间和归还时间转换为 Date 类型 后端处理插入到数据库
    @Override
    public Integer addBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowtime(simpleDateFormat.parse(borrow.getBorrowtimestr())); //借阅记录对象中的字符串类型的借阅时间和归还时间转换为 Date 类型
            borrow.setReturntime(simpleDateFormat.parse(borrow.getReturntimestr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowMapper.insertSelective(borrow);
    }

    // 不会调整时间格式的添加借阅
    @Override
    public Integer addBorrow2(Borrow borrow) {
        return borrowMapper.insertSelective(borrow); //直接插入到数据库
    }

    @Override
    public Integer deleteBorrow(Borrow borrow) {
        // 先查询有没有还书
        Borrow borrow1 = borrowMapper.selectByPrimaryKey(borrow.getBorrowid());
        if(borrow1.getReturntime() == null) return 0;
        return borrowMapper.deleteByPrimaryKey(borrow.getBorrowid());
    }

    @Override
    public Integer deleteBorrows(List<Borrow> borrows) {
        int count = 0;
        for(Borrow borrow : borrows) {
            count += deleteBorrow(borrow);
        }
        return count;
    }

    @Override
    public Integer updateBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowtime(simpleDateFormat.parse(borrow.getBorrowtimestr()));
            borrow.setReturntime(simpleDateFormat.parse(borrow.getReturntimestr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowMapper.updateByPrimaryKeySelective(borrow);
    }

    // 不调整时间格式的更新
    @Override
    public Integer updateBorrow2(Borrow borrow) {
        return borrowMapper.updateByPrimaryKeySelective(borrow);
    }

    @Override
    public Borrow queryBorrowsById(Integer borrowid) {
        return borrowMapper.selectByPrimaryKey(borrowid);
    }

}
