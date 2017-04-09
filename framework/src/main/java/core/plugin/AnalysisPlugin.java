package core.plugin;

import core.data.AnalysisData;

/**
 * Created by Justin on 4/8/2017.
 */
public interface AnalysisPlugin {
    String getDescription();
    AnalysisData getAnalysis();
}
