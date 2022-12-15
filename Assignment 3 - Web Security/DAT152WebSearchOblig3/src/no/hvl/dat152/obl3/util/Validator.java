package no.hvl.dat152.obl3.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.WhitespaceRule;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

public class Validator {

	public static String validString(String parameter) {
		return parameter != null ? parameter : "null";
	}
	
	public static String validInput(String parameter) {
		try {
			parameter = ESAPI.validator().getValidInput("name", parameter, "HTTPParameterValue", 400, false);
		} catch (IntrusionException | ValidationException e) {
			e.printStackTrace();
		}
		return ESAPI.encoder().encodeForHTML(parameter);
	}
	
	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	public static Boolean validPassword(String password) {
		PasswordValidator validator = new PasswordValidator(
		  // length between 8 and 16 characters
		  new LengthRule(8, 16),

		  // at least one upper-case character
		  new CharacterRule(EnglishCharacterData.UpperCase, 1),

		  // at least one lower-case character
		  new CharacterRule(EnglishCharacterData.LowerCase, 1),

		  // at least one digit character
		  new CharacterRule(EnglishCharacterData.Digit, 1),

		  // at least one symbol (special character)
		  new CharacterRule(EnglishCharacterData.Special, 1),

		  // define some illegal sequences that will fail when >= 5 chars long
		  // alphabetical is of the form 'abcde', numerical is '34567', qwery is 'asdfg'
		  // the false parameter indicates that wrapped sequences are allowed; e.g. 'xyzabc'
		  new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
		  new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
		  new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),

		  // no whitespace
		  new WhitespaceRule());
		
		return validator.validate(new PasswordData(password)).isValid();
	}
}
