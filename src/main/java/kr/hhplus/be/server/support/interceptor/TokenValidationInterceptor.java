package kr.hhplus.be.server.support.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.token.domain.TokenService;
import kr.hhplus.be.server.token.domain.TokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenValidationInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
        if (StringUtils.hasText(token) && tokenService.get(token).getStatus() == TokenStatus.ACTIVE) {
            return true; // 토큰이 유효하면 요청
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 토큰이 없거나 유효하지 않으면 401
            response.setContentType("application/json;charset=UTF-8"); // JSON UTF-8 인코딩
            response.setCharacterEncoding("UTF-8"); // 응답 본문 인코딩
            response.getWriter().write("{\"error\":\"토큰이 만료되었거나 존재하지 않습니다.\"}");
            return false;
        }
    }

}
