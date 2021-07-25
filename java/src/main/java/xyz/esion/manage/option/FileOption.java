package xyz.esion.manage.option;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * 文件参数
 *
 * @author Esion
 * @since 2021/7/24
 */
@Data
public class FileOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private String path;

    private List<String> paths;

    private String name;

    private String target;

    private String content;

    /**
     * 是否强制
     * */
    private Boolean isForce = false;

    /**
     * 编码
     * */
    private String charset;

    /**
     * 文件上传的文件
     * */
    private MultipartFile file;

}
