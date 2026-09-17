package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	WebDriver driver;
	public AccountRegistrationPage(WebDriver driver)
	{
		super(driver);
	}
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstname;
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastname;
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;

	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtcnfPassword;
	@FindBy(xpath="//input[@type='checkbox']")
	WebElement chkdPolicy;
	@FindBy(xpath="//input[@value='Continue']")
	WebElement btnContinue;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	public void SetFirstName(String fname)
	{
		txtFirstname.sendKeys(fname);
	}
	public void SetLastname(String lname)
	{
		txtLastname.sendKeys(lname);
	}
	public void SetEmail(String email)
	{
		txtEmail.sendKeys(email);
	}
	public void SetTelephone(String Telephone)
	{
		txtTelephone.sendKeys(Telephone);
	}
	public void SetPassword(String pwd)
	{
		txtPassword.sendKeys(pwd);
	}
	public void SetcnfPassword(String cnfpwd)
	{
		txtcnfPassword.sendKeys(cnfpwd);
	}
	public void SetPrivicyPolicy()
	{
		chkdPolicy.click();
	}
	public void clickContinue()
	{
		btnContinue.click();
	}
	
	public String getConfirmationMsg()
	{
		try
		{
			return (msgConfirmation.getText());
		}
		catch(Exception e)
		{
			return(e.getMessage());
		}
	}
}
