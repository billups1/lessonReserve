package hs.lessonReserve.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GatherController {

    @GetMapping("/gather")
    public String gatherHome() {

        return "gather/gather";
    }

    @GetMapping("/gather/create")
    public String gatherCreate() {
        return "gather/gatherCreate";
    }

}
