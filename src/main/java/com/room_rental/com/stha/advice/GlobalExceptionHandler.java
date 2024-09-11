package com.room_rental.com.stha.advice;

import com.room_rental.com.stha.exception.PasswordMismatchException;
import com.room_rental.com.stha.exception.RoomRentalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends Throwable {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( MethodArgumentNotValidException.class )
    public Map<String , String> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String , String> errorMap = new HashMap<String , String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordMismatchException.class)
    public Map<String, String> handlePasswordMismatchException(PasswordMismatchException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( RoomRentalException.class )
    public Map<String, String> handleRoomRentalException(RoomRentalException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }





}
