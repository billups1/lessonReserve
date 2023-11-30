package hs.lessonReserve.handler;

import hs.lessonReserve.handler.ex.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        System.out.println(e.getMessage());
        return e.getMessage().toString();
    }

}
