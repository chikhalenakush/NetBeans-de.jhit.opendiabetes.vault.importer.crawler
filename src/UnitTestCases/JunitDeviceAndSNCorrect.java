package UnitTestCases;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class JunitDeviceAndSNCorrect {
	public static String device;
	public static String sn;
	Boolean expected = false;
	private static Scanner keyboard;
	static Boolean UserInputTrueorFalse = false;
	Boolean Result = false;

	@Parameterized.Parameters
	public static Collection DetailsForApplet() {
		return Arrays.asList(new Object[][] { { "bgdevice", "1234567890", true }, { "stick", "12358974f0", true },
				{ "chutiy", "58745652590", false }, { "BGdevice", "125648$890", false } });
	}

	public JunitDeviceAndSNCorrect(String Device, String SN, Boolean Expected) {
		System.out.println("TestJunit-> Device:" + Device + " SN:" + SN + " Expected:" +Expected);
		this.device = Device;
		this.sn = SN;
		this.expected = Expected;
	}

	@Test
	public void test() throws IOException, ParseException {

		System.out.println("Expected result is " + expected);

		Result = (getDeviceSN(device, sn));

		assertEquals(expected, Result);

	}

	private Boolean getDeviceSN(String device, String sn) {
		// TODO Auto-generated method stub
		for (int i = 0; i < sn.length(); i++) {
			char c = sn.charAt(i);
			if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a) {
				
				return false;
			}
		}
		
		if ((device.toLowerCase().equals("stick") || device.toLowerCase().equals("bgdevice")) && sn.length() == 10) {

			return true;
		} else {
			return false;

		}

	}
}
