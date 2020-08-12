package com.challenge.personalregister.exception;

import com.challenge.personalregister.util.Constants;
import org.springframework.http.HttpStatus;

public class DataExistsException extends BusinessException {

   private static final long serialVersionUID = 2697252359599571441L;

   public DataExistsException() {
      this.setHttpStatusCode(HttpStatus.BAD_REQUEST);
      this.setMessage(Constants.BAD_REQUEST);
      this.setDescription(Constants.DATA_EXISTS);
   }
   
}

