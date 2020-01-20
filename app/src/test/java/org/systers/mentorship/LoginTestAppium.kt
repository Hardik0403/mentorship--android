package org.systers.mentorship

//import io.appium.java_client.MobileElement
//import io.appium.java_client.android.AndroidDriver
//import io.appium.java_client.android.AndroidElement
//import io.appium.java_client.pagefactory.AndroidBy
//import org.openqa.selenium.By
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.remote.CapabilityType
//import org.openqa.selenium.remote.DesiredCapabilities
//import org.openqa.selenium.remote.RemoteWebDriver

class LoginTestAppium {

//    lateinit var driver: AndroidDriver<AndroidElement>
//
//    @Before
//    fun setUp(){
//
//        var capabilities = DesiredCapabilities()
//        capabilities.setCapability("deviceName","20352759")
//        capabilities.setCapability("platformName","android")
//        capabilities.setCapability("appPackage","org.systers.mentorship")
//        capabilities.setCapability("appActivity",".view.activities.LoginActivity")
//
//        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"),capabilities)
//    }
//
//    @Test
//    fun testLoginWhenEmailIsUnverified(){
//
//        val unverifiedEmail = "rogap94658@etopmail.com"
//        val password = "********"
//
//        // Setting the values in the Username/Email and Password Fields.
//        driver.findElementById("org.systers.mentorship:id/etUsername").setValue(unverifiedEmail)
//        driver.findElementById("org.systers.mentorship:id/etPassword").setValue(password)
//
//        // Click the Login button
//        driver.findElementById("org.systers.mentorship:id/btnLogin").click()
//
//        // Waiting for the Login function to execute.
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
//
//        // Verifying if the Login button is still on the screen or not.
//        // If the Login button is not on the screen, that means that the user is being logged in and the test case fails.
//        Assert.assertNotNull(driver.findElementById("org.systers.mentorship:id/btnLogin"))
//    }
//
//    @Test
//    fun testLoginWhenPasswordIsWrong(){
//
//        val email = "hardikjh42@gmail.com"
//        val wrong_password = "wrong_passwd"
//
//        // Setting the values in the Username/Email and Password Fields.
//        driver.findElementById("org.systers.mentorship:id/etUsername").setValue(email)
//        driver.findElementById("org.systers.mentorship:id/etPassword").setValue(wrong_password)
//
//        // Click the Login button
//        driver.findElementById("org.systers.mentorship:id/btnLogin").click()
//
//        // Waiting for the Login function to execute.
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
//
//        // Verifying if the Login button is still on the screen or not.
//        // If the Login button is not on the screen, that means that the user is being logged in and the test case fails.
//        Assert.assertNotNull(driver.findElementById("org.systers.mentorship:id/btnLogin"))
//
//    }
//
//    @Test
//    fun testLoginWhenEmailIsNonExisting(){
//
//        val nonExistingEmail = "foyovif695@eliteseo.net"
//        val password = "********"
//
//        // Setting the values in the Username/Email and Password Fields.
//        driver.findElementById("org.systers.mentorship:id/etUsername").setValue(nonExistingEmail)
//        driver.findElementById("org.systers.mentorship:id/etPassword").setValue(password)
//
//        // Click the Login button
//        driver.findElementById("org.systers.mentorship:id/btnLogin").click()
//
//        // Waiting for the Login function to execute.
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
//
//        // Verifying if the Login button is still on the screen or not.
//        // If the Login button is not on the screen, that means that the user is being logged in and the test case fails.
//        Assert.assertNotNull(driver.findElementById("org.systers.mentorship:id/btnLogin"))
//
//    }
//
//    @Test
//    fun testLoginWhenUsernameIsNonExisting(){
//
//        val nonExistingUsername = "NonExisting Username"
//        val password = "********"
//
//        // Setting the values in the Username/Email and Password Fields.
//        driver.findElementById("org.systers.mentorship:id/etUsername").setValue(nonExistingUsername)
//        driver.findElementById("org.systers.mentorship:id/etPassword").setValue(password)
//
//        // Click the Login button
//        driver.findElementById("org.systers.mentorship:id/btnLogin").click()
//
//        // Waiting for the Login function to execute.
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
//
//        // Verifying if the Login button is still on the screen or not.
//        // If the Login button is not on the screen, that means that the user is being logged in and the test case fails.
//        Assert.assertNotNull(driver.findElementById("org.systers.mentorship:id/btnLogin"))
//
//    }
//
//    @Test
//    fun testLoginWhenUsernameAndPasswordAreCorrect(){
//
//        val username = "Hardikjh"
//        val password = "********"
//
//        // Setting the values in the Username/Email and Password Fields.
//        driver.findElementById("org.systers.mentorship:id/etUsername").setValue(username)
//        driver.findElementById("org.systers.mentorship:id/etPassword").setValue(password)
//
//        // Click the Login button
//        driver.findElementById("org.systers.mentorship:id/btnLogin").click()
//
//        // Waiting for the Login function to execute.
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
//
//        // Verifying if the BottomViewNavigation is now visible or not.
//        // If it is not visible, that means that the user is still on Login Screen (Login failed), and the test case fails.
//        Assert.assertNotNull(driver.findElementById("org.systers.mentorship:id/bottomNavigation"))
//
//    }
//
//    @Test
//    fun testLoginWhenEmailAndPasswordAreCorrect(){
//
//        val email = "hardikjh42@gmail.com"
//        val password = "********"
//
//        // Setting the values in the Username/Email and Password Fields.
//        driver.findElementById("org.systers.mentorship:id/etUsername").setValue(email)
//        driver.findElementById("org.systers.mentorship:id/etPassword").setValue(password)
//
//        // Click the Login button
//        driver.findElementById("org.systers.mentorship:id/btnLogin").click()
//
//        // Waiting for the Login function to execute.
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
//
//        // Verifying if the BottomViewNavigation is now visible or not.
//        // If it is not visible, that means that the user is still on Login Screen (Login failed), and the test case fails.
//        Assert.assertNotNull(driver.findElementById("org.systers.mentorship:id/bottomNavigation"))
//
//    }
//
//    @After
//    fun endup(){
//        //driver.quit()
//    }
}