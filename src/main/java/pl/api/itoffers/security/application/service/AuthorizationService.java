package pl.api.itoffers.security.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.User;

@Service
public class AuthorizationService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final BCryptPasswordEncoder passwordEncoder;

  public AuthorizationService(
      UserRepository userRepository, JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  public String getToken(String email, String password) {
    User user = userRepository.findUserByEmail(email);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      return null;
    }

    return jwtService.createToken(user);
  }
}
