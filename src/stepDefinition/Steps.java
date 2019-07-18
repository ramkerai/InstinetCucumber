package stepDefinition;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;


class HelperClass{
	static int getYear(String year){
		String format_year = year.replace("(", "");
		format_year = format_year.replace(")", "");
		int int_year = Integer.parseInt(format_year);
		
		return int_year;	
	}
	

	public static enum OrderValue {
		ASCENDING,
		DESCENDING
	}
	
	static boolean checkAscendingDescending( int current_value, int previous_value, OrderValue order_type)
	{
		boolean is_ascending= false;
		switch (order_type){
			case ASCENDING:
				if(current_value >= previous_value)
					is_ascending = true;
				
				break;
				
			case DESCENDING:
				if(current_value <= previous_value)
					is_ascending = false;
				
				break;
		}
		
		return is_ascending;
	}
	
	
}

public class Steps {
	
	WebDriver driver;
	
	String comedy_genre = "Comedy";
	static final String DESCENDING = "Descending";
	static final String ASCENDING = "Ascending";

	@Given("^I load the Top Rated Movies page$")
	public void I_load_the_Top_Rated_Movies_page()  {
		
		//System.setProperty("webdriver.chrome.driver",  "C://Selenium drivers//chromedriver.exe");
		System.setProperty("webdriver.chrome.driver",  "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.imdb.com/chart/top"); 
		//wait for page to load just incase it takes longer than useual.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    
	}

	@Given("^I refine by Genre \"([^\"]*)\"$")
	public void I_refine_by_Genre(String genre)  {
		comedy_genre = genre;
		driver.findElement(By.linkText(genre)).click();
	   
	}

	@Then("^the list of movies should only contain relevant results$")
	public void the_list_of_movies_should_only_contain_relevant_results()  {
	    String genre_string;
		// get number of items in list
		int rows = driver.findElements(By.xpath("//*[@id='main']/div/div[3]/div/div")).size();
		System.out.println("Total Number of Rows for comedy titles = " + rows);
		
		//loop through and check genre text contains 'comedy' string
		
		//COULDNT FIND THE WEB ELEMENT THAT GETS THE GENRE via XPATH. below xpath doesnt work!
		for (int i=1; i<=rows;i++){
			
			//genre_string =  driver.findElement(By.xpath("//*[@id='main']/div/div[3]/div/div[" +i + "]/div[3]/p[1]/span[5]").id("genre"))
			genre_string = "Action";  // ADDED to complete test.
			if ( !genre_string.contains(comedy_genre))
				Assert.assertTrue("Item number " + i +" not of comedy genre", false);
		}
	   
	}

	@Given("^I sort the list by \"([^\"]*)\"$")
	public void I_sort_the_list_by(String arg1)  {
		Select dropdown = new Select(driver.findElement(By.name("sort")));
		dropdown.selectByVisibleText(arg1);
	    
	    
	}

	@Then("^the list of movies should be displayed in order of release date$")
	public void the_list_of_movies_should_be_displayed_in_order_of_release_date()  {
		 
		int current_year, previous_year_value;
		String year_unformatted;
        
        int rows = driver.findElements(By.xpath("//*[@id='main']/div/span/div/div/div[3]/table/tbody/tr")).size();
  
		System.out.println("Total Number of Rows = " + rows);
		
		//int columns = driver.findElements(By.xpath("//*[@id='main']/div/span/div/div/div[3]/table/tbody/tr[1]/td")).size();
		//System.out.println("Total Number of columnc = " + columns);
		
		
		String ordering_type_string = driver.findElement(By.xpath("//*[@id='main']/div/span/div/div/div[3]/div/div/div[1]/span")).getAttribute("title");
		System.out.println("Ordering_type = " + ordering_type_string);
		
		//set initial value depending if descending or ascending
		previous_year_value=  ordering_type_string.contains(DESCENDING) ? 9999 :1111;
		
		for (int i=1; i<=rows;i++){
			year_unformatted =  driver.findElement(By.xpath("//*[@id='main']/div/span/div/div/div[3]/table/tbody/tr[" + i +"]/td[2]/span")).getText();
			current_year = HelperClass.getYear(year_unformatted);
			System.out.println( "Previous = " + previous_year_value +": Current Year = " + current_year);
		
			if (ordering_type_string.contains(DESCENDING))
			{
				if(!(current_year <= previous_year_value))
					Assert.assertTrue("Row number " + i +"not in correct release date order for " + DESCENDING, false); //throw assert if not in correct order
			}
			else //ascending
			{
				if(!(current_year >= previous_year_value))
					Assert.assertTrue("Row number " + i +"not in correct release date order for " + ASCENDING, false); //throw assert if not in correct order
				
			}
			
			// set current_year to previuos for next comparison in next loop
			previous_year_value = current_year;
		}
	
			
	}
		

	    
}

