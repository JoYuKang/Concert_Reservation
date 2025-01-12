package kr.hhplus.be.server.token.interfaces.response;


import kr.hhplus.be.server.token.domain.Token;
import lombok.Getter;

@Getter
public class TokenResponse {

    String token;

    public TokenResponse(Token token) {
        this.token = token.getToken();
    }

}
