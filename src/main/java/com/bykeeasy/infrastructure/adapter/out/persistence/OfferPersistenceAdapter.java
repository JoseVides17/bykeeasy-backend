package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.OfferRepositoryPort;
import com.bykeeasy.domain.model.Offer;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.OfferEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataOfferRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OfferPersistenceAdapter implements OfferRepositoryPort {

    private final SpringDataOfferRepository offerRepository;

    public OfferPersistenceAdapter(SpringDataOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Offer save(Offer offer) {
        OfferEntity entity = PersistenceMapper.toEntity(offer);
        OfferEntity saved = offerRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Offer> findById(String id) {
        return offerRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Offer> findByJourneyId(String journeyId) {
        return offerRepository.findByJourneyId(journeyId).stream()
                .map(PersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(String offerId, String status) {
        offerRepository.findById(offerId).ifPresent(entity -> {
            entity.setStatus(com.bykeeasy.domain.model.OfferStatus.valueOf(status));
            offerRepository.save(entity);
        });
    }
}
