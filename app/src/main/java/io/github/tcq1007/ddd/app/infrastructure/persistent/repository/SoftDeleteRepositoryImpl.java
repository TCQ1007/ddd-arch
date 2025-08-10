package io.github.tcq1007.ddd.app.infrastructure.persistent.repository;

import io.github.tcq1007.ddd.app.infrastructure.persistent.entity.SoftDeleteable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.util.Lazy;
import org.springframework.data.util.Streamable;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import static org.springframework.data.jpa.repository.query.QueryUtils.*;

public class SoftDeleteRepositoryImpl<T extends SoftDeleteable,ID> extends SimpleJpaRepository<T,ID> {

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null";
    private static final String IDS_MUST_NOT_BE_NULL = "Ids must not be null";
    private static final String ENTITY_MUST_NOT_BE_NULL = "Entity must not be null";
    private static final String ENTITIES_MUST_NOT_BE_NULL = "Entities must not be null";

    public static final String DELETE_ALL_SQL = "update %s set %s = 1";
    public static final String DELETE_BATCH_BY_IDS = "update %s set %s = 1 where %s in :ids";
    public static final String DELETE_BY_ID_SQL = "update %s set %s = 1 where %s = :id";
    public static final String DELETE_PREFIX = "update %s set %s = 1";

    private final JpaEntityInformation<T, ID> entityInformation;
    private final EntityManager entityManager;
    private final PersistenceProvider provider;

    private final Lazy<String> deleteAllQueryString;
    private final Lazy<String> deleteAllByIdsQueryString;
    private final Lazy<String> deleteByIdQueryString;
    private final Lazy<String> deleteBatchPrefixQueryString;
    private final String softDeleteAttributeName;


    public SoftDeleteRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);

        this.softDeleteAttributeName = entityInformation.getJavaType().getDeclaredConstructor().newInstance().softDeleteAttributeName();
        this.deleteAllQueryString = Lazy
                .of(() -> String.format(DELETE_ALL_SQL, entityInformation.getEntityName(), this.softDeleteAttributeName));
        this.deleteAllByIdsQueryString = Lazy.of(()->String.format(DELETE_BATCH_BY_IDS, entityInformation.getEntityName(), this.softDeleteAttributeName, entityInformation.getIdAttributeNames()));
        this.deleteByIdQueryString =  Lazy.of(()->String.format(DELETE_BY_ID_SQL, entityInformation.getEntityName(),this.softDeleteAttributeName, entityInformation.getIdAttribute().getName()));
        this.deleteBatchPrefixQueryString =  Lazy.of(()->String.format(DELETE_PREFIX, entityInformation.getEntityName(),this.softDeleteAttributeName));
    }

    @Override
    public void setRepositoryMethodMetadata(CrudMethodMetadata metadata) {
        super.setRepositoryMethodMetadata(metadata);
    }

    @Override
    public void setEscapeCharacter(EscapeCharacter escapeCharacter) {
        super.setEscapeCharacter(escapeCharacter);
    }

    @Override
    public void setProjectionFactory(ProjectionFactory projectionFactory) {
        super.setProjectionFactory(projectionFactory);
    }

    @Override
    public long delete(Specification<T> spec) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> delete = builder.createCriteriaUpdate(getDomainClass());
        Root<T> root = delete.from(getDomainClass());
        delete.set(root.get(this.softDeleteAttributeName),"1");
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, builder.createQuery(getDomainClass()),
                    builder);

            if (predicate != null) {
                delete.where(predicate);
            }
        }

        return this.entityManager.createQuery(delete).executeUpdate();
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        entityManager.createQuery(deleteByIdQueryString.get()).setParameter("id", id).executeUpdate();
    }

    @Override
    @Transactional
    public void delete(T entity) {
        Assert.notNull(entity, ENTITY_MUST_NOT_BE_NULL);
        // 如果是新实体（未持久化），无需删除
        if (entityInformation.isNew(entity)) {
            return;
        }

        // 获取实体ID
        ID id = entityInformation.getId(entity);
        // 执行JPQL更新，设置软删除字段为1
        deleteById(id);

    }



    @Override
    @Transactional
    public void deleteAllById(Iterable<? extends ID> ids) {
        Assert.notNull(ids, IDS_MUST_NOT_BE_NULL);

        if (!ids.iterator().hasNext()) {
            return;
        }

        if (entityInformation.hasCompositeId()) {

            List<T> entities = new ArrayList<>();
            // generate entity (proxies) without accessing the database.
            ids.forEach(id -> entities.add(getReferenceById(id)));
            deleteAllInBatch(entities);
        } else {
            String queryString  = deleteAllByIdsQueryString.get();


            Query query = entityManager.createQuery(queryString);

            /*
             * Some JPA providers require {@code ids} to be a {@link Collection} so we must convert if it's not already.
             */
            query.setParameter("ids", toCollection(ids));

            applyQueryHints(query);

            query.executeUpdate();
        }

    }

    @Override
    @Transactional
    public void deleteAllByIdInBatch(Iterable<ID> ids) {
        deleteAllById(ids);
    }



    @Override
    @Transactional
    public void deleteAllInBatch(Iterable<T> entities) {
        Assert.notNull(entities, ENTITIES_MUST_NOT_BE_NULL);
        if (!entities.iterator().hasNext()) {
            return;
        }
        deleteAll(entities);
    }



    @Override
    @Transactional
    public void deleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, ENTITIES_MUST_NOT_BE_NULL);
        if (!entities.iterator().hasNext()) {
            return;
        }
        applyAndBind(deleteBatchPrefixQueryString.get(), entities, entityManager).executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAll() {
        deleteAllInBatch();
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        Query query = entityManager.createQuery(deleteAllQueryString.get());

        applyQueryHints(query);

        query.executeUpdate();
    }

    private static <T> Collection<T> toCollection(Iterable<T> ids) {
        return ids instanceof Collection c ? c : Streamable.of(ids).toList();
    }

    private void applyQueryHints(Query query) {

        if (getRepositoryMethodMetadata() == null) {
            return;
        }

        getQueryHints().withFetchGraphs(entityManager).forEach(query::setHint);
        applyComment(getRepositoryMethodMetadata(), query::setHint);
    }

    private void applyComment(CrudMethodMetadata metadata, BiConsumer<String, Object> consumer) {

        if (metadata.getComment() != null && provider.getCommentHintKey() != null) {
            consumer.accept(provider.getCommentHintKey(), provider.getCommentHintValue(getRepositoryMethodMetadata().getComment()));
        }
    }
}
