package plugin.data;
import core.category.CategoryCollection;
import core.category.CategoryManager;
import core.category.Data;
import core.plugin.DataPlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calls WeatherUnderground's API to compare the past 7 days' weather data and to see if there's a relationship
 * between the cities listed below.
 * Comparison categories are: City, Temperature, and Weather type.
 * Sample API_KEY -> 0f96c22cf42ae79f
 * Please don't use the API_KEY more than once a minute.
 */
public class WeatherDataPlugin implements DataPlugin{

    @Override
    public String getName() {
        return "City Weather Data";
    }

    @Override
    public boolean cacheEnabled() {
        return false;
    }

    @Override
    public void onRegister() {
        /* Do Nothing */
    }

    @Override
    public String getDescription() {
        return "A plugin that provides 3 categories of data: City, Temperature, and Weather Type.";
    }

    @Override
    public int getNumInputs() {
        return 1;
    }

    @Override
    public ArrayList<String> getInputDescription() {
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("The Wunderground API KEY (0f96c22cf42ae79f)");
        return descriptions;
    }

    @Override
    public CategoryCollection getData(List<String> inputs) {
        if (inputs.isEmpty()) {
            throw new IllegalArgumentException("Received no inputs!");
        }
        String API_KEY = inputs.get(0);
        CategoryManager manager = new CategoryManager();
        CategoryCollection collection = new CategoryCollection(manager);
        manager.registerCategory("City");
        manager.registerCategory("Weather");
        manager.registerCategory("Temp");

        Map<String, Document> docMap = new HashMap<>();
        try {
            docMap.put("Austin",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/TX/Austin.xml").get());
            docMap.put("Dallas",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/TX/Dallas.xml").get());
            docMap.put("Houston",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/TX/Houston.xml").get());
            docMap.put("San_Francisco",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/CA/San_Francisco.xml").get());
            docMap.put("San_Jose",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/CA/San_Jose.xml").get());
            docMap.put("San_Diego",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/CA/San_Diego.xml").get());
            docMap.put("Sacramento",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/CA/Sacramento.xml").get());
            docMap.put("Pittsburgh",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/PA/Pittsburgh.xml").get());
            docMap.put("New_York",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/NY/New_York.xml").get());
            docMap.put("Philadelphia",
                    Jsoup.connect("http://api.wunderground.com/api/"+API_KEY+"/forecast/q/PA/Philadelphia.xml").get());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        for (String s : docMap.keySet()) {
            if (!docMap.get(s).select("error").isEmpty()) {
                throw new IllegalArgumentException("Error in getting the API Data!");
            }
            collection.addData("City", s);
            for (Element el : docMap.get(s).select("icon")) {
                collection.addData("Weather", el.text());
                collection.addRelation("City", s, "Weather", el.text());
            }
            for (Element el : docMap.get(s).select("fahrenheit")) {
                Data d;
                //We'll bin the values, since we don't have enough data to use each individual
                //temperature degree.
                if (Integer.parseInt(el.text()) < 50) {
                    d = collection.addData("Temp", "<50");
                }
                else if (Integer.parseInt(el.text()) < 55) {
                    d = collection.addData("Temp", "50");
                }
                else if (Integer.parseInt(el.text()) < 60) {
                    d = collection.addData("Temp", "65");
                }
                else if (Integer.parseInt(el.text()) < 65) {
                    d = collection.addData("Temp", "60");
                }
                else if (Integer.parseInt(el.text()) < 70) {
                    d = collection.addData("Temp", "65");
                }
                else if (Integer.parseInt(el.text()) < 75) {
                    d = collection.addData("Temp", "70");
                }
                else if (Integer.parseInt(el.text()) < 80) {
                    d = collection.addData("Temp", "75");
                }
                else if (Integer.parseInt(el.text()) < 85) {
                    d = collection.addData("Temp", "80");
                }
                else if (Integer.parseInt(el.text()) < 90) {
                    d = collection.addData("Temp", "85");
                }
                else {
                    d = collection.addData("Temp", ">90");
                }
                collection.addRelation("City", s, d.getCategory(), d.getName());
            }
        }
        return collection;
    }
}
