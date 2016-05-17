package com.bridge.pages.alibaba;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bridge.pages.BasePage;
import com.bridge.util.WebDriverUtil;

public class CategoryPage extends BasePage{
	private static final Log LOG = LogFactory.getLog(CategoryPage.class);
	
	@FindBy(css=".s-widget-features.sm-widget-row")
	List<WebElement> cateRows;
	
	@FindBy(css="ol.sm-widget-path > li:not(:first-of-type)")
	List<WebElement> mainCategory;
	
	@FindBy(className="sm-widget-offer")
	WebElement itemCount;
	
	@FindBy(xpath=".//script[contains(.,'categoryName')]")
	WebElement pageConfigScript;
	
	public CategoryPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public Object[][] getMostCategoryInfo(int level,Object[] lastLevelCategoryDate){
		LOG.info("level:"+level);
		WebDriverUtil.waitForElementPresent(driver, By.cssSelector(".sm-widget-path"), 10);
		
		String mainCategoryName = "/";
		List<String[]> categoryList = new ArrayList<String[]>();
				
		Object[][] categoryDate = null;
		
		//第四层只获取数量
		if(level == 4){
			//获得产品数量并插入数据库
			int itemCount = getItemCount();
			lastLevelCategoryDate[3] = itemCount;

			//插入上一层的数据
			lastLevelCategoryDate[4] = getPageConfig();
			aliCategoryDAO.insertThirdLevel(lastLevelCategoryDate);
		//大于第一层
		}else if(level > 1){
			mainCategoryName = (String) lastLevelCategoryDate[0];
			categoryList.addAll((List<String[]>) lastLevelCategoryDate[1]);
			int itemCount = getItemCount();
			lastLevelCategoryDate[3] = itemCount;
			
			//插入上一层的数据
			if(level == 2){
				aliCategoryDAO.insertFirstLevel(lastLevelCategoryDate);
			}else{
				lastLevelCategoryDate[4] = getPageConfig();
				aliCategoryDAO.insertSecondLevel(lastLevelCategoryDate);
			}
			
			//结果大于XXX就取下一层，数量可修改，不宜太少，否则会没法取到下一层
			if(itemCount >= 300)
				categoryDate = getCategoryData(mainCategoryName,categoryList);
		}else{
			for(WebElement path : mainCategory){
				mainCategoryName = mainCategoryName+"/"+path.getText();
			}
			mainCategoryName = mainCategoryName.replace("//", "");
			LOG.info("大类名："+mainCategoryName);
			categoryDate = getCategoryData(mainCategoryName,categoryList);
		}
		
		return categoryDate;
	}
	
	public Object[][] getCategoryData(String mainCategoryName,List<String[]> categoryList){

		WebElement mostCategory = getMostCategoryElement();
				
		String categoryName = mostCategory.findElement(By.className("sm-widget-title")).getText();
		LOG.info("细分方式："+categoryName);
		
		List<WebElement> items = mostCategory.findElements(By.cssSelector(".sm-widget-items >ul >li >a"));
				
		Object[][] categoryData = new Object[items.size()][5];
		
		int i=0;
		
		for(WebElement item : items){
			
			List<String[]> newCategoryList = new ArrayList<String[]>();
			newCategoryList.addAll(categoryList);
			
			String itemName = item.getAttribute("title");
			LOG.info("细分名："+itemName);
			categoryData[i][0] = mainCategoryName; 
			
			//将细分存入数组再放到list
			String[] currentCategory = {categoryName,itemName};
			
			newCategoryList.add(currentCategory);
			categoryData[i][1] = newCategoryList;

			String itemUrl = item.getAttribute("href");
			LOG.info("细分链接:"+itemUrl);
			
			categoryData[i][2] = itemUrl;
			
			i++;
		}
		
		return categoryData;
	}
	
	public String[] getPageConfig(){
		WebDriverUtil.waitForElementPresent(driver, By.id("sw-pageconfig"), 15);
		String pageScript = pageConfigScript.getAttribute("innerHTML");
		//LOG.info(pageScript);
		String[] pageConfigInfo = new String[4];
		String keywords = getStringByRegex(pageScript,"(?<=(\"keywords\":\"))[\u4e00-\u9fa5]+");
		LOG.info("keyword："+keywords);
		String categoryId = getStringByRegex(pageScript,"(?<=(\"categoryId\":\"))\\d+");
		LOG.info("categoryId："+categoryId);
		String categoryName = getStringByRegex(pageScript,"(?<=(\"categoryNameGbk\":\"))[\u4e00-\u9fa5]+");
		LOG.info("categoryName："+categoryName);
		String featureID = getStringByRegex(driver.getCurrentUrl(),"(?<=feature=)\\w+(((?:%3B)\\w+){1,2})?").replace("%3B", ";");
		LOG.info("featureID："+featureID);
		pageConfigInfo[0] = keywords;
		pageConfigInfo[1] = categoryId;
		pageConfigInfo[2] = categoryName;
		pageConfigInfo[3] = featureID;

		return pageConfigInfo;
	}
	
	public int getItemCount(){
		int count = getTotalPages(itemCount.getText());
		LOG.info(itemCount.getText());
		return count;
	}
	
	//获取最多的细分方式元素
	public WebElement getMostCategoryElement(){
		LOG.debug("get most category");
		WebElement mostCategory = null;
		
		int mostItemNumbers = 0;
		
		for(WebElement categoryRow : cateRows){
			List<WebElement> items = categoryRow.findElements(By.cssSelector(".sm-widget-items >ul >li"));
			if(items.size() > mostItemNumbers){
				mostCategory = categoryRow;
				mostItemNumbers = items.size();
			}
		}
		
		return mostCategory;
	}
	
}
