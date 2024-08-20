package app.auth.auth_jwt.account.services;

import app.auth.auth_jwt.account.annotations.AuthAction;
import app.auth.auth_jwt.account.entities.Permission;
import app.auth.auth_jwt.account.repositories.PermissionRepository;
import app.auth.auth_jwt.shared.repositories.BaseRepository;
import app.auth.auth_jwt.shared.services.BaseService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class PermissionService extends BaseService<Permission, Long> {
    final PermissionRepository permissionRepository;
    final RequestMappingHandlerMapping handlerMapping;

    public PermissionService(BaseRepository<Permission, Long> repository, PermissionRepository permissionRepository, RequestMappingHandlerMapping handlerMapping) {
        super(repository);
        this.permissionRepository = permissionRepository;
        this.handlerMapping = handlerMapping;
    }

    public List<Permission> initAction() {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages("app")
                        .addScanners(new MethodAnnotationsScanner())
        );
        Set<Method> methods = reflections.getMethodsAnnotatedWith(AuthAction.class);
        List<Permission>  permissionCodes = new ArrayList<>();
        for (Method method : methods) {
            AuthAction annotation = method.getAnnotation(AuthAction.class);
            Permission permission = new Permission();
            permission.setAbilityCode(annotation.abilityCode());
            permission.setAbilityName(annotation.abilityName());
            permission.setGroupCode(annotation.groupCode());
            permission.setGroupName(annotation.groupName());
            permission = this.addPermissionExisting(permission);
            permissionCodes.add(permission );
        }
        return permissionCodes;
    }



    public AuthAction getAction(ServletRequest request)  {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // Obtenir la chaîne d'exécution du gestionnaire à partir de la requête en cours
        HandlerExecutionChain handlerChain;
        try {
            handlerChain = handlerMapping.getHandler(httpRequest);
        } catch (Exception e) {
            return null;
        }
        if(handlerChain ==null){
            return  null;
        }
        // Vérifier si la méthode de contrôleur est annotée avec la permission requise
        Method handlerMethod = ((HandlerMethod) handlerChain.getHandler()).getMethod();
        if (handlerMethod.isAnnotationPresent(AuthAction.class)) {
            return handlerMethod.getAnnotation(AuthAction.class);
        }
        return null;
    }
    public Permission addPermissionExisting(Permission permission) {
        Optional<Permission> existingPermission = permissionRepository.findByAbilityCode(permission.getAbilityCode());
        if (existingPermission.isPresent()) {
            Permission updatedPermission = existingPermission.get();
            updatedPermission.setAbilityName(permission.getAbilityName());
            updatedPermission.setGroupCode(permission.getGroupCode());
            updatedPermission.setGroupName(permission.getGroupName());
            permission = permissionRepository.save(updatedPermission);
        } else {
            permission = permissionRepository.save(permission);
        }
        return permission ;
    }

}
