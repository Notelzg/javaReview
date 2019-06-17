package chapter20_Annotation;

public class PasswordUtils {
    @UseCase(id = 47, descriptin = "pawword must contain al least one numeric")
    public boolean validatePassword(String password){
        return (password.matches("\\w*\\d\\w*"));
    }
}
