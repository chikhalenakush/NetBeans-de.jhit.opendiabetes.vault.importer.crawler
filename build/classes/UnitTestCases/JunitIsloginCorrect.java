package UnitTestCases;

import static org.junit.Assert.assertEquals;

import java.io.Console;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class JunitIsloginCorrect {
	public static String UserName;
	public static String Password;
	Boolean expected = false;
	private static Scanner keyboard;
	static Boolean UserInputTrueorFalse = false;
	private Map<String, String> loginCookies = new HashMap<String, String>();
	public static String UDERAGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

	@Parameterized.Parameters
	public static Collection Login() {
		System.out.println("Inside Test");
		keyboard = new Scanner(System.in);
		System.out.println("Enter UserName");
		String userNameFromUser = keyboard.next();
		String passwordFromUser = null;
		Console c = System.console();
		if (c == null) {
			System.err.println("No console.");

			final JPasswordField pf = new JPasswordField();
			passwordFromUser = JOptionPane.showConfirmDialog(null, pf, "Password", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(pf.getPassword()) : "";
		} else {

			System.out.println("Enter Passowrd");
			passwordFromUser = new String(c.readPassword("Password: "));
		}

		try {
			System.out.println("Is the login details correct? \n Type true for yes \n Type false for no");

			UserInputTrueorFalse = keyboard.nextBoolean();
		}

		catch (Exception e) {
			System.out.println("Entered Value is not Boolean");
			Login();

		}

		return Arrays.asList(new Object[][] {

				{ userNameFromUser, passwordFromUser, UserInputTrueorFalse },

		});
	}

	public JunitIsloginCorrect(String user, String pass, Boolean expected) {
		System.out.println("TestJunit-> UserName:" + user + " expected:" + expected);
		this.UserName = user;
		this.Password = pass;
		this.expected = expected;
	}

	@Test
	public void test() throws IOException, ParseException {

		Boolean Result = getLogin(UserName, Password);

		assertEquals(expected, Result);

	}

	private Boolean getLogin(String UserName, String Password) {
		// TODO Auto-generated method stub

		try {

			Connection.Response res = Jsoup.connect("https://carelink.minimed.eu/patient/j_security_check")
					.data("j_username", UserName).data("j_password", Password).method(Connection.Method.POST).execute();
			return true;
		} catch (Exception e) {
			System.out.println("Incorrect Username or Password");
			return false;
		}
	}

}
