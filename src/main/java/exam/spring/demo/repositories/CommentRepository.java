package exam.spring.demo.repositories;

public interface CommentRepository {
	List<Comment> getCommentsByProductId(int productId, int page)

}
