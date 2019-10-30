package com.sidianzhong.sdz.interceptor;

import com.sidianzhong.sdz.utils.PermissionException;
import com.sidianzhong.sdz.utils.ResultModel;
import com.sidianzhong.sdz.utils.ResultStatus;
import com.sidianzhong.sdz.utils.TokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获
 */
@ResponseBody
@ControllerAdvice
public class MyControllerAdvice {

    /**
     * Token异常捕获
     */
    @ResponseBody
    @ExceptionHandler(value = TokenException.class)
    public ResultModel tokenExceptionHandler() {
        return ResultModel.error(ResultStatus.TOKEN_ERROR);
    }

    /**
     * 权限异常捕获
     */
    @ResponseBody
    @ExceptionHandler(value = PermissionException.class)
    public ResultModel permissionExceptionHandler() {
        return ResultModel.error(ResultStatus.NOT_PERMISSION);
    }
}