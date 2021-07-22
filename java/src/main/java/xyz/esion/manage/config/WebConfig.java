package xyz.esion.manage.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import xyz.esion.manage.global.Constant;
import xyz.esion.manage.global.Result;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private final static String OPTIONS = "OPTIONS";

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
        registry.addInterceptor(new HandlerInterceptor(){
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                if (OPTIONS.equals(request.getMethod())){
                    return true;
                }else {
                    try {
                        StpUtil.checkLogin();
                    }catch (NotLoginException exception){
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("text/json;charset=UTF-8 ");
                        if(exception.getType().equals(NotLoginException.NOT_TOKEN)) {
                            try {
                                response.getWriter().print(JSONUtil.toJsonStr(Result.fail(Result.ResultCode.UN_AUTHENTICATION)));
                                return false;
                            } catch (IOException ignored) {
                            }
                        }
                        else if(exception.getType().equals(NotLoginException.INVALID_TOKEN) || exception.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
                            try {
                                response.getWriter().print(JSONUtil.toJsonStr(Result.fail(Result.ResultCode.INVALID)));
                                return false;
                            } catch (IOException ignored) {
                            }
                        }
                    }
                }
                // 通过验证
                return true;
            }
        }).addPathPatterns("/test/api/**");
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
                if (line.startsWith("#")){
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
