package itopia.resolar.application.exception;

import io.swagger.v3.oas.annotations.Hidden;
import itopia.resolar.application.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("Runtime Exception occurred: ", e);
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception e) {
        String message = "잘못된 요청 데이터입니다";
        if (e instanceof MethodArgumentNotValidException validException) {
            message = validException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (e instanceof BindException bindException) {
            message = bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unexpected exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("서버 내부 오류가 발생했습니다"));
    }
}