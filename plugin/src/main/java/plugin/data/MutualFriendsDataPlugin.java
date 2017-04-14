package plugin.data;

import core.category.CategoryCollection;
import core.category.CategoryManager;
import core.plugin.DataPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Reads a .csv File that has a list of People. Those people know each other.
 * Ex:
 * Justin A = knows Tianyu G
 * Tianyu G = knows Justin Ahn
 * Christian K = doesn't know either Tianyu G or Justin Ahn.
 *
 * File Location -> src/main/resources/friends.csv
 */
public class MutualFriendsDataPlugin implements DataPlugin{
    private File f;

    private Scanner scan;

    @Override
    public String getName() {
        return "Mutual Friends List";
    }

    @Override
    public boolean cacheEnabled() {
        return true;
    }

    @Override
    public void onRegister() {
        /* Do Nothing */
    }

    @Override
    public int getNumInputs() {
        return 1;
    }

    @Override
    public ArrayList<String> getInputDescription() {
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("Path to the .csv File. (src/main/resources/friends.csv)");
        return descriptions;
    }

    @Override
    public String getDescription() {
        return "A plugin that provides a list of people & their respective friends through a .csv file";
    }

    /**
     * Provides the data from reading the .csv file. .csv file is located @ the resources directory.
     * @return The CategoryCollection of the Data that we got from the .csv file.
     */
    @Override
    public CategoryCollection getData(List<String> inputs) {
        try {
            f = new File(inputs.get(0));
            scan = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CategoryManager manager = new CategoryManager();
        CategoryCollection collection = new CategoryCollection(manager);
        manager.registerCategory("person");
        manager.registerCategory("friend");


        while (scan.hasNext ()) {
            String s = scan.nextLine();
            String [] sArray = s.split(",");
            collection.addData("person", sArray[0]);
            for (int i = 1; i < sArray.length; i++) {
                collection.addData("friend", sArray[i]);
                //Each person in columns[1->n] are the person @ column[0]'s friend.
                //So thus add a relation between those two people.
                collection.addRelation("person", sArray[0], "friend", sArray[i]);
            }
        }
        scan.close();
        return collection;
    }
}
