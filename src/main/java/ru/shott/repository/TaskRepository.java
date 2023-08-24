package ru.shott.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.shott.domain.Task;

import java.util.List;

@Repository
public class TaskRepository {
    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Task> getAll(int offset, int limit){
        Query<Task> query = getSession().createQuery("from Task", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public int getCount(){
        Query<Long> query = getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    public Task getById(int id){
        Query<Task> query = getSession().createQuery("select t from Task t where t.id = :id", Task.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    public void saveOrUpdate(Task task){
        getSession().saveOrUpdate(task);
    }

    public void delete(Task task){
        getSession().delete(task);
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
