package core.plugin;

import core.data.AnalysisData;
import core.data.RelationshipData;

/**
 * Created by Justin on 4/8/2017.
 */
public interface AnalysisPlugin {
    //MAKE ANALYSIS PLUGIN TIED TO CERTAIN VISUALIZATION PLUGINS??? <----

    /**
     * @return The description of the type of analysis and what kind of data it will return.
     */
    String getDescription();

    /**
     * @param relationData
     * @return
     */
    AnalysisData getAnalysis(RelationshipData relationData);
}
