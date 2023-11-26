package com.gifthub.gifticon.repository;

import com.gifthub.gifticon.entity.GifticonStorage;
import com.gifthub.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GifticonStorageRepository extends JpaRepository<GifticonStorage, Long> {
    List<GifticonStorage> findGifticonStorageByUser(User user);
}