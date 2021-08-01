package xyz.esion.manage.enumeration;

import xyz.esion.manage.exception.BasicException;
import xyz.esion.manage.exception.ServerException;

/**
 * 服务器类型
 *
 * @author Esion
 * @since 2021/7/31
 */
public enum ServerTypeEnum {

    // 用户定义类型
    DEFAULT(0, ""),
    // nginx
    NGINX(1, "Nginx"),
    // apache
    APACHE(2, "Apache"),
    // tomcat
    TOMCAT(3, "Tomcat");

    private Integer value;

    private String name;

    ServerTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ServerTypeEnum getByValue(Integer value) throws ServerException {
        for (ServerTypeEnum item : values()) {
            if (item.getValue().equals(value)){
                return item;
            }
        }
        throw new ServerException("不受支持的服务器类型：" + value);
    }

}
