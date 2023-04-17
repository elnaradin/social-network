package ru.itgroup.intouch.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itgroup.intouch.model.PostTagEntity;

import java.util.Optional;

@Repository
public interface PostTagRepository extends JpaRepository<PostTagEntity, Integer> {

    Optional<PostTagEntity> findByName(String name);
}
