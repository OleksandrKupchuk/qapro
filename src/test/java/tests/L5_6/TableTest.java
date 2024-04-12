package tests.L5_6;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.art.tests.L5_6.Utils.Utils;
import io.art.tests.L5_6.models.MapField;
import io.art.tests.L5_6.models.Task;
import io.art.tests.L5_6.models.TaskAnnotated;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.codeborne.selenide.Selenide.*;

public class TableTest {

    @Test
    public void tableTest() {
        List<Map<String, String>> resultTable = new LinkedList<>();
        open("file://"
                + this.getClass().getClassLoader().getResource("table2.html").getPath());

        List<String> headers = $$x("//table//th").texts();
        ElementsCollection rows = $$x("//table//tbody//tr");

        for (SelenideElement row : rows) {
            List<String> cellsTexts = row.$$x(".//td").texts();
            resultTable.add(Utils.zipToMap(headers, cellsTexts));
        }
        System.out.println(resultTable.get(6).get("Col 16"));
    }

//    @Test
//    public void seleniumTest() {
////        WebDriver driver = new ChromeDriver();
//        List<Map<String, String>> resultTable = new LinkedList<>();
//        driver.get("file://"
//                + this.getClass().getClassLoader().getResource("table2.html").getPath());
//        List<String> headers = driver.findElements(By.xpath("//table//th"))
//                .stream().map(WebElement::getText)
//                .toList();
//
//        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
//        for (WebElement row : rows) {
//            List<String> cellsTexts = row.findElements(By.xpath(".//td"))
//                    .stream().map(WebElement::getText)
//                    .toList();
//
//            resultTable.add(Utils.zipToMap(headers, cellsTexts));
//        }
//        System.out.println(resultTable.get(6).get("Col 16"));
//        driver.quit();
//    }

    @Test
    public void jSoupTableTests() {
        List<Map<String, String>> resultTable = new LinkedList<>();
        open("file://"
                + this.getClass().getClassLoader().getResource("table2.html").getPath());

        String outerHTML = $x("//table").attr("outerHTML");

        Document document = Jsoup.parse(outerHTML);   //meta class - copied doc

        List<String> headerTexts = document.selectXpath("//table//th").eachText();

        Elements rows = document.selectXpath("//table//tbody//tr");
        for (Element row : rows) {
            List<String> cellsTexts = row.selectXpath(".//td").eachText();
            resultTable.add(Utils.zipToMap(headerTexts, cellsTexts));
        }

        System.out.println(resultTable.get(6).get("Col 16"));
    }

    @Test
    public void dynamic() {
        open("http://uitestingplayground.com/dynamictable");

        SelenideElement table = $("[role=table]");
        Map<String, Map<String, String>> resultMap = new HashMap<>();

        List<String> headerText = table.$$("[role=columnheader").texts();

        SelenideElement groupRows = table.$$("[role=rowgroup]").last();

        ElementsCollection rows = groupRows.$$("[role=row]");

        for (SelenideElement row : rows) {
            List<String> texts = row.$$("[role=cell]").texts();
            String name = texts.get(0);
            resultMap.put(name, Utils.zipToMap(headerText.subList(1, headerText.size()),
                    texts.subList(1, texts.size())));
        }

        Map<String, String> chrome = resultMap.get("Chrome");
        System.out.println(chrome.get("CPU"));
        System.out.println(chrome.get("Network"));
        System.out.println(chrome.get("Memory"));
        System.out.println(chrome.get("Disk"));
        Selenide.screenshot("chrome");


        Map<String, String> firefox = resultMap.get("Firefox");
        System.out.println(firefox.get("CPU"));

        String text = $(".bg-warning").text();

        Assert.assertEquals("Chrome CPU: " + chrome.get("CPU"), text);

    }

