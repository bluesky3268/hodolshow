package com.hyunbennylog.api.config;

import com.hyunbennylog.api.config.data.UserSession;
import com.hyunbennylog.api.domain.Session;
import com.hyunbennylog.api.exception.UnAuthorizedException;
import com.hyunbennylog.api.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization");
        if(accessToken == null || accessToken.equals("")) throw new UnAuthorizedException();

        // DB 사용자 확인 작업
        Session findSession = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new UnAuthorizedException());

        return new UserSession(findSession.getId());
    }
}
