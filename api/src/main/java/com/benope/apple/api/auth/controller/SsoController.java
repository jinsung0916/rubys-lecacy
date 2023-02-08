package com.benope.apple.api.auth.controller;

import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.config.auth.ClientId;
import com.benope.apple.config.auth.SsoAttributes;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.utils.Cafe24RandomIdUtils;
import com.benope.apple.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
@RequestMapping("/sso")
@RequiredArgsConstructor
public class SsoController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "client_id") ClientId clientId,
            @RequestParam(value = "client_secret", required = false) String clientSecret,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            HttpServletResponse response
    ) {
        SsoAttributes ssoAttributes = SsoAttributes.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .state(state)
                .redirectUri(redirectUri)
                .build();
        CookieUtils.addCookie(response, SsoAttributes.SSO_ATTRIBUTES_REQUEST_COOKIE_NAME, CookieUtils.serialize(ssoAttributes), 180);

        return "Login";
    }

    @RequestMapping("/token")
    @ResponseBody
    public Map<String, String> tokenEndpoint(@RequestParam("code") String accessToken) {
        return Map.of("access_token", accessToken);
    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public Map<String, Object> userInfoEndpoint(@RequestParam("access_token") String accessToken) {
        return retrieveUserInfo(accessToken);
    }

    private Map<String, Object> retrieveUserInfo(String accessToken) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);

        Long mbNo = Long.valueOf(authentication.getName());
        Member member = memberService.getOne(
                        Member.builder()
                                .mbNo(mbNo)
                                .build()
                )
                .orElseThrow(IllegalStateException::new);

        // 카페 24 최초 연동 시 카페24 ID 를 생성한다.
        if (Objects.isNull(member.getCafe24Id())) {
            Member record = Member.builder()
                    .mbNo(member.getMbNo())
                    .cafe24Id(Cafe24RandomIdUtils.getRandomId())
                    .build();

            memberService.update(record);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", member.getCafe24Id());
        response.put("name", member.getNickname());
        response.put("email", member.getEmail());
        return response;
    }

    @RequestMapping("/login/fail")
    private String loginFailPage() {
        return "LoginFail";
    }
}
