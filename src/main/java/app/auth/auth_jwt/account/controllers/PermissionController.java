package app.auth.auth_jwt.account.controllers;

import app.auth.auth_jwt.account.abilities.PermissionAbility;
import app.auth.auth_jwt.account.annotations.AuthAction;
import app.auth.auth_jwt.account.entities.Permission;
import app.auth.auth_jwt.account.services.PermissionService;
import app.auth.auth_jwt.shared.dto.http.DtoSearChewable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PermissionAbility._PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(name =PermissionAbility._ABILITY_TITLE, description = PermissionAbility._ABILITY_DESC)
public class PermissionController {
    final PermissionService permissionService;
    @GetMapping("generate-permissions")
    @Operation(summary = PermissionAbility._GENERATE_NAME, description = PermissionAbility._GENERATE_NAME)
    @AuthAction(abilityCode = PermissionAbility._GENERATE_CODE,abilityName = PermissionAbility._GENERATE_NAME, groupName = PermissionAbility._GROUP_NAME, groupCode =PermissionAbility._GENERATE_CODE)
    public ResponseEntity<List<Permission>> generate()  {
        return ResponseEntity.ok(permissionService.initAction()) ;
    }

    @GetMapping("")
    @Operation(summary = PermissionAbility._LIST_NAME, description = PermissionAbility._LIST_NAME)
    @AuthAction(abilityCode = PermissionAbility._LIST_CODE, abilityName =PermissionAbility._LIST_NAME , groupCode = PermissionAbility._GROUP_CODE,groupName = PermissionAbility._GROUP_NAME )
    public ResponseEntity<Page<Permission>> list(@ModelAttribute DtoSearChewable dtoSearChewable)  {
        return ResponseEntity.ok(permissionService.listPaginate(dtoSearChewable));
    }

    @GetMapping("{id}")
    @Operation(summary = PermissionAbility._SHOW_NAME, description = PermissionAbility._SHOW_NAME)
    @AuthAction(abilityCode = PermissionAbility._SHOW_CODE, abilityName =PermissionAbility._SHOW_NAME , groupCode = PermissionAbility._GROUP_CODE,groupName = PermissionAbility._GROUP_NAME )
    public ResponseEntity<Permission> show(@PathVariable("id") Long id)  {
        return ResponseEntity.ok(permissionService.getById(id).orElseThrow());
    }
}
