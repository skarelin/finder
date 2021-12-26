package com.business.finder.user.application.archive;

import com.business.finder.user.db.BfArchiveUserRepository;
import com.business.finder.user.domain.BfArchiveUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArchiveUserServiceImpl implements ArchiveUserService {

    private final BfArchiveUserRepository repository;

    @Override
    public void archiveUser(ArchiveUserCommand command) {
        BfArchiveUser bfArchiveUser = new BfArchiveUser(command.getEmail());
        repository.save(bfArchiveUser);
    }
}
