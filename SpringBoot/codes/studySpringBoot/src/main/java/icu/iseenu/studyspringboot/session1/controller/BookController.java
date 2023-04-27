package icu.iseenu.studyspringboot.session1.controller;

import icu.iseenu.studyspringboot.session1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    public void print(){
        System.out.println(bookService);
    }
}
