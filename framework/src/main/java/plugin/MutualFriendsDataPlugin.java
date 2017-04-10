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
            while (scan.hasNext()) {
                String s = scan.nextLine();

            }
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
        collection.
        return null;
    }
}
