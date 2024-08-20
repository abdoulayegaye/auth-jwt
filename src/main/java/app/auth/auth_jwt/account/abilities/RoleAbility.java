package app.auth.auth_jwt.account.abilities;

import app.auth.auth_jwt.shared.abilities.ActionNameCode;

public class RoleAbility  {
   public final static String _PATH = "/api/v1/account/roles";
   public final static String _ABILITY_TITLE = "Gestion des roles";
   public final static String _ABILITY_DESC = "Donne toutes opérations qui sont liées á la gestion des roles";
   public final static String _ABILITY_CODE = "ROLE:";
   public   final static String _ABILITY_NAME = "Role";
   public final static String _GROUP_CODE = "ACCOUNT";
   public final static String _GROUP_NAME = "Profilage";
   //ABILITIES ||
   public final static   String _ADD_CODE = _ABILITY_CODE + ActionNameCode.ADD_CODE;
   public final static   String _ADD_NAME = ActionNameCode.ADD_NAME +  _ABILITY_NAME;

   public final static   String _UPDATE_CODE = _ABILITY_CODE + ActionNameCode.UPDATE_CODE;
   public final static   String _UPDATE_NAME = ActionNameCode.UPDATE_NAME +  _ABILITY_NAME;

   public final static   String _DELETE_CODE = _ABILITY_CODE + ActionNameCode.DELETE_CODE;
   public final static   String _DELETE_NAME = ActionNameCode.DELETE_NAME +  _ABILITY_NAME;

   public final static   String _SHOW_CODE = _ABILITY_CODE + ActionNameCode.SHOW_CODE;
   public final static   String _SHOW_NAME = ActionNameCode.SHOW_NAME +  _ABILITY_NAME;

   public final static   String _LIST_CODE = _ABILITY_CODE + ActionNameCode.LIST_CODE;
   public final static   String _LIST_NAME = ActionNameCode.LIST_NAME +  _ABILITY_NAME;

   public final static   String _ENABLED_CODE = _ABILITY_CODE + ActionNameCode.ENABLED_CODE;
   public final static   String _ENABLED_NAME = ActionNameCode.ENABLE_NAME +  _ABILITY_NAME;

   public final static   String _DISABLED_CODE = _ABILITY_CODE + ActionNameCode.DISABLED_CODE;
   public final static   String _DISABLED_NAME = ActionNameCode.DISABLED_NAME +  _ABILITY_NAME;

}
