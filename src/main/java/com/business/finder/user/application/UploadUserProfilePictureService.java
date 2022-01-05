package com.business.finder.user.application;

import com.business.finder.upload.application.port.LocalPictureUploaderUseCase;
import com.business.finder.upload.application.port.LocalPictureUploaderUseCase.LocalPictureUploadedResponse;
import com.business.finder.user.application.port.UploadUserProfilePictureUseCase;
import com.business.finder.user.application.validator.UploadUserProfilePictureValidator;
import com.business.finder.user.db.BfProfilePictureRepository;
import com.business.finder.user.domain.BfProfilePicture;
import com.business.finder.user.domain.type.ProfilePictureExtension;
import com.business.finder.user.domain.type.ProfilePictureStatus;
import com.business.finder.user.domain.type.ProfilePictureStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadUserProfilePictureService implements UploadUserProfilePictureUseCase {

    @Value("${bf.user.profile.picture.folder}")
    private String userProfilePictureFolder;

    private final LocalPictureUploaderUseCase localPictureUploaderUseCase;
    private final BfProfilePictureRepository profilePictureRepository;
    private final UploadUserProfilePictureValidator validator;

    @Override
    @Transactional
    public UploadUserProfilePictureResponse upload(UploadUserProfilePictureCommand command) {
        final String fileName = command.getUserUuid();
        final ProfilePictureExtension fileExtension = getFileExtensionFrom(command.getFile());

        List<ErrorCode> errors = validator.validate(fileExtension);

        if (!errors.isEmpty()) {
            return UploadUserProfilePictureResponse.errors(errors);
        }

        BfProfilePicture entity = getOrCreateBfProfilePicture(command.getUserId());
        entity.setFileName(fileName);
        entity.setPictureStorage(ProfilePictureStorage.LOCAL);
        entity.setExtension(fileExtension);

        LocalPictureUploadedResponse response = localPictureUploaderUseCase.uploadAndReplace(command.getFile(), fileName, userProfilePictureFolder);

        if (response.isSuccess()) {
            entity.setStatus(ProfilePictureStatus.PICTURE_UPLOADED);
        }

        log.info("Added profile picture for user: " + command.getUserId());
        return UploadUserProfilePictureResponse.OK;
    }

    private ProfilePictureExtension getFileExtensionFrom(MultipartFile file) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return ProfilePictureExtension.valueOf(fileExtension.toUpperCase());
    }

    private BfProfilePicture getOrCreateBfProfilePicture(Long userId) {
        return profilePictureRepository
                .findByUserId(userId)
                .orElseGet(() -> profilePictureRepository.save(new BfProfilePicture(userId)));
    }
}
