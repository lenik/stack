package user.spring;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.restful.book.Book;

public class FooBean
        extends ComponentBase {

    @Inject
    Book book333;

    public FooBean() {
    }

    public Book getBook() {
        return book333;
    }

    public void setBook(Book book) {
        this.book333 = book;
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
