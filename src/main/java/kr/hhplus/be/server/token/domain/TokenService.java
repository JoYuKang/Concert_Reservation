package kr.hhplus.be.server.token.domain;

import java.util.List;

public interface TokenService {

    Token get(String token);

    String create();

    List<Token> active(List<Token> tokens);

    Token expire(String token);

    List<Token> expireList(List<Token> tokens);

    List<Token> findInactiveTokens();

    List<Token> findExpiredTokens();

}
