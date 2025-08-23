package itopia.resolar.domain.page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findAllBySubjectIdAndUserId(long subjectId, long userId);
    
    @Query("SELECT p FROM Page p WHERE p.user.id = :userId AND p.summary LIKE %:keyword% ORDER BY p.createdAt DESC")
    List<Page> findByUserIdAndSummaryContaining(@Param("userId") long userId, @Param("keyword") String keyword);
    
    @Query("SELECT p FROM Page p WHERE p.subject.id = :subjectId AND p.user.id = :userId AND p.summary LIKE %:keyword% ORDER BY p.createdAt DESC")
    List<Page> findBySubjectIdAndUserIdAndSummaryContaining(@Param("subjectId") long subjectId, @Param("userId") long userId, @Param("keyword") String keyword);
}
