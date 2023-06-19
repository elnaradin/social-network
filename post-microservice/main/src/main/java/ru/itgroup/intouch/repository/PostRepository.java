package ru.itgroup.intouch.repository;

import model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findAll(Specification<Post> specification, Pageable pageable);

    @Query(value = "SELECT id FROM posts WHERE tag_id in (:tagIds )", nativeQuery = true)
    List<Long> findIdsByTagIds(@Param("tagIds") Long[] tagIds);

    Page<Post> findAllByAuthorId(Long authorId, Pageable pageable);
}
