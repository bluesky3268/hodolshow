package com.hyunbennylog.api.config;

import com.hyunbennylog.api.config.data.UserSession;
import com.hyunbennylog.api.domain.Session;
import com.hyunbennylog.api.exception.UnAuthorizedException;
import com.hyunbennylog.api.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            log.error("servletRequest is null");
            throw new UnAuthorizedException();
        }

//        // DB 사용자 확인 작업
//        Session findSession = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(() -> new UnAuthorizedException());

        // JWT 사용
        String jws = webRequest.getHeader("Authorization");
        if(jws == null || jws.equals("")) throw new UnAuthorizedException();

        // jwt 복호화
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(appConfig.getJwt_key())
                    .build()
                    .parseClaimsJws(jws);

            // 이제 jwt를 신뢰할 수 있음
            String userId = claims.getBody().getSubject();
            return new UserSession(Long.parseLong(userId));

        } catch (JwtException e) {
            throw new UnAuthorizedException();
        }

//        return new UserSession(findSession.getId());
    }

}
