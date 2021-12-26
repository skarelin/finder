package com.business.finder.user.application.archive;

import lombok.Value;

import javax.validation.constraints.NotBlank;

public interface ArchiveUserService {
    void archiveUser(ArchiveUserCommand command);

    @Value
    class ArchiveUserCommand {
        @NotBlank String email;
    }
}
