package ru.itgroup.intouch.repository;

import model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PostTagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByName(String name);

    @Query(value = "SELECT id FROM post_tags WHERE name IN (:names)", nativeQuery = true)
    List<Long> findTagIdsByTagNames(@Param("names") String[] names);
}
