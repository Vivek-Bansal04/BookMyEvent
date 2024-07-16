package com.bookmyshow.api.utils;

import com.bookmyshow.api.exceptions.InternalServerException;
import com.bookmyshow.api.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.bookmyshow.api.utils.ValidationUtils.isNotNullOrBlank;
import static com.bookmyshow.api.utils.constants.GlobalConstants.REQUEST_ID;

@Slf4j
@RestControllerAdvice("com.bookmyshow.api")
public class TLExceptionAdvice extends ResponseEntityExceptionHandler {



    @ExceptionHandler({ClassCastException.class})
    public final ResponseEntity<Object> handleClassCastException(ClassCastException ex) {
        log.error("", ex);
        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.BAD_REQUEST.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        output.setRequestId(MDC.get(REQUEST_ID));
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            InternalServerException.class,
            DataIntegrityViolationException.class,
            Exception.class
    })
    public final ResponseEntity<Object> handleExceptions(Exception ex) {
        log.error("", ex);

        final ResponseBuilder<Object> output = parseUnknownException(ex);

        output.setRequestId(MDC.get(REQUEST_ID));
        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("", ex);

        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.NOT_FOUND.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        output.setErrorData(ex.getCause());
        output.setRequestId(MDC.get(REQUEST_ID));

        return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({RequestValidationException.class})
//    public final ResponseEntity<Object> handleRequestValidationException(RequestValidationException ex) {
//        log.error("", ex);
//
//        final ResponseBuilder<Object> output = new ResponseBuilder<>();
//        output.setStatus(HttpStatus.BAD_REQUEST.value());
//        output.setMessage(ex.getMessage());
//        output.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
//        output.setErrorData(ex.getCause());
//        output.setRequestId(MDC.get(REQUEST_ID));
//
//        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler({ConcurrentUpdateException.class})
//    public final ResponseEntity<Object> handleConcurrentUpdateException(ConcurrentUpdateException ex) {
//        log.error("", ex);
//
//        final ResponseBuilder<Object> output = new ResponseBuilder<>();
//        output.setStatus(HttpStatus.CONFLICT.value());
//        output.setMessage(ex.getMessage());
//        output.setError(HttpStatus.CONFLICT.getReasonPhrase());
//        output.setErrorData(ex.getCause());
//        output.setRequestId(MDC.get(REQUEST_ID));
//
//        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
//    }

    private ResponseBuilder<Object> parseUnknownException(Exception ex) {
        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        output.setErrorData(ex.getCause());
        output.setRequestId(MDC.get(REQUEST_ID));
        return output;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        log.error("", ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());

        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ": " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ": " + objectError.getDefaultMessage();
            errors.add(error);
        }

        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.BAD_REQUEST.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        output.setErrorData(errors);
        output.setRequestId(MDC.get(REQUEST_ID));
        return handleExceptionInternal(ex, output, headers, HttpStatus.BAD_REQUEST, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        log.error("", ex);
        String unsupported = "Unsupported content type: " + ex.getContentType();
        String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());

        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.BAD_REQUEST.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        output.setErrorData(List.of(unsupported, supported));
        output.setRequestId(MDC.get(REQUEST_ID));
        return handleExceptionInternal(ex, output, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("", ex);
        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.BAD_REQUEST.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        output.setRequestId(MDC.get(REQUEST_ID));
        return handleExceptionInternal(ex, output, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        log.error("", ex);
        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.BAD_REQUEST.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        output.setRequestId(MDC.get(REQUEST_ID));
        return handleExceptionInternal(ex, output, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        log.error("", ex);
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        List<String> errors = new ArrayList<>();
        if (isNotNullOrBlank(mostSpecificCause.getMessage())) {
            String exceptionName = mostSpecificCause.getClass().getName();
            String message = mostSpecificCause.getMessage();
            errors.add(exceptionName);
            errors.add(message);
        } else {
            errors.add(ex.getMessage());
        }

        final ResponseBuilder<Object> output = new ResponseBuilder<>();
        output.setStatus(HttpStatus.BAD_REQUEST.value());
        output.setMessage(ex.getMessage());
        output.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        output.setErrorData(errors);
        output.setRequestId(MDC.get(REQUEST_ID));
        return handleExceptionInternal(ex, output, headers, HttpStatus.BAD_REQUEST, request);
    }
}
