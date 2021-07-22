package xyz.esion.manage.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.esion.manage.service.FileService;
import xyz.esion.manage.view.FileListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Esion
 * @since 2021/7/21
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<FileListView> show(String path) {
        return null;
    }

    @Override
    public void open(String path, ResponseEntity<Byte> entity) {

    }

}
