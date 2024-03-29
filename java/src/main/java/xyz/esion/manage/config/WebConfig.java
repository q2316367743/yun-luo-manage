package xyz.esion.manage.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.sql.SqlExecutor;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Esion
 * @since 2021/7/15
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    /**
     * 当前环境
     * */
    @Value("${spring.profiles.active}")
    private String active;
    private final static String PROFILES = "dev";

    private DataSource dataSource;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 如果是dev环境增加跨域
        if (PROFILES.equals(active)){
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowCredentials(true)
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .maxAge(3600);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 权限拦截
        registry.addInterceptor(new SaAnnotationInterceptor())
                .addPathPatterns("/api/**").excludePathPatterns("/api/common/login");
    }

    /**
     * 配置MyBatis分页
     * */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 初始化时，检查数据库是否创建
     * */
    @PostConstruct
    public void init() throws SQLException {
        initDatabase();
    }

    public void initDatabase() throws SQLException {
        Connection connection = null;
        try {
            File f = new File("./manage.db");
            if (f.exists()) {
                log.debug("存在数据库");
                return;
            }
            // 执行建表命令
            connection = dataSource.getConnection();
            ArrayList<String> manage = IoUtil.readLines(this.getClass().getResourceAsStream("/manage.sql"), StandardCharsets.UTF_8, new ArrayList<>());
            List<String> sqls = new ArrayList<>();
            StringBuilder temp = new StringBuilder();
            for (String line : manage) {
                if (line.startsWith("'/")){
                    continue;
                }
                if (line.endsWith(";")){
                    temp.append(" ").append(line);
                    sqls.add(temp.toString());
                    temp.delete(0, temp.length());
                    continue;
                }
                temp.append(" ").append(line);
            }
            // 如果最后一行没有加分号
            if (temp.length() > 0){
                sqls.add(temp.toString());
            }
            for (String sql : sqls) {
                log.debug("sql：{}", sql);
                SqlExecutor.execute(connection, sql);
            }
            log.debug("数据库创建成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("建表发生错误");
        } finally {
            if (ObjectUtil.isNotNull(connection)) {
                connection.close();
            }
        }
    }

    public WebConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
