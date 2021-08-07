package xyz.esion.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Esion
 * @since 2021/6/20
 */
@SpringBootApplication
@MapperScan("xyz.esion.manage.mapper")
@EnableTransactionManagement
public class JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaApplication.class, args);
    }

}
