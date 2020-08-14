package com.challenge.personalregister.exception;

import com.challenge.personalregister.util.Constants;
import org.springframework.http.HttpStatus;

public class AuthException extends BusinessException {

   private static final long serialVersionUID = 2697252359599571441L;

   public AuthException() {
      this.setHttpStatusCode(HttpStatus.UNAUTHORIZED);
      this.setMessage(Constants.UNAUTHORIZED);
      this.setDescription(Constants.UNAUTHORIZED);
   }
   
}

