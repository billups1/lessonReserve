package hs.lessonReserve.web.api.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class ImageFile {

    @Value("${file.path}")
    private String uploadFolder;

    @GetMapping("/image/{imageFileName}")
    public Resource downloadImage(@PathVariable String imageFileName) throws MalformedURLException {
        System.out.println("aaa");
        String fullPath = uploadFolder + imageFileName;
        return new UrlResource("file:"+fullPath);
    }

}
