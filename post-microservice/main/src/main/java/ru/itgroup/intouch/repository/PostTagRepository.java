package ru.itgroup.intouch.repository;

import model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PostTagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByName(String name);
}
