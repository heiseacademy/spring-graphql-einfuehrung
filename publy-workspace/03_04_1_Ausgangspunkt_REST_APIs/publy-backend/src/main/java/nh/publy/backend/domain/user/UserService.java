package nh.publy.backend.domain.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final RestTemplate restTemplate;
  private final String userServiceUrl;

  public UserService(@Value("${publy.userservice.url}") String userServiceUrl) {
    this.userServiceUrl = userServiceUrl;
    this.restTemplate = new RestTemplate();
  }

  public User findUser(String userId) {
    URI uri = UriComponentsBuilder.fromHttpUrl(this.userServiceUrl)
      .path("/users/{userId}")
      .build(userId);

    logger.debug("Loading user with Id '{}'", userId);

    ResponseEntity<User> response =
      restTemplate.exchange(uri,
        HttpMethod.GET,
        null,
        User.class);

    User user = response.getBody();

    logger.debug("Received user for id {}: {}", userId, user);
    return user;
  }
}
