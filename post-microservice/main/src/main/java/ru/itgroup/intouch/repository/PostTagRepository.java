package ru.itgroup.intouch.repository;
import model.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {

    Optional<PostTag> findByName(String name);
}
