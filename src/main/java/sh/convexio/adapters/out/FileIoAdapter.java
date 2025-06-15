package sh.convexio.adapters.out;

import org.apache.commons.io.FileUtils;
import sh.convexio.domain.model.FileContent;
import sh.convexio.domain.ports.IFileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIoAdapter implements IFileSystem {
    @Override
    public FileContent readFile(String path) {
        try {
            byte[] content = Files.readAllBytes(Path.of(path));
            String format = getFileFormat(path);
            return new FileContent(path, content, format);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file: " + path, e);
        }
    }

    @Override
    public void writeFile(String path, FileContent content) {
        if (path == null || content == null || content.content() == null) {
            throw new IllegalArgumentException("Path and data cannot be null");
        }

        File file = new File(path);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new RuntimeException("Final Directory was not found: " + parent);
        }

        if (!parent.canWrite()) {
            throw new RuntimeException("Not enugh rights to convert: " + parent);
        }

        try {
            FileUtils.writeByteArrayToFile(file, content.content());
        } catch (IOException e) {
            throw new RuntimeException("Error while converting file: " + path +
                    " - " + e.getMessage(), e);
        }
    }

    private String getFileFormat(String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        return extension.toLowerCase();
    }
}