package Study.ApiStudy.config.jwt;

public interface JwtProperties {
    // 서버만 알고있는 시크릿 키
    String secret = "kyu9610";
    int EXPIRATION_TIME = 60000 * 10; // 10분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
