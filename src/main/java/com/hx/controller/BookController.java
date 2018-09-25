package com.hx.controller;

import com.hx.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author yxqiang
 * @create 2018-09-23 21:57
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
}
