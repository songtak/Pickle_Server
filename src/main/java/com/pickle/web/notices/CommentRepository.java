package com.pickle.web.notices;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<NoticeComment, Long>, ICommentRepository {
}
