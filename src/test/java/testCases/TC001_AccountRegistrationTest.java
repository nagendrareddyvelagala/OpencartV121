package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass{
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("******* Starting *******");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("******* clicked on MyAccount *******");
		hp.clickRegister();
		logger.info("******* clicked on Register *******");
		AccountRegistrationPage regpage =new AccountRegistrationPage(driver);
		
		logger.info("******* Providing customer details *******");
		regpage.SetFirstName(randomString().toUpperCase());
		regpage.SetLastname(randomString().toUpperCase());
		regpage.SetEmail(randomString()+"@gmail.com");
		regpage.SetTelephone(randomNumber());
		
		String Password =randomAlphaNumeric();
		regpage.SetPassword(Password);
		regpage.SetcnfPassword(Password);
		regpage.SetPrivicyPolicy();
		regpage.clickContinue();
		
		String confmsg =regpage.getConfirmationMsg();
		logger.info("******* Validaing expected message.. *******");
		
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed...");
			logger.debug("debug logs..");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch (Exception e)
		{
			
			Assert.fail();
		}
		logger.info("******* Starting TC001_AccountRegistrationTest *******");
	}
	
}
