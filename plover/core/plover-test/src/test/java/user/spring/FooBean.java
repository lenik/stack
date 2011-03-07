package user.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bee32.plover.restful.book.Book;

@Component
public class FooBean {

    @Autowired
    Book book1;

    public Book getBook1() {
        return book1;
    }

    public void setBook1(Book book1) {
        this.book1 = book1;
    }

}
