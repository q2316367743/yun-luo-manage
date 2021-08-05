package xyz.esion.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.esion.manage.entity.Server;
import xyz.esion.manage.entity.ServerCommand;
import xyz.esion.manage.entity.ServerConfig;
import xyz.esion.manage.enumeration.ServerTypeEnum;
import xyz.esion.manage.exception.ServerException;
import xyz.esion.manage.mapper.ServerCommandMapper;
import xyz.esion.manage.mapper.ServerConfigMapper;
import xyz.esion.manage.mapper.ServerMapper;
import xyz.esion.manage.option.ServerCommandOption;
import xyz.esion.manage.option.ServerConfigOption;
import xyz.esion.manage.option.ServerOption;
import xyz.esion.manage.service.ServerService;
import xyz.esion.manage.view.ServerCommandView;
import xyz.esion.manage.view.ServerConfigView;
import xyz.esion.manage.view.ServerInfoView;
import xyz.esion.manage.view.ServerListView;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Esion
 * @since 2021/7/31
 */
@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerMapper serverMapper;
    private final ServerCommandMapper serverCommandMapper;
    private final ServerConfigMapper serverConfigMapper;

    @Override
    public List<ServerListView> list() {
        List<Server> servers = serverMapper.selectList(new QueryWrapper<Server>()
                .eq("is_delete", 0).orderByDesc("create_time"));
        return servers.stream()
                .map(item -> BeanUtil.copyProperties(item, ServerListView.class))
                .collect(Collectors.toList());
    }

    @Override
    public ServerInfoView infoById(String id) {
        Server server = serverMapper.selectById(id);
        if (server == null) {
            return new ServerInfoView();
        }
        List<ServerCommand> serverCommands = serverCommandMapper.selectList(new QueryWrapper<ServerCommand>()
                .eq("server_id", id));
        List<ServerConfig> serverConfigs = serverConfigMapper.selectList(new QueryWrapper<ServerConfig>()
                .eq("server_id", id));
        ServerInfoView view = new ServerInfoView();
        BeanUtil.copyProperties(server, view);
        view.setCommands(serverCommands.stream()
                .map(item -> BeanUtil.copyProperties(item, ServerCommandView.class))
                .collect(Collectors.toList()));
        view.setConfigs(serverConfigs.stream()
                .map(item -> BeanUtil.copyProperties(item, ServerConfigView.class))
                .collect(Collectors.toList()));
        return view;
    }

    @Override
    public Integer install(Integer serverType, Integer serverVersion, Integer installType) {
        //TODO 通过任务进行安装
        return 0;
    }

    @Override
    public void add(ServerOption option){
        Server server = new Server();
        server.setName(option.getName());
        server.setType(option.getType());
        server.setVersion(option.getVersion());
        server.setIsDelete(0);
        serverMapper.insert(server);

    }

    @Override
    public void update(String id, ServerOption option) {
        Server server = new Server();
        server.setId(id);
        server.setName(option.getName());
        server.setType(option.getType());
        server.setVersion(option.getVersion());
        server.setIsDelete(0);
        serverMapper.updateById(server);
    }

    @Override
    public void remove(String id) {
        Server server = new Server();
        server.setId(id);
        server.setIsDelete(1);
        serverMapper.updateById(server);
    }

    @Override
    public void commandAdd(ServerCommandOption option) {
        ServerCommand command = new ServerCommand();
        command.setName(option.getName());
        command.setCommand(option.getCommand());
        command.setServerId(option.getServerId());
        serverCommandMapper.insert(command);
    }

    @Override
    public void commandUpdate(String id, ServerCommandOption option) {
        ServerCommand serverCommand = BeanUtil.copyProperties(option, ServerCommand.class);
        serverCommand.setId(id);
        serverCommandMapper.updateById(serverCommand);
    }

    @Override
    public void commandRemove(String id) {
        serverCommandMapper.deleteById(id);
    }

    @Override
    public void configAdd(ServerConfigOption option) {
        ServerConfig config = new ServerConfig();
        config.setPath(option.getPath());
        config.setServerId(option.getServerId());
        serverConfigMapper.insert(config);
    }

    @Override
    public void configUpdate(String id, ServerConfigOption option) {
        ServerConfig serverConfig = BeanUtil.copyProperties(option, ServerConfig.class);
        serverConfig.setId(id);
        serverConfigMapper.updateById(serverConfig);
    }

    @Override
    public void configRemove(String id) {
        serverConfigMapper.deleteById(id);
    }

    @Override
    public String getConfigById(String id, String charset) throws ServerException {
        ServerConfig serverConfig = serverConfigMapper.selectById(id);
        if (serverConfig == null) {
            throw new ServerException("配置文件ID错误");
        }
        String path = serverConfig.getPath();
        File file = FileUtil.file(path);
        if (!file.exists()) {
            return "配置文件路径错误，请删除后重新选择。";
        }
        if (StrUtil.isBlank(charset)) {
            charset = CharsetUtil.UTF_8;
        }
        return FileUtil.readString(file, CharsetUtil.parse(charset));
    }

    @Override
    public String execCommand(String id) throws ServerException {
        ServerCommand serverCommand = serverCommandMapper.selectById(id);
        if (serverCommand == null) {
            throw new ServerException("命令ID错误");
        }
        return RuntimeUtil.execForStr(serverCommand.getCommand());
    }

}
