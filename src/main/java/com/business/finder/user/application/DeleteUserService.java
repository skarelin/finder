package com.business.finder.user.application;

import com.business.finder.user.application.archive.ArchiveUserService;
import com.business.finder.user.application.archive.ArchiveUserService.ArchiveUserCommand;
import com.business.finder.user.application.exception.BfUserException;
import com.business.finder.user.application.port.DeleteUserUseCase;
import com.business.finder.user.db.BfUserRepository;
import com.business.finder.user.domain.BfUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
class DeleteUserService implements DeleteUserUseCase {

    private final BfUserRepository bfUserRepository;
    private final ArchiveUserService archiveUserService;

    @Override
    @Transactional
    public DeleteUserResponse deleteUser(String userEmail, User user) {
        Optional<BfUser> userForDelete = bfUserRepository.findByEmailIgnoreCase(user.getUsername());

        if (userForDelete.isPresent()) {
            if (user.getUsername().equalsIgnoreCase(userForDelete.get().getEmail())) {
                archiveUserService.archiveUser(new ArchiveUserCommand(user.getUsername()));
                bfUserRepository.delete(userForDelete.get());
                // TODO. What about partnership-proposals?
                return DeleteUserResponse.OK;
            } else {
                throw new BfUserException(String.format("Tried to delete user by email %s instead of %s", userForDelete.get().getEmail(), user.getUsername()));
            }
        } else {
            throw new BfUserException("BfUser is not present during deleting user use-case!");
        }
    }

}
