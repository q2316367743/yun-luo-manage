package xyz.esion.manage.service;

import xyz.esion.manage.exception.ServerException;
import xyz.esion.manage.option.ServerOption;
import xyz.esion.manage.option.ServerCommandOption;
import xyz.esion.manage.option.ServerConfigOption;
import xyz.esion.manage.view.ServerInfoView;
import xyz.esion.manage.view.ServerListView;

import java.util.List;

/**
 * 服务器接口
 * @author Esion
 * @since 2021/7/31
 */
public interface ServerService {

    /**
     * 这里就不进行分页了
     *
     * @return 全部的服务器
     */
    List<ServerListView> list();

    /**
     * 通过ID获取服务器信息
     *
     * @param id 服务器ID
     * @return 服务器信息
     */
    ServerInfoView infoById(String id);

    /**
     * 安装服务器
     *
     * @param serverType 服务器类型
     * @param serverVersion 服务器版本
     * @param installType 安装方式
     * @return 任务ID
     */
    Integer install(Integer serverType, Integer serverVersion, Integer installType);

    /**
     * 增加一个服务器
     *
     * @param option 数据
     * @throws ServerException 服务器类型错误
     */
    void add(ServerOption option) throws ServerException;

    /**
     * 服务器信息更新
     *
     * @param id 服务器ID
     * @param option 服务器信息
     * @throws ServerException 服务器类型错误
     */
    void update(String id, ServerOption option) throws ServerException;

    /**
     * 服务器删除
     *
     * @param id 服务器ID
     */
    void remove(String id);

    /**
     * 修改命令
     *
     * @param id 命令ID
     * @param option 命令内容
     */
    void commandUpdate(String id, ServerCommandOption option);

    /**
     * 删除命令
     *
     * @param id 命令ID
     */
    void commandRemove(String id);

    /**
     * 配置更新
     *
     * @param id 配置ID
     * @param option 配置内容
     */
    void configUpdate(String id, ServerConfigOption option);

    /**
     * 配置删除
     *
     * @param id 配置ID
     */
    void configRemove(String id);

    /**
     * 获取配置信息
     *
     * @param id 配置ID
     * @param charset 解码方式
     * @return 配置信息
     * @throws ServerException 配置文件不存在
     */
    String getConfigById(String id, String charset) throws ServerException;

    /**
     * 根据命令ID执行命令
     *
     * @param id 命令ID
     * @return 执行结果
     * @throws ServerException 命令不存在
     */
    String execCommand(String id) throws ServerException;

}