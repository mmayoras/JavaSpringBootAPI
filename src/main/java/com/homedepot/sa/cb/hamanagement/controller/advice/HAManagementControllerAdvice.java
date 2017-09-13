package com.homedepot.sa.cb.hamanagement.controller.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.homedepot.sa.cb.hamanagement.HAManagementApplication;
import com.homedepot.sa.cb.hamanagement.exception.*;
import com.homedepot.sa.cb.hamanagement.model.api.Error;
import com.homedepot.sa.cb.hamanagement.model.api.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This intercepts all the controllers and listens for the exception.
 * Prepares a common error response.
 * Created by PXK2457 on 11/22/2016.
 */
@Component
@ControllerAdvice
public class HAManagementControllerAdvice {

    private static final Logger LOGGER = Logger.getLogger(HAManagementControllerAdvice.class);

    @Autowired
    private MessageSource msgSource;

    public

    HAManagementControllerAdvice(){
        if(null == msgSource){
            msgSource = (new HAManagementApplication()).messageSource();
        }
    }

    /**
     * Method to handle when unexpected thrown from service. Though it shouldn't happen ideally have palced this for safety.
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponse> handleUnknownException(Exception ex) {
        LOGGER.error("Unknown Exception.",ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage("error.internal.unknown"));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to handle Application Exception thrown from service.
     * @param ex
     * @return
     */
    @ExceptionHandler(InternalServerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponse> handleApplicationError(InternalServerException ex) {
        LOGGER.error(ex.getErrorCode(),ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage(ex.getErrorCode()));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to handle Application Exception thrown from service.
     * @param ex
     * @return
     */
    @ExceptionHandler(TandemServiceException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse> handleTandemServiceException(TandemServiceException ex) {
        LOGGER.error(ex.getErrorCode(),ex);
        ErrorResponse response = new ErrorResponse();
        Error error = new Error(ex.getErrorCode(),ex.getMessage());
        response.addError(error);
        if("5061".equals(ex.getTandemServiceErrorCode()) || "1".equals(ex.getTandemServiceErrorCode()) || "2".equals(ex.getTandemServiceErrorCode())){
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        LOGGER.error(ex.getErrorCode(),ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage(ex.getErrorCode()));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        LOGGER.error(ex.getErrorCode(),ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage(ex.getErrorCode()));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        LOGGER.error(ex.getMessage(),ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage("error.mediaType.unsupported"));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleServletRequestBindingException(ServletRequestBindingException ex) {
        LOGGER.error(ex.getMessage(),ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage("error.requestHeaderParameter.required"));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PreconditionFailedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    ResponseEntity<ErrorResponse> handlePreconditionFailedException(PreconditionFailedException ex) {
        LOGGER.error(ex.getErrorCode(),ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage("error.precondition.failed"));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        LOGGER.error(ex.getMessage(),ex);
        ErrorResponse response = new ErrorResponse();
        response.addError(getErrorMessage("error.requestParameter.required"));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleMessageNotReadableException(Throwable ex) {
        LOGGER.error(ex.getMessage(),ex);
        Throwable throwable = ex.getCause();
        String errorCode;
        ErrorResponse response = new ErrorResponse();
        if(throwable instanceof JsonMappingException) {
            StringBuilder field = new StringBuilder();
            JsonMappingException jsonMappingException = (JsonMappingException) throwable;
            List<JsonMappingException.Reference> references = jsonMappingException.getPath();
            for (JsonMappingException.Reference reference : references) {
                if (reference.getFieldName() != null) {
                    field.append(reference.getFieldName()).append(".");
                }
            }
            errorCode = "error."+field.deleteCharAt(field.length() - 1).toString();


        }else{
            errorCode = "error.request.invalid";
         }
        response.addError(getErrorMessage(errorCode));
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidationException(MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getMessage(),ex);
        BindingResult result = ex.getBindingResult();
        return processFieldError(result.getAllErrors());
    }

    private ErrorResponse processFieldError(List<ObjectError> errors){
        ErrorResponse errorResponse = new ErrorResponse();
        List<Error> errorList = new ArrayList<Error>();

        if (!CollectionUtils.isEmpty(errors)){
            errors.stream().forEach(error -> errorList.add(getErrorMessage(error.getDefaultMessage())));
            errorResponse.setError(errorList);
        }
        return errorResponse;
    }

    /**
     * Prepare error response with list of errors
     *
     * Read the error code and message from the message.properties file using the validation / javax contraint messages
     * These constraing messages should be a property / key name in the message.properties file.
     * @param errorMessageLabel
     * @return Error Object
     */
    private Error getErrorMessage(String errorMessageLabel) {
        Error errorObject = null;
        Locale currentLocale = LocaleContextHolder.getLocale();
        try{
            String errorValue = msgSource.getMessage(errorMessageLabel, null, currentLocale);
            String[] keyValue = StringUtils.split(errorValue , "|" );
            errorObject = new Error(keyValue[0], keyValue[1]);

        }catch (NoSuchMessageException ne){
            LOGGER.error(ne);
            errorObject = new Error("Unknown","An Error occurred while retrieving the message");
        }

        return errorObject;
    }
}
