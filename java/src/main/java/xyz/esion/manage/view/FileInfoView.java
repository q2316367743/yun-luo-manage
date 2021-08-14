package xyz.esion.manage.view;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.esion.manage.util.FileUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @author Esion
 * @since 2021/8/11
 */
@Data
public class FileInfoView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String size;

    private String group;

    private String user;

    /**
     * 访问权限 - 字符串
     */
    private String accessString;

    /**
     * 访问权限 - 数字
     */
    private String accessValue;

    private Access access;

    private String accessTime;

    private String modifyTime;

    private String changeTime;

    private String birth;

    @Data
    public static class Access implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 拥有者
         */
        private Permission owner;

        /**
         * 群组
         */
        private Permission group;

        /**
         * 其它组
         */
        private Permission other;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Permission implements Serializable {

        private static final long serialVersionUID = 1L;

        private Boolean read;

        private Boolean write;

        private Boolean execute;

    }

    /**
     * 根据list解析文件/夹详情
     *
     * @param info 文件/夹详情list
     * @return 文件/夹详情
     */
    public static FileInfoView parse(String info){
        String[] items = info.split("\\|");
        FileInfoView view = new FileInfoView();
        view.setName(items[1]);
        view.setSize(FileUtil.beautify(items[2]));
        view.setGroup(items[3]);
        view.setUser(items[4]);
        view.setAccessValue(items[5]);
        view.setAccessString(items[6]);
        view.setBirth(DateUtil.date(Long.parseLong(items[7]) * 1000).toString("yyyy-MM-dd hh:mm:ss"));
        view.setAccessTime(DateUtil.date(Long.parseLong(items[8]) * 1000).toString("yyyy-MM-dd hh:mm:ss"));
        view.setModifyTime(DateUtil.date(Long.parseLong(items[9]) * 1000).toString("yyyy-MM-dd hh:mm:ss"));
        view.setChangeTime(DateUtil.date(Long.parseLong(items[10]) * 1000).toString("yyyy-MM-dd hh:mm:ss"));
        char[] accessChars = view.getAccessString().toCharArray();
        Access access = new Access();
        access.setOwner(Permission.builder()
                .read(accessChars[1] == 'r')
                .write(accessChars[2] == 'w')
                .execute(accessChars[3] == 'x')
                .build());
        access.setGroup(Permission.builder()
                .read(accessChars[4] == 'r')
                .write(accessChars[5] == 'w')
                .execute(accessChars[6] == 'x')
                .build());
        access.setOther(Permission.builder()
                .read(accessChars[7] == 'r')
                .write(accessChars[8] == 'w')
                .execute(accessChars[9] == 'x')
                .build());
        view.setAccess(access);
        return view;
    }

}
