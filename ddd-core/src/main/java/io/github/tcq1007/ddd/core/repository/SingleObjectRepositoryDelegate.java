package io.github.tcq1007.ddd.core.repository;


import java.util.Optional;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;

public abstract class SingleObjectRepositoryDelegate<BO, Entity, ID, ER extends BaseEntityRepository<Entity, ID>> {

    public SingleObjectRepositoryDelegate(ER entityRepository, ConversionService conversionService) {
        this.entityRepository = entityRepository;
        this.conversionService = conversionService;
    }

    protected final ER entityRepository;

    protected final ConversionService conversionService;

    protected ER getEntityRepository() {
        return entityRepository;
    }

    protected ConversionService getConversionService() {
        return conversionService;
    }

    @SuppressWarnings("unchecked")
    public <T, Q> Specification<T> specification(Q query) {
        return Optional.ofNullable(query)
                .map(e -> conversionService.convert(query, Specification.class))
                .orElse(null);
    }

    public <Q> boolean hasSpecification(Q query) {
        return Optional.ofNullable(query)
                .map(e -> conversionService.canConvert(query.getClass(), Specification.class))
                .orElse(false);
    }

    public abstract Class<BO> getBOClass();

    public abstract Class<Entity> getEntityClass();
}
