package itopia.resolar.domain.page;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findAllBySubjectIdAndUserId(long subjectId, long userId);
}
