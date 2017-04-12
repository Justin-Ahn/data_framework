package plugin.data;

import core.category.CategoryCollection;
import core.category.CategoryManager;
import core.plugin.DataPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads a .csv File that has a list of People. Those people know each other.
 * Ex:
 * Justin A = knows Tianyu G
 * Tianyu G = knows Justin Ahn
 * Christian K = doesn't know either Tianyu G or Justin Ahn.
 */
public class MutualFriendsDataPlugin implements DataPlugin{
    private static final String FILE_LOCATION = "src/main/resources/friends.csv";
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

    public MutualFriendsDataPlugin() {
        try {
            f = new File(FILE_LOCATION);
            scan = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
    public CategoryCollection getData() {
        CategoryManager manager = new CategoryManager();
        CategoryCollection collection = new CategoryCollection(manager);
        manager.registerCategory("Person");
        manager.registerCategory("Friend");


        while (scan.hasNext()) {
            String s = scan.nextLine();
            String [] sArray = s.split(",");
            collection.addData("Person", sArray[0]);
            for (int i = 1; i < sArray.length; i++) {
                collection.addData("Friend", sArray[i]);
                //Each person in columns[1->n] are the person @ column[0]'s friend.
                //So thus add a relation between those two people.
                collection.addRelation("Person", sArray[0], "Friend", sArray[i]);
            }
        }
        return collection;
    }
}
