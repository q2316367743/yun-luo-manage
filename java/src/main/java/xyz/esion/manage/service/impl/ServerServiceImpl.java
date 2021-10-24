package xyz.esion.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.esion.manage.entity.Server;
import xyz.esion.manage.entity.ServerCommand;
import xyz.esion.manage.entity.ServerConfig;
import xyz.esion.manage.exception.ServerException;
import xyz.esion.manage.mapper.ServerCommandMapper;
import xyz.esion.manage.mapper.ServerConfigMapper;
import xyz.esion.manage.mapper.ServerMapper;
import xyz.esion.manage.option.ServerCommandOption;
import xyz.esion.manage.option.ServerConfigOption;
import xyz.esion.manage.option.ServerOption;
import xyz.esion.manage.service.ServerService;
import xyz.esion.manage.view.server.ServerCommandView;
import xyz.esion.manage.view.server.ServerConfigView;
import xyz.esion.manage.view.server.ServerInfoView;
import xyz.esion.manage.view.server.ServerListView;

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
                .map(item -> {
                    ServerListView view = BeanUtil.copyProperties(item, ServerListView.class);
                    view.setStatus(false);
                    // 判断状态
                    String applicationName = item.getApplicationName();
                    if (StrUtil.isNotBlank(applicationName)){
                        String[] commands = {"/bin/sh","-c","ps -aux | grep " + applicationName};
                        List<String> lines = RuntimeUtil.execForLines(commands);
                        for (String line : lines) {
                            if (!line.contains("grep")){
                                view.setStatus(true);
                            }
                        }
                    }
                    return view;
                })
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
        String applicationName = view.getApplicationName();
        if (StrUtil.isNotBlank(applicationName)){
            String[] commands = {"/bin/sh","-c","ps -aux | grep " + applicationName};
            List<String> lines = RuntimeUtil.execForLines(commands);
            for (String line : lines) {
                if (!line.contains("grep")){
                    view.setStatus(true);
                }
            }
        }
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
        server.setCreateId(option.getUserId());
        server.setUpdateId(option.getUserId());
        server.setApplicationName(option.getApplicationName());
        serverMapper.insert(server);

    }

    @Override
    public void update(String id, ServerOption option) {
        Server server = new Server();
        server.setId(id);
        server.setName(option.getName());
        server.setType(option.getType());
        server.setVersion(option.getVersion());
        server.setUpdateId(option.getUserId());
        server.setApplicationName(option.getApplicationName());
        serverMapper.updateById(server);
    }

    @Override
    public void remove(String id, String userId) {
        Server server = new Server();
        server.setId(id);
        server.setIsDelete(1);
        server.setUpdateId(userId);
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
    public String commandExec(String id) throws ServerException {
        ServerCommand serverCommand = serverCommandMapper.selectById(id);
        if (serverCommand == null) {
            throw new ServerException("命令ID错误");
        }
        return RuntimeUtil.execForStr(serverCommand.getCommand());
    }

}
