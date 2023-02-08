package com.benope.apple.config.exception;

import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.utils.DomainObjectUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ConditionalOnWebApplication
@Component
public class CommonErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        Throwable e = super.getError(webRequest);
        RstCode rstCode = toRstCode(e);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("trace", errorAttributes.get("trace"));
        returnMap.put("message", Objects.requireNonNullElse(
                DomainObjectUtil.nullSafeFunction(e, Throwable::getMessage), errorAttributes.get("message"))
        );
        returnMap.put("success", false);
        returnMap.put("rst_cd", rstCode.getCode());
        returnMap.put("rst_msg", rstCode.getMessage());
        return returnMap;
    }

    private RstCode toRstCode(Throwable e) {
        if (e instanceof BusinessException) {
            return ((BusinessException) e).getRstCode();
        } else {
            return RstCode.FAILURE;
        }
    }

}
