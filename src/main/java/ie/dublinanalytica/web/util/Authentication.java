package ie.dublinanalytica.web.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Utility class for authentication related tasks.
 */
public class Authentication {
  private static final Random RANDOM = new SecureRandom();
  private static final Base64.Encoder ENCODER = Base64.getUrlEncoder();
  private static final String JWT_ISSUER = "dublinanalytica";

  /**
   * Generates a random salt to use for hashing passwords.
   *
   * @return a random 16 byte salt
   */
  public static byte[] generateSalt() {
    byte[] salt = new byte[16];
    RANDOM.nextBytes(salt);
    return salt;
  }

  /**
   * Returns a salted and hashed password.
   *
   * @param password the user's password
   * @param salt the salt to use when hashing
   *
   * @return the salted and hashed password
   */
  public static byte[] hash(char[] password, byte[] salt) {
    PBEKeySpec spec = new PBEKeySpec(password, salt, 1000, 256);

    // Clear the password in memory
    Arrays.fill(password, '\0');

    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
    } finally {
      spec.clearPassword();
    }
  }

  /**
   * Checks whether the provided password and salt match the hashed password.
   *
   * @param password the user's password
   * @param salt the salt to use when hashing
   * @param expectedHash the expected hashed password
   *
   * @return true if the password and salt match the hashed password
   */
  public static boolean verifyPassword(char[] password, byte[] salt, byte[] expectedHash) {
    byte[] passwordHash = hash(password, salt);

    // Clear the password in memory
    Arrays.fill(password, '\0');

    return Arrays.equals(passwordHash, expectedHash);
  }

  /**
   * Generates a string to use as an authentication token.
   *
   * @return a random 32-byte base64 string
   */
  public static String generateAuthToken() {
    byte[] randomBytes = new byte[24];
    RANDOM.nextBytes(randomBytes);
    return ENCODER.encodeToString(randomBytes);
  }

  /**
   * Returns the secret to use for JWT Tokens, acquired from the environment variable.
   *
   * @return JWT Secret
   */
  public static String getSecret() {
    return System.getenv().get("SECRET");
  }

  /**
   * Creates a JWT Token with a given payload.
   *
   * @param payload The payload
   * @return The encoded JWT Token
   */
  public static String createJwtToken(Map<String, Object> payload) {
    return JWT.create()
        .withIssuer(JWT_ISSUER)
        .withPayload(payload)
        .sign(Algorithm.HMAC256(getSecret()));
  }

  /**
   * Decodes a JWT Token while verifying the signature.
   *
   * @param token the token to check
   * @return The decoded JWT Token
   */
  public static DecodedJWT decodeJwtToke(String token) {
    Algorithm algorithm = Algorithm.HMAC256(getSecret());
    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(JWT_ISSUER)
        .build();
    return verifier.verify(token);
  }
}