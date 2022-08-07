package cn.winter.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/08/07 23:02
 */
public class FileSystemResource implements Resource {
    private final File file;

    private final String filePath;


    public FileSystemResource(String filePath) {
        this.file = new File(filePath);
        this.filePath = filePath;
    }

    public FileSystemResource(File file) {
        this.file = file;
        this.filePath = file.getPath();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public final String getFilePath() {
        return this.filePath;
    }

}
