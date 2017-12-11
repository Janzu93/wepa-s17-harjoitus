package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wepa.service.FileObjectService;

import java.io.IOException;

@Controller
@Transactional
public class FileObjectController {

    @Autowired
    private FileObjectService fileObjectService;

    @GetMapping(path = "/images/{id}", produces = "image/png")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return fileObjectService.getFileContent(id);
    }

    @PostMapping("/images")
    public String save(@RequestParam("file")MultipartFile file) throws IOException {
        fileObjectService.save(file);

        return "redirect:/";
    }
}
