package com.loveloveshot.image.command.domain.repository;

import com.loveloveshot.image.command.domain.aggregate.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageCommandRepository extends JpaRepository<Image, Long> {
}
