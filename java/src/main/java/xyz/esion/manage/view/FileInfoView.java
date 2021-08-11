package xyz.esion.manage.view;

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

    private String blocks;

    private String ioBlock;

    private String type;

    private String device;

    private String inode;

    private String links;

    /**
     * 访问权限 - 字符串
     */
    private String accessString;

    /**
     * 访问权限 - 数字
     */
    private String accessValue;

    private Access access;

    private String uid;

    private String gid;

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
    public static FileInfoView parse(List<String> info){
        FileInfoView view = new FileInfoView();
        view.setName(info.get(0).split(":")[1].trim());
        String one = info.get(1);
        int sizeIndex = one.indexOf("Size: ");
        int blockIndex = one.indexOf("Blocks: ");
        int ioBlockIndex = one.indexOf("IO Block: ");
        int typeIndex = one.lastIndexOf(" ", ioBlockIndex + 14);
        String sizeString = one.substring(sizeIndex, blockIndex).substring(6).trim();
        view.setSize(FileUtil.beautify(sizeString));
        view.setBlocks(one.substring(blockIndex, ioBlockIndex).substring(8).trim());
        view.setIoBlock(one.substring(ioBlockIndex, typeIndex).substring(10).trim());
        view.setType(one.substring(typeIndex).trim());
        String two = info.get(2);
        int deviceIndex = two.indexOf("Device: ");
        int inodeIndex = two.indexOf("Inode: ");
        int linksIndex = two.indexOf("Links: ");
        view.setDevice(two.substring(deviceIndex, inodeIndex).substring(8).trim());
        view.setInode(two.substring(inodeIndex, linksIndex).substring(7).trim());
        view.setLinks(two.substring(linksIndex).substring(7).trim());
        String three = info.get(3);
        int accessIndex = three.indexOf("Access: ");
        int uidIndex = three.indexOf("Uid: ");
        int gidIndex = three.indexOf("Gid: ");
        String accessString = three.substring(accessIndex, uidIndex).substring(8).trim();
        view.setAccessString(accessString.substring(6, 16));
        view.setAccessValue(accessString.substring(1, 5));
        char[] accessChars = accessString.substring(6, 16).toCharArray();
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
        view.setUid(three.substring(uidIndex, gidIndex).substring(5).trim());
        view.setGid(three.substring(gidIndex).substring(5).trim());
        view.setAccessTime(info.get(4).substring(8).trim().substring(0, 19));
        view.setModifyTime(info.get(5).substring(8).trim().substring(0, 19));
        view.setChangeTime(info.get(6).substring(8).trim().substring(0, 19));
        view.setBirth(info.get(7).substring(8).trim());
        return view;
    }

}
