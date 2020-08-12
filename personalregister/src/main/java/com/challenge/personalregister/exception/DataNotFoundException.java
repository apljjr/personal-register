package com.challenge.personalregister.exception;

import com.challenge.personalregister.util.Constants;
import org.springframework.http.HttpStatus;

public class DataNotFoundException extends BusinessException {
	
   private static final long serialVersionUID = 2697252359599571441L;
   
   public DataNotFoundException() {
      this.setHttpStatusCode(HttpStatus.NOT_FOUND);
      this.setMessage(Constants.NOT_FOUND);
      this.setDescription(Constants.DATA_NOT_FOUND);
   }

   public DataNotFoundException(String description) {
      this.setHttpStatusCode(HttpStatus.NOT_FOUND);
      this.setMessage(Constants.NOT_FOUND);
      this.setDescription(description);
   }
   
}

