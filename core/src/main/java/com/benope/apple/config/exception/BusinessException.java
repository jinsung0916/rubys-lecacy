package com.benope.apple.config.exception;

import com.benope.apple.config.webMvc.RstCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BusinessException extends RuntimeException {

    private final RstCode rstCode;

    public BusinessException(RstCode rstCode) {
        super(rstCode.getMessage());
        this.rstCode = rstCode;
    }

    public BusinessException(RstCode rstCode, Throwable cause) {
        super(rstCode.getMessage(), cause);
        this.rstCode = rstCode;
    }

}
