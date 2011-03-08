package user.spring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bee32.plover.restful.book.Book;

@Configuration
public class Configs {

    // static Book book10 = new Book("name10", "content10");
    static Book book10B = new Book("name10B", "content10");

    @Bean(name = "book10")
    @Qualifier("B")
    public Book book10B() {
        return book10B;
    }

}
