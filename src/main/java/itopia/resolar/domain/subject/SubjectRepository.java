package itopia.resolar.domain.subject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByUserId(long userId);

    Optional<Subject> findByIdAndUserId(long id, long userId);
}


