package kr.hhplus.be.server.support.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.token.domain.TokenService;
import kr.hhplus.be.server.token.domain.TokenStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenValidationInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Incoming Request: URL = {}, Method = {}, Params = {}",
                request.getRequestURI(),
                request.getMethod(),
                request.getParameterMap());
        // "/reservations"의 POST 요청
        if ("POST".equalsIgnoreCase(request.getMethod()) && "/reservations".equals(request.getRequestURI())) {
            return validateToken(request, response);
        }

        // "/concerts/**"와 기타 등록된 경로는 기본 처리
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/concerts/")) {
            return validateToken(request, response);
        }

        return true; // 나머지 경로는 검증 없이 통과
    }

    // 공통 토큰 검증 로직을 메서드로 분리
    private boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Token"); // 헤더에서 토큰을 가져옴
        TokenStatus status = tokenService.get(token).getStatus();
        if (StringUtils.hasText(token) && status == TokenStatus.ACTIVE) {
            return true; // 토큰이 유효하면 요청
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 토큰이 없거나 유효하지 않으면 401
            response.setContentType("application/json;charset=UTF-8"); // JSON UTF-8 인코딩
            response.setCharacterEncoding("UTF-8"); // 응답 본문 인코딩
            if (status == TokenStatus.INACTIVE) {
                response.getWriter().write("{\"error\": \"" + ErrorMessages.UNAUTHORIZED_ACCESS + "\"}");
            } else if (status == TokenStatus.EXPIRED) {
                response.getWriter().write("{\"error\": \"" + ErrorMessages.TOKEN_EXPIRED + "\"}");
            }
            log.info("Token is valid.");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Outgoing Response: Status = {}, Content-Type = {}",
                response.getStatus(),
                response.getContentType());
    }


}
