package user.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bee32.plover.restful.book.Book;

@Configuration
public class Configs
        implements InitializingBean {

    static Book book10 = new Book("name10", "content10");
    static Book book10B = new Book("name10B", "content10B");
    static Book book333 = new Book("name333", "content333");

    @Bean(name = "book10")
    @Version("B")
    public Book book10B() {
        return book10B;
    }

    @Bean(name = "book10")
    public Book book10() {
        return book10;
    }

    @Bean
    @Version("B")
    public Book book333() {
        return book10;
    }

    @Bean(name = "magic")
    public Book bookMagic(ApplicationContext context) {
        String contextStr = String.valueOf(context);
        Book magic = new Book("magic", contextStr);
        return magic;
    }

    @Override
    public void afterPropertiesSet() {
    }

}
