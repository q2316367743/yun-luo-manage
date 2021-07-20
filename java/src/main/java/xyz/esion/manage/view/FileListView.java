package xyz.esion.manage.view;

import lombok.Data;
import xyz.esion.manage.enumeration.FileTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * @author Esion
 * @since 2021/7/20
 */
@Data
public class FileListView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private FileTypeEnum type;

}
