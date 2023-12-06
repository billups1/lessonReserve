package hs.lessonReserve.handler;

import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public @ResponseBody String exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomApiException.class)
    public @ResponseBody String apiException(CustomApiException e) {
        return Script.back(e.getMessage());
    }

}
