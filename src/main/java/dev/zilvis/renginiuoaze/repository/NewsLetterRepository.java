package dev.zilvis.renginiuoaze.repository;

import dev.zilvis.renginiuoaze.models.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsLetterRepository extends JpaRepository<NewsLetter, Long> {
}
