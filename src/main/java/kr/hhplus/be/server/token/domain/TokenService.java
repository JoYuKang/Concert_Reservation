package kr.hhplus.be.server.token.domain;

public interface TokenService {

    Token getToken();

    Boolean checkToken(String tokenUUID);
}
