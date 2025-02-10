package kr.hhplus.be.server.token.interfaces;

import kr.hhplus.be.server.token.application.service.TokenRedisService;
import kr.hhplus.be.server.token.domain.TokenService;
import kr.hhplus.be.server.token.interfaces.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final TokenRedisService tokenRedisService;

    // 토큰 순서 조회
    @GetMapping("/{UUID}")
    public ResponseEntity<String> getToken(@PathVariable("UUID") String UUID) {
        if (tokenRedisService.isActiveToken(UUID)){
            return new ResponseEntity<>("토큰이 활성화된 상태입니다.", HttpStatus.OK);
        }
        long waitingTokenIndex = tokenRedisService.getWaitingTokenIndex(UUID);
        return new ResponseEntity<>("고객님의 순서는 " + waitingTokenIndex + "입니다.", HttpStatus.OK);
    }

    // 토큰 생성
    @GetMapping("/create")
    public ResponseEntity<TokenResponse> createToken() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Token",   tokenRedisService.addWaitingToken())
                .build();
    }

}
