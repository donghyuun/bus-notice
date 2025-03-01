package com.example.busnotice.global.handler;

import com.example.busnotice.global.code.ErrorCode;
import com.example.busnotice.global.code.StatusCode;
import com.example.busnotice.global.exception.BusStopException;
import com.example.busnotice.global.exception.BusinessException;
import com.example.busnotice.global.exception.CityCodeException;
import com.example.busnotice.global.exception.GeneralException;
import com.example.busnotice.global.exception.RefreshTokenException;
import com.example.busnotice.global.exception.ScheduleException;
import com.example.busnotice.global.exception.UserException;
import com.example.busnotice.global.format.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException e) {
        return ApiResponse.createFail(e.getErrorCode());
    }

    @ExceptionHandler(ScheduleException.class)
    public ApiResponse<Void> handleScheduleException(ScheduleException e) {
        return ApiResponse.createFail(e.getErrorCode());
    }

    @ExceptionHandler(UserException.class)
    public ApiResponse<Void> handleUserException(UserException e) {
        return ApiResponse.createFail(e.getErrorCode());
    }

    @ExceptionHandler(BusStopException.class)
    public ApiResponse<Void> busStopException(BusStopException e) {
        return ApiResponse.createFail(e.getErrorCode());
    }

    @ExceptionHandler(CityCodeException.class)
    public ApiResponse<Void> CityCodeException(CityCodeException e) {
        return ApiResponse.createFail(e.getErrorCode());
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ApiResponse<Void> handleUnsupportedEncodingException(UnsupportedEncodingException e) {
        return ApiResponse.createFail(ErrorCode.UNSUPPORTED_ENCODING);
    }

    @ExceptionHandler(GeneralException.class)
    public ApiResponse<Void> GeneralException(GeneralException e) {
        return ApiResponse.createFail(e.getErrorCode());
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ApiResponse<Void> RefreshTokenException(RefreshTokenException e) {
        return ApiResponse.createFail(e.getErrorCode());
    }

    @ExceptionHandler
    public ApiResponse<Void> handleAuthenticationException(HttpServletRequest request,
        AuthenticationException e) {
        return ApiResponse.createAuthFail((String) request.getAttribute("exceptionMessage"));
    }
}
