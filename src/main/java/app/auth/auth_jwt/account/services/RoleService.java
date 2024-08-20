package app.auth.auth_jwt.account.services;

import app.auth.auth_jwt.account.entities.Role;
import app.auth.auth_jwt.account.repositories.RoleRepository;
import app.auth.auth_jwt.shared.repositories.BaseRepository;
import app.auth.auth_jwt.shared.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class RoleService extends BaseService<Role,Long> {
   final RoleRepository roleRepository;
   final ModelMapper modelMapper;

    public RoleService(BaseRepository<Role, Long> repository, RoleRepository roleRepository, ModelMapper modelMapper) {
        super(repository);
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

}
