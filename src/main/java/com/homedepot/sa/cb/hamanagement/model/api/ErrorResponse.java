package com.homedepot.sa.cb.hamanagement.model.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PXK2457 on 11/22/2016.
 */
@ApiModel(description = "Error Model",value = "errorResponse")
@XmlRootElement(name="errors")
@JacksonXmlRootElement(localName="errors")
public class ErrorResponse implements Serializable {



    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Error> error;


    public ErrorResponse() {
        // Default constructor
    }

    public List<Error> getError() {
        return error;
    }

    @XmlElement
    public void setError(List<Error> error) {
        this.error = error;
    }

    public void addError(Error error) {
       this.error = new ArrayList<Error>(1);
       this.error.add(error);
    }


}
