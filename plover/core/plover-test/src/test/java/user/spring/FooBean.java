package user.spring;

import javax.inject.Inject;

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

    @Inject
    public void setBigBlob(BigBlob blob) {
        this.blob = blob;
    }

}
