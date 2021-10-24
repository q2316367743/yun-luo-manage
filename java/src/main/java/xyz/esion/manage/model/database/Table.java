package xyz.esion.manage.model.database;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * MySQL数据库的表信息
 *
 * @author Esion
 * @since 2021/7/17
 */
@Data
public class Table {

    public static final String[] COLUMNS = new String[]{"Name", "Engine", "Version", "Row_format", "Rows",
            "Avg_row_length", "Data_length", "Max_data_length", "Index_length", "Data_free", "Auto_increment",
            "Create_time", "Update_time", "Check_time", "Collation", "Checksum", "Create_options", "Comment"
    };

    private String name;

    private String engine;

    private Integer version;

    private String rowFormat;

    private Integer rows;

    private Integer avgRowLength;

    private Integer dataLength;

    private Integer maxDataLength;

    private Integer indexLength;

    private Integer dataFree;

    private Integer autoIncrement;

    private Date createTime;

    private Date updateTime;

    private Date checkTime;

    private String collation;

    private String checkSum;

    private String createOptions;

    private String comment;

    /**
     * 这个表所包含的字段
     * */
    private List<Field> fields;

}
