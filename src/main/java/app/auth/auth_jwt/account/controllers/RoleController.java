package app.auth.auth_jwt.account.controllers;

import app.auth.auth_jwt.account.abilities.RoleAbility;
import app.auth.auth_jwt.account.annotations.AuthAction;
import app.auth.auth_jwt.account.entities.Role;
import app.auth.auth_jwt.account.services.RoleService;
import app.auth.auth_jwt.shared.dto.http.DtoSearChewable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(RoleAbility._PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(name = RoleAbility._ABILITY_TITLE, description = RoleAbility._ABILITY_DESC)
public class RoleController {
    final RoleService roleService;

    @GetMapping("")
    @Operation(summary = RoleAbility._LIST_NAME, description = RoleAbility._LIST_NAME)
    @AuthAction(abilityCode = RoleAbility._LIST_CODE, abilityName =RoleAbility._LIST_NAME , groupCode = RoleAbility._GROUP_CODE,groupName = RoleAbility._GROUP_NAME )
    public Page<Role> list(@ModelAttribute DtoSearChewable dtoSearChewable)  {
       return roleService.listPaginate(dtoSearChewable);
    }

    @GetMapping("{id}")
    @Operation(summary = RoleAbility._SHOW_NAME, description = RoleAbility._SHOW_NAME)
    @AuthAction(abilityCode = RoleAbility._SHOW_CODE, abilityName =RoleAbility._SHOW_NAME , groupCode = RoleAbility._GROUP_CODE,groupName = RoleAbility._GROUP_NAME )
    public Role show(@PathVariable("id") Long id)  {
        return roleService.getById(id).orElseThrow();
    }
}
