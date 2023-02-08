package com.benope.apple.api;

import com.benope.apple.config.ElasticsearchConfig;
import com.benope.apple.config.auth.TokenProvider;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Import(ElasticsearchConfig.class)
@Transactional
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppleTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    private TokenProvider tokenProvider;

    protected String accessToken(Long mbNo) {
        return accessToken(mbNo, new String[]{});
    }

    protected String accessToken(Long mbNo, String... authorities) {
        Authentication authentication = toMockAuthentication(mbNo, authorities);
        return tokenProvider.createAccessToken(authentication);
    }

    protected String refreshToken(Long mbNo) {
        Authentication authentication = toMockAuthentication(mbNo);
        return tokenProvider.createRefreshToken(authentication);
    }

    private Authentication toMockAuthentication(Long mbNo, String... authorities) {
        return new UsernamePasswordAuthenticationToken(
                mbNo,
                null,
                Arrays.stream(authorities)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    protected ResultMatcher isBusinessException(RstCode rstCode) {
        return result -> {
            BusinessException e = (BusinessException) result.getResolvedException();
            assertThat(e).isNotNull();
            assertThat(e.getRstCode()).isEqualTo(rstCode);
        };
    }

}
