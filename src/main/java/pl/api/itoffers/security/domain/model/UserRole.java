package pl.api.itoffers.security.domain.model;

public enum UserRole {
  ROLE_USER,
  ROLE_ADMIN;

  public static String[] getStandardRoles() {
    return new String[] {ROLE_USER.toString()};
  }
}
