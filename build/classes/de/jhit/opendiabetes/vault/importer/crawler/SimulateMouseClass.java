package de.jhit.opendiabetes.vault.importer.crawler;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

public class SimulateMouseClass {

	public void startMouseClicks(String loginName, String loginPassword, String device, String pump, String sn, Logger logger)
			throws AWTException, InterruptedException, SecurityException, IOException {
		logger.info("Inside Class Simulate mouse and Method Startmagic");
		// TODO Auto-generated method stub
		Boolean BG = false;
		Boolean stick = false;
		Boolean minimedpump = false;
		Boolean paradigmpump = false;
		if (device.toLowerCase().equals("stick")) {
			stick = true;
		} else if (device.toLowerCase().equals("bgdevice")) {
			BG = true;
		} else {
			logger.info("Device not properly selected)");
			System.out.println("Device not properly selected, Try changing config file");
			return;
		}
		if (pump.toLowerCase().equals("minimed")) {  //Paradgim
			minimedpump = true;
		} else if(pump.toLowerCase().equals("paradigm"))
		{
			paradigmpump = true;
		} else{
			logger.info("Pump not properly selected, Try changing config file");
			System.out.println("Pump not properly selected, Try changing config file");
			return;
		}
		
		if(minimedpump){
		if (sn.length() != 10) {
			logger.info("SN Number should be of 10 characters (alpha numeric only), Because Minimed Pump is selected");
			System.out.println(
					"SN Number should be of 10 characters (alpha numeric only)" + ", Try changing config file");
			return;
		} else {
			for (int i = 0; i < sn.length(); i++) {
				char c = sn.charAt(i);
				if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a) {
					logger.info("SN Number should be of alpha numeric only");
					System.out.println("SN Number should be of alpha numeric only" + ", Try changing config file");
					return;
				}
			}

		}
	} else 	if(paradigmpump){
			if (sn.length() != 6) {
				logger.info("SN Number should be of 6 characters (numeric only), Because Paradigm Pump is selected");
				System.out.println(
						"SN Number should be of 6 characters (numeric only)" + ", Try changing config file");
				return;
			} else {
				for (int i = 0; i < sn.length(); i++) {
					char c = sn.charAt(i);
					if (c < 0x30 || c>0x39) {
						logger.info("SN Number should be only numeric only");
						System.out.println("SN Number should be of numeric only" + ", Try changing config file");
						return;
					}
				}

			}
		}
	
