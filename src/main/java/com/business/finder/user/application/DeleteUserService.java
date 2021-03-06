package com.business.finder.user.application;

import com.business.finder.user.application.archive.ArchiveUserService;
import com.business.finder.user.application.archive.ArchiveUserService.ArchiveUserCommand;
import com.business.finder.user.application.exception.BfUserException;
import com.business.finder.user.application.port.DeleteUserUseCase;
import com.business.finder.user.db.BfUserRepository;
import com.business.finder.user.domain.BfUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
class DeleteUserService implements DeleteUserUseCase {

    private final BfUserRepository bfUserRepository;
    private final ArchiveUserService archiveUserService;

    @Override
    @Transactional
    public DeleteUserResponse deleteUser(String userEmail, UserDetails user) {
        // TODO. We need to logout user.
        Optional<BfUser> userForDelete = bfUserRepository.findByEmailIgnoreCase(user.getUsername());

        if (userForDelete.isPresent()) {
            final String username = userForDelete.get().getEmail();
            if (user.getUsername().equalsIgnoreCase(username)) {
                log.info("Archive user: " + username);
                archiveUserService.archiveUser(new ArchiveUserCommand(user.getUsername()));

                log.info("Removing user from repository: " + username);
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
