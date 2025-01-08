package kr.hhplus.be.server.token.interfaces;

import kr.hhplus.be.server.token.domain.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    // 토큰 발급

}
