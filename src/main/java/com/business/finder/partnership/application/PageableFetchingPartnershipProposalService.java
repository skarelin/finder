package com.business.finder.partnership.application;

import com.business.finder.partnership.application.port.PageableFetchingPartnershipProposalUseCase;
import com.business.finder.partnership.db.PartnershipProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PageableFetchingPartnershipProposalService implements PageableFetchingPartnershipProposalUseCase {

    private final PartnershipProposalRepository repository;
    private final PartnershipProposalMapper mapper;

    @Override
    public Page<PartnershipProposalResponse> fetch(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toPartnershipProposalResponse);
    }

}