    @Test
    public void dynamicObj() {
        open("http://uitestingplayground.com/dynamictable");

        SelenideElement table = $("[role=table]");

        List<String> headerText = table.$$("[role=columnheader").texts();

        SelenideElement groupRows = table.$$("[role=rowgroup]").last();

        ElementsCollection rows = groupRows.$$("[role=row]");

        List<Task> resultList = new ArrayList<>();

        for (SelenideElement row : rows) {
            Task task = new Task();
            for (int i = 0; i < headerText.size(); i++) {
                SelenideElement cell = row.$$("[role=cell]").get(i);
                String cellText = cell.text();
                switch (headerText.get(i).toLowerCase()) {
                    case "name":
                        task.setName(cellText.toLowerCase());
                        break;
                    case "cpu":
                       task.setCpu(Double.parseDouble(cellText.replaceAll("%", "")));
                        break;
                    case "memory":
                        task.setMemory(Task.parseCellToDouble(cellText));
                        break;
                    case "network":
                        task.setNetwork(Task.parseCellToDouble(cellText));
                        break;
                    case "disk":
                        task.setDisk(Task.parseCellToDouble(cellText));
                        break;
                }
            }
            resultList.add(task);
        }
        Task chrome = resultList.stream()
                .filter(p -> p.getName().equals("chrome"))
                .findFirst()
                .get();

        System.out.println(chrome.getName());
        System.out.println(chrome.getCpu());
        System.out.println(chrome.getNetwork());
        System.out.println(chrome.getMemory());
        System.out.println(chrome.getDisk());

    }

    @Test
    public void dynamicObjAnno() {
        open("http://uitestingplayground.com/dynamictable");

        SelenideElement table = $("[role=table]");

        List<String> headerTexts = table.$$("[role=columnheader").texts();

        SelenideElement groupRows = table.$$("[role=rowgroup]").last();

        ElementsCollection rows = groupRows.$$("[role=row]");

        List<TaskAnnotated> resultList = new ArrayList<>();

        for (SelenideElement row : rows) {
            TaskAnnotated task = new TaskAnnotated();
            for (int i = 0; i < headerTexts.size(); i++) {
                SelenideElement cell = row.$$("[role=cell]").get(i);
                String cellText = cell.text();
                String header = headerTexts.get(i);

                Field[] declaredFields = task.getClass().getDeclaredFields();

                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(MapField.class)) {
                        MapField annotation = field.getAnnotation(MapField.class);
                        if (annotation.columnName().equals(header)) {
                            if (field.getType().equals(Double.class)) {
                                Double value = Double.parseDouble(
                                        cellText.replaceAll("%", "")
                                        .split(" ")[0]);
                                try {
                                    field.set(task, value);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {
                                try {
                                    field.set(task, cellText);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            }
            resultList.add(task);
        }
                TaskAnnotated chrome = resultList.stream()
                .filter(p -> p.getName().equals("Chrome"))
                .findFirst()
                .get();

        System.out.println(chrome.getName());
        System.out.println(chrome.getCpu());
        System.out.println(chrome.getNetwork());
        System.out.println(chrome.getMemory());
        System.out.println(chrome.getDisk());
    }

    @Test
    public void dynamicObjAnnoOptimized() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        open("http://uitestingplayground.com/dynamictable");

        SelenideElement table = $("[role=table]");

        List<String> headerTexts = table.$$("[role=columnheader").texts();

        SelenideElement groupRows = table.$$("[role=rowgroup]").last();

        ElementsCollection rows = groupRows.$$("[role=row]");

        List<TaskAnnotated> resultList =  Utils.toList(rows, headerTexts, TaskAnnotated.class);


        TaskAnnotated chrome = resultList.stream()
                .filter(p -> p.getName().equals("Chrome"))
                .findFirst()
                .get();

        System.out.println(chrome.getName());
        System.out.println(chrome.getCpu());
        System.out.println(chrome.getNetwork());
        System.out.println(chrome.getMemory());
        System.out.println(chrome.getDisk());

    }

}
