package com.business.finder.user.application;

import com.business.finder.upload.application.port.LocalPictureUploaderUseCase;
import com.business.finder.upload.application.port.LocalPictureUploaderUseCase.LocalPictureUploadedResponse;
import com.business.finder.user.application.port.UploadUserProfilePictureUseCase;
import com.business.finder.user.application.validator.UploadUserProfilePictureValidator;
import com.business.finder.user.db.BfProfilePictureRepository;
import com.business.finder.user.domain.BfProfilePicture;
import com.business.finder.user.domain.type.ProfilePictureStatus;
import com.business.finder.user.domain.type.ProfilePictureStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadUserProfilePictureService implements UploadUserProfilePictureUseCase {

    @Value("${bf.user.profile.picture.folder}")
    private String userProfilePictureFolder;

    private final LocalPictureUploaderUseCase localPictureUploaderUseCase;
    private final BfProfilePictureRepository profilePictureRepository;
    private final UploadUserProfilePictureValidator validator;

    @Override
    @Transactional
    public UploadUserProfilePictureResponse upload(UploadUserProfilePictureCommand command) {
        final String fileName = command.getUserId().toString();
        final String fileExtension = getFileExtensionFrom(command.getFile());

        List<ErrorCode> errors = validator.validate(fileExtension);

        if (!errors.isEmpty()) {
            return UploadUserProfilePictureResponse.errors(errors);
        }

        BfProfilePicture entity = new BfProfilePicture(fileName, command.getUserId(), ProfilePictureStorage.LOCAL);
        BfProfilePicture savedEntity = profilePictureRepository.save(entity);

        LocalPictureUploadedResponse response = localPictureUploaderUseCase.upload(command.getFile(), fileName, userProfilePictureFolder);

        if (response.isSuccess()) {
            savedEntity.setStatus(ProfilePictureStatus.PICTURE_UPLOADED);
        }

        return UploadUserProfilePictureResponse.OK;
    }

    private String getFileExtensionFrom(MultipartFile file) {
        return StringUtils.getFilenameExtension(file.getOriginalFilename());
    }
}
