package hs.lessonReserve.web.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class ImageFileApiController {

    @Value("${file.path}")
    private String uploadFolder;

    @GetMapping("/image/{imageFileName}")
    public Resource downloadImage(@PathVariable String imageFileName) throws MalformedURLException {
        String fullPath = uploadFolder + imageFileName;
        return new UrlResource("file:"+fullPath);
    }

}
