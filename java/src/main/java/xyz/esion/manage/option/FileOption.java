package xyz.esion.manage.option;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import xyz.esion.manage.exception.FileException;

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
     *
     * */
    private String url;

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
    private List<MultipartFile> files;

    public void verify() throws FileException {
        // 目录不能是，目录也不能包含
        if (StrUtil.isNotBlank(this.path) && this.path.contains(SystemUtil.getUserInfo().getCurrentDir())) {
            throw new FileException("目录不能是也不能包含项目所在目录");
        }
        if (this.paths != null && !this.paths.isEmpty()){
            for (String path : paths) {
                if (path.contains(SystemUtil.getUserInfo().getCurrentDir())) {
                    throw new FileException("目录不能是也不能包含项目所在目录");
                }
            }
        }
    }

}
