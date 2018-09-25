package top.jbzm.cloud.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jbzm.cloud.jwtsecurity.entity.Role;
import top.jbzm.cloud.jwtsecurity.entity.User;
import top.jbzm.cloud.jwtsecurity.repository.RoleRepository;
import top.jbzm.cloud.jwtsecurity.repository.UserRepository;
import top.jbzm.cloud.jwtsecurity.security.JwtTokenProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jbzm
 * @date 2018下午10:32
 **/
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("signin")
    public ResponseEntity signin(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return ResponseEntity.ok(jwtTokenProvider.generateToken(authenticate));
    }

    @PostMapping("signup")
    public ResponseEntity signup(String username, String password) {
        Set<Role> roles=new HashSet<>();
        Role role = roleRepository.findByRoleName("admin");
        roles.add(role);
        User user = User.builder().username(username).roles(roles).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}