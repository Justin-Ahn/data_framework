package plugin;

import core.category.CategoryCollection;
import core.plugin.DataPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Justin on 4/9/2017.
 */
public class MutualFriendsDataPlugin implements DataPlugin{
    private static final String FILE_LOCATION = "src/main/resources/friends.csv";
    private File f;
    private Scanner scan;

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

    @Override
    public CategoryCollection getData() {
        CategoryCollection collection = new CategoryCollection();

        while (scan.hasNext()) {
            String s = scan.nextLine();
            String [] sArray = s.split(",");
            collection.addCategory("person", sArray[0]);
            for (int i = 1; i < sArray.length; i++) {
                collection.addCategory("friend", sArray[i]);
                collection.addRelation("person", sArray[0], "friend", sArray[i]);
            }
        }
        System.out.println(collection);
        return collection;
    }

    public static void main(String [] args) {
        MutualFriendsDataPlugin plugin = new MutualFriendsDataPlugin();
        plugin.getData();
    }
}
