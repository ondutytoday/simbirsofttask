package org.vasileva.simbirsofttask.service;


import org.springframework.stereotype.Service;
import org.vasileva.simbirsofttask.exception.FileSizeException;

import java.io.IOException;
import java.nio.file.Path;

/*
* Интрефейс, который позволяет нам масштабировать приложение
*
* но это неточно
*/
@Service
public interface TaskService {

    void checkFile(Path fileWithLogs) throws IOException, FileSizeException;

}
