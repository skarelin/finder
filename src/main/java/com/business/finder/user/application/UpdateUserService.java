package com.business.finder.user.application;

import com.business.finder.user.application.port.UpdateUserUseCase;
import com.business.finder.user.db.BfUserRepository;
import com.business.finder.user.domain.BfUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final BfUserRepository repository;

    @Override
    @Transactional
    public UpdateUserResponse update(UpdateUserCommand command) {
        return repository.findByEmailIgnoreCase(command.getUserEmail())
                .map(user -> {
                    updateFields(command, user);
                    return UpdateUserResponse.OK;
                })
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find username during updating the entity. Username: " + command.getUserEmail()));
    }

    private BfUser updateFields(UpdateUserCommand command, BfUser user) {
        if (command.getUserDescription() != null) {
            user.setDescription(command.getUserDescription());
        }
        return user;
    }
}
