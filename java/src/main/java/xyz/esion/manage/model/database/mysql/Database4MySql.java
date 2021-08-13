package xyz.esion.manage.model.database.mysql;

import lombok.Data;

import java.util.List;

/**
 * @author Esion
 * @since 2021/7/17
 */
@Data
public class Database4MySql {

    private String name;

    /**
     * 表
     * */
    private List<Table4MySql> tables;

    private List<View4MySql> views;

}
