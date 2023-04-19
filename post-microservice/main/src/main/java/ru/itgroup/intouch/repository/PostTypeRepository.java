package ru.itgroup.intouch.repository;
import model.post.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
    Optional<PostType> findByCodeIs(String code);
}
