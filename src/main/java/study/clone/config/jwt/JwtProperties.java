package study.clone.config.jwt;

public interface JwtProperties {

  String SECRET = "ajiefjaslijaivsjadvasv";

  int EXPIRATION_TIME = 1800000;

  String TOKEN_PREFIX = "Bearer";

  String HEADER_STRING = "Authorization";
}
