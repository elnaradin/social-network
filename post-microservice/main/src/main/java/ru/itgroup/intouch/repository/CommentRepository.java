package ru.itgroup.intouch.repository;

import model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByPostId(Long PostId, Pageable pageable);

    List<Comment> findALLByParentId(Long ParentId, Pageable pageable);
}
