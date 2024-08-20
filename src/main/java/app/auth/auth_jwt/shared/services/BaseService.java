package app.auth.auth_jwt.shared.services;

import app.auth.auth_jwt.shared.dto.http.DtoSearChewable;
import app.auth.auth_jwt.shared.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID extends Serializable> {
    private final BaseRepository<T, ID> repository;

    public BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }
    public Page<T> listPaginate(DtoSearChewable dtoSearChewable){
        return dtoSearChewable.isAnd()

                ?repository.filterByAttributes(dtoSearChewable.getFilters(), PageRequest.of(dtoSearChewable.getPage(),dtoSearChewable.getSize()))

                : repository.filterByAttributesOr(dtoSearChewable.getFilters(), PageRequest.of(dtoSearChewable.getPage(),dtoSearChewable.getSize()));
    }

    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public T store(T entity) {
        return repository.save(entity);
    }
    public T update(T entity) {
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
