package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wepa.domain.FileObject;
import wepa.repository.FileObjectRepository;

import java.io.IOException;
import java.util.List;

@Service
public class FileObjectService {

    @Autowired
    private FileObjectRepository fileObjectRepository;


    public byte[] getFileContent(Long id) {
        if (fileObjectRepository.exists(id)) {
            return fileObjectRepository.findOne(id).getContent();
        }
        return null;
    }

    public void save(MultipartFile file) throws IOException{
        if (file.getContentType().equals("image/png")) {
            FileObject fo = new FileObject();
            fo.setContent(file.getBytes());
            fo.setMediaType(file.getContentType());
            fo.setName(file.getName());
            fo.setSize(file.getSize());
            fileObjectRepository.save(fo);
        }
    }

    public List<FileObject> findAll() {
        return fileObjectRepository.findAll();
    }
}
