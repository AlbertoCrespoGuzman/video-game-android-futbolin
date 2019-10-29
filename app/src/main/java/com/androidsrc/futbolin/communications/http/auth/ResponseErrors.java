package com.androidsrc.futbolin.communications.http.auth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alberto on 2017/12/05.
 */

public class ResponseErrors {

    List<ResponseError> errors = new ArrayList<>();

    public ResponseErrors(){

    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ResponseError> errors) {
        this.errors = errors;
    }
}
