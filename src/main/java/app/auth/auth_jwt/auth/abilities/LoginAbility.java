package app.auth.auth_jwt.auth.abilities;

public class LoginAbility {
   public final static String _PATH = "/api/v1/auth";
   public final static String _ABILITY_TITLE = "Authentification";
   public final static String _ABILITY_DESC = "Donne toutes opérations qui sont liées á la sécurité de l'application";
   public final static String _ABILITY_CODE = "AUTH:";
   public final static String _ABILITY_NAME = "Authentification";
   public final static String _GROUP_CODE = "SECURITY";
   public final static String _GROUP_NAME = "Sécurité";
   public final static String _LOGIN_CODE = _ABILITY_CODE + "LOGIN";
   public final static String _LOGIN_NAME = "S'Authentifier";
   public final static String _LOGIN_OTP_CODE = _ABILITY_CODE + "LOGIN_OTP";
   public final static String _LOGIN_OTP_NAME = "Validation otp login" ;
}
