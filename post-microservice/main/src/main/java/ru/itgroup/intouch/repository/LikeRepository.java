package ru.itgroup.intouch.repository;

import model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Likes findByItemId(Long ItemId);
}
