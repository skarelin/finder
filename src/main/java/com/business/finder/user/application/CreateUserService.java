package com.business.finder.user.application;

import com.business.finder.user.application.port.CreateUserUseCase;
import com.business.finder.user.db.BfUserRepository;
import com.business.finder.user.domain.BfUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.business.finder.user.application.port.CreateUserUseCase.Error.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Slf4j
class CreateUserService implements CreateUserUseCase {

    private final BfUserRepository bfUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserCommand command) {
        if (bfUserRepository.findByEmailIgnoreCase(command.getEmail()).isPresent()) {
            return CreateUserResponse.error(USER_ALREADY_EXISTS);
        }
        BfUser bfUser = new BfUser(command.getEmail(),
                passwordEncoder.encode(command.getPassword()),
                command.getUserType());
        bfUserRepository.save(bfUser);
        log.info("Created new user: " + bfUser);
        return CreateUserResponse.OK;
    }
}
