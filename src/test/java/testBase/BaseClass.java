
	package testBase;

	import java.io.File;
	import java.io.FileReader;
	import java.io.IOException;
	import java.net.URL;
	import java.text.SimpleDateFormat;
	import java.time.Duration;
	import java.util.Date;
	import java.util.Properties;

	import org.apache.commons.lang3.RandomStringUtils;
	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;
	import org.openqa.selenium.Capabilities;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.openqa.selenium.edge.EdgeOptions;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.remote.AbstractDriverOptions;
	import org.openqa.selenium.remote.RemoteWebDriver;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Parameters;
	import org.testng.annotations.Test;

	public class BaseClass {
		public static WebDriver driver;
		public Logger logger;
		public Properties p;
	    @BeforeClass(groups={"Sanity","Regression","Master"})
	    @Parameters({"os","browser"})
	    
		public void setup(String os,String br) throws IOException
		{
	    	FileReader file =new FileReader("./src//test//resources//config.properties");
	    	p=new Properties();
	    	p.load(file);
	    	
	    	logger =LogManager.getLogger(this.getClass());
	    	
	    	if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

	    	    AbstractDriverOptions<?> options;

	    	    // 1. Select Browser First
	    	    switch (br.toLowerCase()) {
	    	        case "chrome":
	    	            options = new ChromeOptions();
	    	            break;

	    	        case "edge":
	    	            options = new EdgeOptions();
	    	            break;

	    	        default:
	    	            System.out.println("No matching browser: " + br);
	    	            return;
	    	    }

	    	    // 2. Apply OS Configuration to the selected browser options
	    	    if (os.equalsIgnoreCase("windows")) {
	    	        options.setPlatformName("windows");
	    	    } 
	    	    else if (os.equalsIgnoreCase("mac")) {
	    	        options.setPlatformName("mac");
	    	    } 
	    	    else if (os.equalsIgnoreCase("linux")) {
	    	        options.setPlatformName("linux");
	    	    } 
	    	    else {
	    	        System.out.println("No matching os: " + os);
	    	        return;
	    	    }

	    	    // 3. Instantiate RemoteWebDriver with the configured options
	    	    driver = new RemoteWebDriver(new URL("http://localhost:4444/"), options);
	    	}
	    	
	    	if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

	    	switch(br.toLowerCase())
	    	{
	    		case "chrome" :driver=new ChromeDriver();break;
	    		case "edge" :driver=new EdgeDriver();break;
	    		case "firefox" :driver=new FirefoxDriver();break;
	    		default:System.out.println("Invalid browser name..");return;

	    	}}
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			driver.get(p.getProperty("appURL1"));
			driver.manage().window().maximize();
		}
		@AfterClass
		public void tearDown()
		{
			driver.quit();
		}
		public String randomString()
		{
			String generatedstring =RandomStringUtils.randomAlphabetic(5);
			return generatedstring;
		}
		
		public String randomNumber()
		{
			String generatednumber =RandomStringUtils.randomNumeric(10);
			return generatednumber;
		}
		public String randomAlphaNumeric()
		{
			String generatedstring =RandomStringUtils.randomAlphabetic(3);
			String generatednumber =RandomStringUtils.randomNumeric(3);
			return generatedstring+"@"+generatednumber;
		}
		
		public String captureScreen(String tname) throws IOException {
		    
		    // Generates a timestamp to ensure each screenshot has a unique name
		    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		    // Casts the WebDriver instance to a TakesScreenshot object and captures the screen as a File
		    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		    // Defines the destination path for the screenshot, incorporating the test name (tname) and timestamp
		    String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		    File targetFile = new File(targetFilePath);

		    // Moves the temporary screenshot file to the target location
		    sourceFile.renameTo(targetFile);

		    // Returns the path so it can be attached to reports (like ExtentReports)
		    return targetFilePath;
		}
	}


