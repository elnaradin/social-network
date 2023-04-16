package ru.itgroup.intouch.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itgroup.intouch.model.PostTypeEntity;

import javax.print.attribute.standard.MediaSize;
import java.util.Optional;

@Repository
public interface PostTypeRepository extends JpaRepository<PostTypeEntity, Integer> {

    Optional<PostTypeEntity> findByCodeIs(String code);

}
