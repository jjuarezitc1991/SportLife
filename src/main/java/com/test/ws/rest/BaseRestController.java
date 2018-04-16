package com.test.ws.rest;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseRestController {

    private final Logger log = Logger.getLogger(BaseRestController.class.getName());

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleEntityNotFoundExceptions(EntityNotFoundException exception, HttpServletResponse response) {
        return new ErrorDTO("Entity not found");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleJPAEntityNotFoundExceptions(EntityNotFoundException exception, HttpServletResponse response) {
        return new ErrorDTO("Entity not found");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorDTO handleConstraintViolationExceptions(ConstraintViolationException exception, HttpServletResponse response) {
        ErrorDTO error = new ErrorDTO("Validation failed");
        if (exception.getConstraintViolations() != null) {
            for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
                error.addDetail(cv.getPropertyPath() + " " + cv.getMessage() + " (" + cv.getInvalidValue() + ")");
            }
        }
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleExceptions(Exception exception, HttpServletResponse response) {
        log.error("[ParkStationRestController]", exception);
        return new ErrorDTO("Internal server error");
    }

}
