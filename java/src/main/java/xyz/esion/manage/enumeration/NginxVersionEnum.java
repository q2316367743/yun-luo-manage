package xyz.esion.manage.enumeration;

import xyz.esion.manage.exception.ServerException;

/**
 * nginx版本
 *
 * @author Esion
 * @since 2021/7/31
 */
public enum NginxVersionEnum {

    // Nginx版本
    NGINX_0_5_38(1, "0.5.38"),
    NGINX_0_6_39(2, "0.6.39"),
    NGINX_0_7_69(3, "0.7.69"),
    NGINX_0_8_55(4, "0.8.55"),
    NGINX_1_0_15(5, "1.0.15"),
    NGINX_1_2_9(6, "1.2.9"),
    NGINX_1_4_7(7, "1.4.7"),
    NGINX_1_6_3(8, "1.6.3"),
    NGINX_1_10_3(9, "1.10.3"),
    NGINX_1_12_2(10, "1.12.2"),
    NGINX_1_14_2(11, "1.14.2"),
    NGINX_1_16_1(12, "1.16.1"),
    NGINX_1_18_0(13, "1.18.0"),
    NGINX_1_20_1(14, "1.20.1");

    private Integer value;

    private String version;

    NginxVersionEnum(Integer value, String version) {
        this.value = value;
        this.version = version;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static NginxVersionEnum getByValue(Integer value) throws ServerException {
        for (NginxVersionEnum item : values()) {
            if (item.getValue().equals(value)){
                return item;
            }
        }
        throw new ServerException("不受支持的版本类型：" + value);
    }

}
