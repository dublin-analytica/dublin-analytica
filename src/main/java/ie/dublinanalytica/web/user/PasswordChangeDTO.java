package ie.dublinanalytica.web.user;

/**
 * DTO for password change.
 */
public class PasswordChangeDTO {

  public char[] oldPassword;
  public char[] newPassword;

  public PasswordChangeDTO() {
  }

  public void setOldPassword(char[] oldPassword) {
    this.oldPassword = oldPassword;
  }

  public char[] getOldPassword() {
    return oldPassword;
  }

  public void setNewPassword(char[] newPassword) {
    this.newPassword = newPassword;
  }

  public char[] getNewPassword() {
    return newPassword;
  }
}
