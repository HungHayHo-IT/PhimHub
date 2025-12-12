package dao;

import java.util.List;

public interface DAOInterface<T> {
	
	public void insert(T entity);
	
	public void update(T entity);
	
	public void delete(Object id); // Sửa thành Object id để xóa cho dễ (truyền ID vào là xóa)
	
	public T findById(Object id); // Sửa void thành T để return đối tượng tìm được
	
	public List<T> findAll();
	
}