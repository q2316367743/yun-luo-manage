package xyz.esion.manage.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.esion.manage.exception.FileException;
import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.global.Result;

import java.io.IOException;

/**
 * @author Esion
 * @since 2021/7/15
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception exception){
        exception.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException exception){
        return Result.fail().message("参数错误");
    }

    @ExceptionHandler(NotLoginException.class)
    public Result notLoginException(NotLoginException exception){
        if(exception.getType().equals(NotLoginException.NOT_TOKEN)) {
            return Result.fail(Result.ResultCode.UN_AUTHENTICATION);
        }
        else if(exception.getType().equals(NotLoginException.INVALID_TOKEN) || exception.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            return Result.fail(Result.ResultCode.INVALID);
        }
        return Result.fail(Result.ResultCode.FAIL).message(exception.getMessage());
    }

    @ExceptionHandler(FileException.class)
    public Result fileException(FileException exception){
        return Result.fail(Result.ResultCode.FAIL).message(exception.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public Result userException(UserException exception){
        return Result.fail(Result.ResultCode.FAIL).message(exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Result ioException(IOException exception){
        log.error(exception.getMessage());
        return Result.fail(Result.ResultCode.FAIL).message("没有权限");
    }

}
