package com.pickle.web.notices;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, INoticeRepository {
    int countByCategory(String category);
}
