package hs.lessonReserve.handler.aop;

import hs.lessonReserve.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

@Component
@Aspect
public class ValidationAdvice {

    @Around("execution(* hs.lessonReserve.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        System.out.println("★ 0");
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                System.out.println("★ 1");
                if (bindingResult.hasErrors()) {
                    System.out.println("★ 2");
                    HashMap<String, String> errorMap = new HashMap<>();
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                    for (FieldError fieldError : fieldErrors) {
                        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }


}
