package com.business.finder.partnership.application.port;

import com.business.finder.partnership.application.PartnershipProposalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableFetchingPartnershipProposalUseCase {

    Page<PartnershipProposalResponse> fetch(Pageable pageable);

}
