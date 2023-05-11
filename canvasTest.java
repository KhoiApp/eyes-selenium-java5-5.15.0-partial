package demo;


import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.Region;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.locators.VisualLocator;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.google.common.base.Stopwatch;

public class canvasTest {
    
    private static WebDriver driver;
    
	public static void main(String[] args) {
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");


			
			//options.addArguments("--headless","--disable-web-security");
			driver = new ChromeDriver(options);
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			initEyes();
			
			
			Stopwatch stopwatch = Stopwatch.createStarted();
			driver.get("https://kitchen.applitools.com/ingredients/canvas");
	        
	        eyes.open(driver,"DemoApp","Canvas Demo" );
	        
	        eyes.setViewportSize(driver, new RectangleSize(1200,900));
	        
	        
	        eyes.setLogHandler(new StdoutLogHandler(true));
	        
	        
	        eyes.check("before click", Target.window().fully());

	        Map<String, List<Region>> locators = eyes.locate(VisualLocator.name("LetsEatButton").name("deleteMe"));
	        
	        System.out.println(locators);
			
			Actions actions = new Actions(driver);
			
			Region letsEatButton = locators.get("LetsEatButton").get(0);

			actions.moveByOffset(
					letsEatButton.getLeft() + letsEatButton.getWidth() / 2,
					letsEatButton.getTop() + letsEatButton.getHeight() / 2).click().build().perform();
	        
	        eyes.check("after click", Target.window().fully());
	              
	        eyes.closeAsync();
	        
	        stopwatch.stop(); // optional

			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			System.out.println("time: " + millis);
	        
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
	        driver.quit();
	        
	     	// Print results
	        TestResultsSummary allTestResults = runner.getAllTestResults(false);
			System.out.println(allTestResults);
		
		}
		
        
	}
	
	//private static EyesRunner runner = new ClassicRunner();
	private static EyesRunner runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
    private static Eyes eyes = new Eyes(runner);
	
	
	
	private static Configuration config;
    private static BatchInfo batch;
	    
	private static void initEyes() {
		eyes.setApiKey("xxx"); //ENTER YOUR API KEY HERE
		
        config = eyes.getConfiguration();
        config.addBrowser(900, 600, BrowserType.CHROME);
        config.addBrowser(1200, 800, BrowserType.SAFARI);
        
       // config.setEnablePatterns(true);
        
        //config.setDisableBrowserFetching(true);
        
        
        /*
        
        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro_Max)); //latest version - currently 16
        //config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro_Max, IosVersion.ONE_VERSION_BACK)); //15.3
        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro));
        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_12_mini));
        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_12_mini, IosVersion.ONE_VERSION_BACK));//15.3
        config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.Pixel_2_XL, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.iPad_Pro, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.Galaxy_S20, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.Pixel_5, ScreenOrientation.PORTRAIT);
        */
        //config.setDisableBrowserFetching(false);
        //config.setViewportSize(new RectangleSize(600,600));
       // config.setWaitBeforeCapture(5000);
        
        
        //config.setDisableBrowserFetching(false);        
        eyes.setConfiguration(config);
        //eyes.setWaitBeforeScreenshots(200);
        
        eyes.setStitchMode(StitchMode.CSS);
        
        /*
        eyes.setConfiguration(config);
        String env = System.getenv("TEST_ENV");
        if(env == null) {
        	env = "local";
        }*/
        //batch = new BatchInfo(env);
        batch = new BatchInfo("Nightly");
        //batch.setId("nightly" + param);
        batch.setSequenceName("Nightly");
        batch.addProperty("appName", "Test");
        batch.addProperty("appName", "Test2");
        batch.setNotifyOnCompletion(true);
        
        eyes.setBatch(batch);
	}

}
