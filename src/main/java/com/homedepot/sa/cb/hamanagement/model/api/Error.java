package com.homedepot.sa.cb.hamanagement.model.api;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * Created by PXK2457 on 11/22/2016.
 */
@ApiModel(description = "Error Model",value="error")

public class Error implements Serializable  {

        private final String errorCode;

        private final String errorMessage;

    public Error(String var1, String var2) {
        this.errorCode = var1;
        this.errorMessage = var2;
    }
        @XmlElement
        public String getErrorCode() {
            return errorCode;
        }


        @XmlElement
        public String getErrorMessage() {
            return errorMessage;
        }

}
