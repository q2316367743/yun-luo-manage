package xyz.esion.manage.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
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
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
     * 初始化时，检查数据库是否创建
     * */
    @PostConstruct
    public void init() throws SQLException {
        initDatabase();
        initSystem();
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

    public void initDatabase() throws SQLException {
        Connection connection = null;
        try {
            File f = new File("./disk.db");
            if (f.exists()) {
                log.debug("存在数据库");
                return;
            }
            // 执行建表命令
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
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

    public void initSystem(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            while (true){
                JSONObject item = new JSONObject();
                JSONObject cpu = new JSONObject();
                SystemInfo systemInfo = new SystemInfo();
                CentralProcessor processor = systemInfo.getHardware().getProcessor();
                long[] prevTicks = processor.getSystemCpuLoadTicks();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long[] ticks = processor.getSystemCpuLoadTicks();
                long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
                long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
                long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
                long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
                long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
                long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
                long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
                long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
                long totalCpu = user + nice + cSys + idle + iowait + irq + softIrq + steal;
                cpu.set("core", processor.getLogicalProcessorCount());
                cpu.set("system_use", new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
                cpu.set("user_use", new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
                cpu.set("curr_wait", new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));
                cpu.set("curr_use", new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu)));
                item.set("cpu", cpu);
                JSONObject member = new JSONObject();
                GlobalMemory memory = systemInfo.getHardware().getMemory();
                //总内存
                long totalByte = memory.getTotal();
                //剩余
                long availableByte = memory.getAvailable();
                member.set("all", formatByte(totalByte));
                member.set("use", formatByte(totalByte - availableByte));
                member.set("free", formatByte(availableByte));
                member.set("proportion", new DecimalFormat("#.##%").format((totalByte - availableByte) * 1.0 / totalByte));
                item.set("member", member);
                JSONObject system = new JSONObject();
                Properties props = System.getProperties();
                system.set("name", props.getProperty("os.name"));
                system.set("arch", props.getProperty("os.arch"));
                item.set("system", system);
                List<JSONObject> disk = new ArrayList<>();
                File[] files = File.listRoots();
                for (File file : files) {
                    String total = new DecimalFormat("#.#").format(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024)
                            + "G";
                    String free = new DecimalFormat("#.#").format(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String un = new DecimalFormat("#.#").format((file.getTotalSpace() - file.getFreeSpace()) * 1.0 / 1024 / 1024 / 1024) + "G";
                    String path = file.getPath();
                    disk.add(new JSONObject().set("path", path).set("all", total).set("use", un).set("free", free));
                }
                item.set("disk", disk);
                Constant.system = item;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    private String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

    public WebConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
