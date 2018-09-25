package com.hx.service;

import com.hx.Repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author yxqiang
 * @create 2018-09-23 21:58
 */

@Service
public class BookService {
//    @Qualifier("bookDao2")  //明确指定组件的id = bookDao
    @Autowired(required = false) //required = false 表示能装配上，就装配，否则为空时，要报错
    private BookDao bookDao2;

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao2=" + bookDao2 +
                '}';
    }
}
