package user.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.restful.book.Book;

public class FooBean
        extends ComponentBase {

    @Autowired
    Book book1;

    public FooBean() {
    }

    public Book getBook1() {
        return book1;
    }

    public void setBook1(Book book1) {
        this.book1 = book1;
    }

    BigBlob blob;

    public BigBlob getBigBlob() {
        return blob;
    }

    @Autowired
    @Lazy
    public void setBigBlob(BigBlob blob) {
        this.blob = blob;
    }

}