		try {
		
			String lang = null;
			int replacmentForKeyN = 0; // to clcik buttons depending on Website language 
			
			WebDriver driver = null; // selenium webdirver for Opening IE
			Robot robot = new Robot();
			logger.info("Inside Method Startmagic Before Desired Cap");
			DesiredCapabilities capabilities = null;
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			logger.info("(Inside Method Startmagic) DesiredCap filled ");
			try {
				URL url = null; // Url to get IEDriver from a Folder in Eclipse

				File fileWhereIEDriverislocated;	

				logger.info("(Inside Method Startmagic) Checking for IEDriver path ");				

				String location = SimulateMouseClass.class.getResource("SimulateMouseClass.class").toString();

				url = this.getClass().getResource("/IEDriverServer.exe");
				//System.out.println();
				
				/********************
				 * checking if program is ran from Jar
				 *  If program ran from Jar file
				 *  IEWebdriver.exe file is extracted from jar and saved to current location from where jar is ran
				 
				 */
				
				
				if (location.startsWith("rsrc:") || location.endsWith(".jar")
						&& !new File(location.substring(location.indexOf(':') + 1)).isDirectory()) {
					logger.info("(Inside Method Startmagic) Running from Jar file ");
					
					//System.out.println("using this.getclass "+ this.getClass().getClassLoader().getResourceAsStream("IEDriverServer.exe"));
					
					
				//	System.out.println("using new "+  SimulateMouseClass.class.getClass().getResourceAsStream("/IEDriver/IEDriverServer.exe"));
					
					 File filetemp = new File("TempIeWebdriver.exe");
					 if (!filetemp.exists()) {
					      InputStream link = (this.getClass().getClassLoader().getResourceAsStream("IEDriverServer.exe"));//   SimulateMouseClass.class.getClass().getResourceAsStream("/IEDriver/IEDriverServer.exe"));
					      Files.copy(link, filetemp.getAbsoluteFile().toPath());
					 }
					// System.out.println("file location "+ filetemp.getAbsoluteFile().toPath().toString());
										logger.info("(Inside Method Startmagic) Setthing IEDriver path from Extracted Jar ");
					fileWhereIEDriverislocated = new File(filetemp.getAbsoluteFile().toPath().toString());
					//fileWhereIEDriverislocated = new File(destExtractJarDir + "/IEDriverServer.exe");
				} 
				/********************
				 *  If program ran from IDE
				 *  IEWebdriver.exe file is directly taken from folder
				 
				 */
				
				else {
					logger.info("(Inside Method Startmagic) Setthing IEDriver path directly from Project folder ");                           
                                        
                                        
					if(url != null){
                                        fileWhereIEDriverislocated = Paths.get(url.toURI()).toFile();
                                        }
                                        else
                                        {
                                           String temp  =  Paths.get(".").toAbsolutePath().normalize().toString() + "\\IEDriver\\IEDriverServer.exe";
                                          
                                           fileWhereIEDriverislocated = new File(temp);
                                        }
                                      
				}

				if (fileWhereIEDriverislocated.exists()) {                                 
                                   
                                    System.setProperty("webdriver.ie.driver", fileWhereIEDriverislocated.getAbsolutePath());

					driver = new InternetExplorerDriver(capabilities);

					driver.manage().window().maximize();
					driver.get("https://carelink.minimed.eu/patient/entry.jsp?bhcp=1");
				} else {
					System.out.println( "IEDriver is not selected!!!!!! "
                                                + "Please check Folder IEDriver with IEDriverServer.exe is available in project");
					return;
				}

			} catch (Exception e) {
				logger.info("Inside catch of Class Simulate mouse and Method Startmagic");
				System.out.println(
						"Please Check if the correct IEDriver is selected OR IE settings are not correctly");
					return;
			}
			Boolean IsLoginPageOfCarelinkAvailable = driver.findElements(By.id("j_username")).size() > 0;
			if (IsLoginPageOfCarelinkAvailable) {
				driver.findElement(By.id("j_username")).sendKeys(loginName);
				driver.findElement(By.id("j_password")).sendKeys(loginPassword);
				Thread.sleep(2000);

				driver.findElement(By.id("j_password")).sendKeys(Keys.ENTER);
			} else {
				Thread.sleep(4000);
				if (driver.findElements(By.id("j_username")).size() > 0) {
					driver.findElement(By.id("j_username")).sendKeys(loginName);
					driver.findElement(By.id("j_password")).sendKeys(loginPassword);
					Thread.sleep(2000);

					driver.findElement(By.id("j_password")).sendKeys(Keys.ENTER);
				} else {
					Thread.sleep(6000);
					if (driver.findElements(By.id("j_username")).size() > 0) {
						driver.findElement(By.id("j_username")).sendKeys(loginName);
						driver.findElement(By.id("j_password")).sendKeys(loginPassword);
						Thread.sleep(2000);

						driver.findElement(By.id("j_password")).sendKeys(Keys.ENTER);
					} else {
						logger.info("Website has not fully loaded close and try running again");
						System.out.println( "Website has not fully loaded close and try running again");
						return;
					}
				}
			}
			Thread.sleep(4000);
			Boolean isUploadSectionPresent = driver.findElements(By.id("upload")).size() > 0;
			if (isUploadSectionPresent) {
				driver.findElement(By.id("upload")).sendKeys(Keys.ENTER);
			} else {
				Thread.sleep(6000);
				if (driver.findElements(By.id("upload")).size() > 0) {
					driver.findElement(By.id("upload")).sendKeys(Keys.ENTER);
				} else {
					Thread.sleep(8000);

					if (driver.findElements(By.id("upload")).size() > 0) {
						driver.findElement(By.id("upload")).sendKeys(Keys.ENTER);
					} else {
						logger.info("upload section has not fully loaded close IE and try running Java program again");
						System.out.println(
								"upload section has not fully loaded close IE and try running Java program again");
						return;
					}
				}
			}
			Thread.sleep(10000);

			Boolean CompleAutoClick = false;
			Thread.sleep(2000);
			try {
				lang = driver.findElement(By.tagName("html")).getAttribute("lang");

				LanguageClass Language = new LanguageClass();

				replacmentForKeyN = Language.getReplacment(lang, logger);
				if(replacmentForKeyN == 0)
				{
					System.out.println(
							"Language of User logged in is not supporetd by Carelink Java program!! \n"
							+ "Please try with user who has language as English or German");
					return;
				}

			} catch (Exception e) {
				logger.info("Site not fully loaded");
				System.out.println( "Site not fully loaded");
			}

			if (driver.findElements(By.tagName("object")).size() > 0) {
				CompleAutoClick = RunAppletWithButtonSimulation(sn, BG, stick,paradigmpump,minimedpump, robot, replacmentForKeyN, logger);

			} else {
				Thread.sleep(10000);
				if (driver.findElements(By.tagName("object")).size() > 0) {
					CompleAutoClick = RunAppletWithButtonSimulation(sn, BG, stick,paradigmpump,minimedpump, robot, replacmentForKeyN, logger);

				} else {
					Thread.sleep(12000);
					if (driver.findElements(By.tagName("object")).size() > 0) {
						CompleAutoClick = RunAppletWithButtonSimulation(sn, BG, stick,paradigmpump,minimedpump, robot, replacmentForKeyN, logger);

					} else {
						logger.info(
								"Applet section has not fully loaded close IE and try running again Java program again");
						System.out.println(
								"Applet section has not fully loaded close IE and try running again Java program again");
						return;
					}
				}

			}
				System.out.println("Applet Wrapper completed Sucessfully");
		} catch (Exception e) {
			logger.info("Please Check if the correct IEDriver is selected OR IE settings are not correctly");
			System.out.println(
					"Please Check if the correct IEDriver is selected OR IE settings are not correctly");
			System.out.println(
					"You cannot open IE browser instance if Protected Mode settings are not the same for all zones OR if the browser is zoomed.\n"
					+ "To resolve this, Open IE Browser and go to Internet Options windows.\n"
					+ "Click on Security tab and make sure 'Internet','Local Intranet','Trusted sites'\n"
					+ "and 'Restricted sites' have 'Enable Protected Mode' either checked or unchecked for all options.\n"
					+ "Apply and save the settings and re-run the test code. It should work.\n");
			return;
		} finally {
			
			Thread.currentThread().interrupt();

		}

	}

	private Boolean RunAppletWithButtonSimulation(String sn, Boolean BG, Boolean stick, Boolean paradigmpump, Boolean minimedpump, Robot robot, int replacmentForN,
			Logger logger) throws InterruptedException, AWTException {
		logger.info("Inside Method RunAppletWithButtonSimulation");
		
		Thread.sleep(500);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_TAB);

		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		Thread.sleep(2000);
		
		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);	
		
		if(minimedpump){
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_TAB);

		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_TAB);
		
		
		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		}
		
		if(paradigmpump){
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);

			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);
			
			
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
		}

		
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		
		
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);

		// Entering Value for Device
		Thread.sleep(2000);
		type(sn);
		
		// Fourth selecting ALT + N
		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);


			
			// System.out.println("stick is selected");
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(200);
			
			if (stick) {
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			 Thread.sleep(4000);
		}
			if (BG ) {
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(2000);
		}

		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		if(stick){
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(replacmentForN);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(replacmentForN);
			robot.keyRelease(replacmentForN);
			robot.keyRelease(replacmentForN);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
		}
		if (BG) {
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
		}
		return true;

	}

	private static void leftClick() throws AWTException {
		Robot robot = new Robot();
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(200);
	}

	private void type(int i) throws AWTException {
		Robot robot = new Robot();
		robot.delay(40);
		robot.keyPress(i);
		robot.keyRelease(i);
	}

	private static void type(String s) throws AWTException {
		Robot robot = new Robot();
		byte[] bytes = s.getBytes();
		for (byte b : bytes) {
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code > 96 && code < 123)
				code = code - 32;
			robot.delay(40);
			robot.keyPress(code);
			robot.keyRelease(code);
		}
	}

}
