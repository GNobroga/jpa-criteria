package app.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Uma classe genérica para persistir e excluir entidades
 * @E - Tipo da Entitidade
 */
public class GenericDAO<E> {

    private Class<E> clazz;

    private final EntityManager em;

    public GenericDAO(Class<E> clazz, EntityManager em) {
        this.clazz = clazz;
        this.em = em;
    }

    public GenericDAO<E> startTransaction() {
        em.getTransaction().begin();
        return this;
    }

    public GenericDAO<E> commit() {
        em.getTransaction().commit();
        return this;
    }

    public GenericDAO<E> rollback() {
        em.getTransaction().rollback();
        return this;
    }

    public GenericDAO<E> save(E object) {
        em.persist(object);
        return this;
    }

    public Optional<E> findById(Object id) {
        if (Objects.isNull(id)) return Optional.empty();
        return Optional.ofNullable(em.find(clazz, id));
    }

    public List<E> findAll() {
        String jpql = "from %s e".formatted(this.clazz.getName());
        TypedQuery<E> query = em.createQuery(jpql, clazz);
        List<E> result = query.getResultList();
        return Objects.isNull(result) ? new ArrayList<>() : result;
    }

    public List<E> findByParameters(String jpql, Map<String, Object> parameters) {
       try {
            var query = em.createQuery(jpql, clazz);
            for (Map.Entry<String, Object> param: parameters.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
            return query.getResultList();
       } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Collections.emptyList();
       }
    }

}
