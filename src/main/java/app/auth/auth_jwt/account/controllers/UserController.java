package app.auth.auth_jwt.account.controllers;

import app.auth.auth_jwt.account.abilities.UserAbility;
import app.auth.auth_jwt.account.annotations.AuthAction;
import app.auth.auth_jwt.account.dto.UserRequest;
import app.auth.auth_jwt.account.entities.User;
import app.auth.auth_jwt.account.services.UserService;
import app.auth.auth_jwt.shared.dto.http.DtoSearChewable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserAbility._PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(name = UserAbility._ABILITY_TITLE, description = UserAbility._ABILITY_DESC)
public class UserController {
    final UserService userService;

    @GetMapping("")
    @Operation(summary = UserAbility._LIST_NAME, description = UserAbility._LIST_NAME)
    @AuthAction(abilityCode = UserAbility._LIST_CODE, abilityName =UserAbility._LIST_NAME , groupCode = UserAbility._GROUP_CODE,groupName = UserAbility._GROUP_NAME )
    public Page<User> index(@ModelAttribute DtoSearChewable dtoSearChewable)  {
        return userService.listPaginate(dtoSearChewable);
    }

    @PostMapping("")
    @Operation(summary = UserAbility._ADD_NAME, description = UserAbility._ADD_NAME)
    @AuthAction(abilityCode = UserAbility._ADD_CODE, abilityName =UserAbility._ADD_NAME , groupCode = UserAbility._GROUP_CODE,groupName = UserAbility._GROUP_NAME )
    public ResponseEntity<User> store(@Valid @RequestBody UserRequest.AddRequest userRequest){
        return ResponseEntity.ok(userService.store(userRequest)) ;
    }
    @PutMapping("")
    @Operation(summary = UserAbility._UPDATE_NAME, description = UserAbility._UPDATE_NAME)
    @AuthAction(abilityCode = UserAbility._UPDATE_CODE, abilityName =UserAbility._UPDATE_NAME , groupCode = UserAbility._GROUP_CODE,groupName = UserAbility._GROUP_NAME )
    public ResponseEntity<User> update(@Valid @RequestBody UserRequest.UpdateRequest userRequest){
        return ResponseEntity.ok(userService.update(userRequest)) ;
    }

    @GetMapping("{id}")
    @Operation(summary = UserAbility._SHOW_NAME, description = UserAbility._SHOW_NAME)
    @AuthAction(abilityCode = UserAbility._SHOW_CODE, abilityName =UserAbility._SHOW_NAME , groupCode = UserAbility._GROUP_CODE,groupName = UserAbility._GROUP_NAME )
    public ResponseEntity<User> show(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getById(id).orElseThrow()) ;
    }

    @DeleteMapping("{id}")
    @Operation(summary = UserAbility._DELETE_NAME, description = UserAbility._DELETE_NAME)
    @AuthAction(abilityCode = UserAbility._DELETE_CODE, abilityName =UserAbility._DELETE_NAME , groupCode = UserAbility._GROUP_CODE,groupName = UserAbility._GROUP_NAME )
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
