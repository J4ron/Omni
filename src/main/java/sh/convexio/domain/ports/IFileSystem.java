package sh.convexio.domain.ports;
import sh.convexio.domain.model.FileContent;

public interface IFileSystem {
    FileContent readFile(String path);
    void writeFile(String path, FileContent content);
}