package app.auth.auth_jwt.account.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthAction {
    String abilityCode();
    String abilityName();
    String groupCode() ;
    String groupName() ;
}
