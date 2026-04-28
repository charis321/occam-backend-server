package com.charis.occam_spm_sys.exception;

import java.sql.SQLException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.charis.occam_spm_sys.common.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SQLException.class)
	public Result handleSQLException(SQLException e) {
		log.error("資料庫操作失敗: ", e);
		return Result.fail(500, "資料庫操作失敗");
	}

	@ExceptionHandler(BusinessException.class)
	public Result handleBusinessException(BusinessException e) {
		log.warn("業務邏輯發生問題 | msg:{}", e.getMsg());
		return Result.fail(e.getCode(), e.getMsg(), e.getData());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public Result handleAccessDeniedException(AccessDeniedException e) {
		log.warn("權限不足嘗試訪問 | msg {}", e.getMessage());
		return Result.fail(403, "權限不足，請聯繫管理員");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result handleValidationException(MethodArgumentNotValidException e) {
		var fieldError = e.getBindingResult().getFieldError();
		String message = fieldError != null ? fieldError.getDefaultMessage() : "參數校驗失敗";
		log.warn("參數格式錯誤 | msg: {}", message);
		return Result.fail(400, "參數格式錯誤: " + message);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		log.warn("不支援的請求方式 | method: {}", e.getMethod());
		return Result.fail(405, "不支援的請求方式: " + e.getMethod());
	}

	@ExceptionHandler(RuntimeException.class)
	public Result handleRuntimeException(RuntimeException e) {
		log.error("伺服器發生問題: msg:{}", e);
		return Result.fail(500, "伺服器發生問題，請稍後再試或聯繫管理員");
	}
}
