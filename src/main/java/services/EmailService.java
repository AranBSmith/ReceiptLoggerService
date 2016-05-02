package services;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Email Service class used to verify emails.
 * 
 * @author Aran
 *
 */
public class EmailService {
    /**
     * Verify if an email is of the correct format to be an email.
     * 
     * @param email
     * @return true if email is authentic, false if otherwise.
     */
    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
