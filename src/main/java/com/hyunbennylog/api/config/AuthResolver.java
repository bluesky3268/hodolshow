package com.hyunbennylog.api.config;

import com.hyunbennylog.api.config.data.UserSession;
import com.hyunbennylog.api.domain.Session;
import com.hyunbennylog.api.exception.UnAuthorizedException;
import com.hyunbennylog.api.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        String accessToken = webRequest.getHeader("Authorization");
//        if(accessToken == null || accessToken.equals("")) throw new UnAuthorizedException();

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            log.error("servletRequest is null");
            throw new UnAuthorizedException();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0) {
            log.error("cookies arr length is 0");
            throw new UnAuthorizedException();
        }

        String accessToken = cookies[0].getValue();

        // DB 사용자 확인 작업
        Session findSession = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new UnAuthorizedException());

        return new UserSession(findSession.getId());
    }
}
