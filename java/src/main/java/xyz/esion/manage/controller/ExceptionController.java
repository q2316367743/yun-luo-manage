package xyz.esion.manage.controller;

import cn.dev33.satoken.exception.NotLoginException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.esion.manage.exception.FileException;
import xyz.esion.manage.global.Result;

/**
 * @author Esion
 * @since 2021/7/15
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception exception){
        exception.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(NotLoginException.class)
    public Result NotLoginException(NotLoginException exception){
        return Result.fail(Result.ResultCode.FAIL).message(exception.getMessage());
    }

    @ExceptionHandler(FileException.class)
    public Result FileException(FileException exception){
        return Result.fail(Result.ResultCode.FAIL).message(exception.getMessage());
    }

}
