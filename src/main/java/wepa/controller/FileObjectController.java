package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wepa.service.FileObjectService;

import java.io.IOException;

@Controller
public class FileObjectController {

    @Autowired
    private FileObjectService fileObjectService;

    @GetMapping(path = "/images/{id}", produces = "image/png")
    public byte[] get(@PathVariable Long id) {
        return fileObjectService.getFileContent(id);
    }

    @PostMapping("/images")
    public String save(@RequestParam("file")MultipartFile file) throws IOException {
        fileObjectService.save(file);

        return "redirect:/";
    }
}
