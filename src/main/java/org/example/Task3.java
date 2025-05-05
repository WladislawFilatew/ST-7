package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task3 {

    public static void getMeteo() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");
            System.out.println(webDriver.getPageSource());
            WebElement elem = webDriver.findElement(By.tagName("pre"));

            String json_str = elem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json_str);
            JSONObject hourly = (JSONObject) obj.get("hourly");

            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray rains = (JSONArray) hourly.get("rain");
            JSONArray temperaturs = (JSONArray) hourly.get("temperature_2m");

            String[] headers = {"№", "Дата/время", "Температура", "Осадки (мм)"};

            String rowFormat = "| %-" + 5 + "s " +
                    "| %-" + 20 + "s " +
                    "| %-" + 15 + "s " +
                    "| %-" + 13 + "s |%n";

            String line = "+" + "-".repeat( 7) +
                    "+" + "-".repeat(22) +
                    "+" + "-".repeat(17) +
                    "+" + "-".repeat(14) + "+";

            System.out.println(line);
            System.out.format(rowFormat, headers[0], headers[1], headers[2], headers[3]);
            System.out.println(line);

           for (int i = 0; i < times.size(); ++i) {
               String time = times.get(i).toString();
               String rain = rains.get(i).toString();
               String temperatura = temperaturs.get(i).toString();
               System.out.format(rowFormat, i + 1, time, rain, temperatura);
           }

            System.out.println(line);


        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }
}
