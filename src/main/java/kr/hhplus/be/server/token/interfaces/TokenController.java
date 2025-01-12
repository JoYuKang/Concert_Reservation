package kr.hhplus.be.server.token.interfaces;

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

    // 토큰 조회
    @GetMapping("/{UUID}")
    public ResponseEntity<TokenResponse> getToken(@PathVariable("UUID") String UUID) {
        return new ResponseEntity<>(new TokenResponse(tokenService.get(UUID)), HttpStatus.OK);
    }

    // 토큰 생성
    @GetMapping("/create")
    public ResponseEntity<TokenResponse> createToken() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Token",   tokenService.create())
                .build();
    }

}
