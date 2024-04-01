package exam.spring.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
@Transactional
public class CommentRepositoryimpl {
	@Autowired
	private LocalSessionFactoryBean sessionFactory;
	
	@Override
	
	public List<Comment> getCommentsByProductId(int productId, int page )
	CriteriaBuider builder = session.getCriteriaBuider();
	CriteriaQuery<Comment>query = buider.createQuery(Comment.class);
	Root root = query.from(Comment.class);
	
	query = query.where(builder.equal(root.get("productId"),productId));
	query = query.orderBy(builder.desc(root.get(id)));
    
	Query q = session.createQuery(query);
	
	int max = 20 ;
	q.setMaxResults(max);
	q.setFirstResult(page - 1) * max
	
	return q.getResultList();
	}

}
