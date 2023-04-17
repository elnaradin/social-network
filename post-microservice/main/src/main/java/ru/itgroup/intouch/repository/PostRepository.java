package ru.itgroup.intouch.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itgroup.intouch.model.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
