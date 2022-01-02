package com.business.finder.user.application;

import com.business.finder.upload.application.port.LocalPictureUploaderUseCase;
import com.business.finder.user.application.port.UploadUserProfilePictureUseCase;
import com.business.finder.user.db.BfProfilePictureRepository;
import com.business.finder.user.domain.BfProfilePicture;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadUserProfilePictureService implements UploadUserProfilePictureUseCase {

    @Value("${bf.user.profile.picture.folder}")
    private String userProfilePictureFolder;

    private final LocalPictureUploaderUseCase localPictureUploaderUseCase;
    private final BfProfilePictureRepository profilePictureRepository;

    @Override
    public Response upload(UploadUserProfilePictureCommand command) {
        final String fileName = command.getUserId().toString();
        LocalPictureUploaderUseCase.Response response = localPictureUploaderUseCase.upload(command.getFile(), fileName, userProfilePictureFolder);
        if (response.isSuccess()) {
            BfProfilePicture entity = new BfProfilePicture(fileName, command.getUserId());
            profilePictureRepository.save(entity);
        } else {
            throw new NotYetImplementedException("TODO");
        }
    }
}
