package org.example.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Entities.UsersEntity;
import org.example.model.UsereEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component


@AllArgsConstructor
@Data
public class ValidationUtils {

    public static boolean validate(UsereEntityDto user){
        if(checkpassword(user.getPassword()) && checkemail(user.getEmail())){
            return true;
        }
        return false;

    }

    private static  boolean checkemail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Simple email regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    private static boolean checkpassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if ("!@#$%^&*()-_+=<>?/{}~|".indexOf(ch) >= 0) hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }


}
