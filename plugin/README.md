# Plugin Information

This is the flow of data seen by a typical process:

Vague Data --A--> CategoryCollection --B--> RelationshipData|AnalysisData --C--> JPanel --D--> GUI Visual

A: CategoryCollection compiled by Data Plugin<br />
B: Framework Uses Data Plugin's CategoryCollection to produce RelationshipData & AnalysisData<br />
C: Visualization Plugin takes in RelationshipData and AnalysisData to create a JPanel<br />
D: The FrameworkGUI displays the JPanel given by the VisualPlugin.<br />

# Data Plugin API
* String getName() <br/>
The name of the data plugin. (A couple of words)
* String getDescription() <br/>
The description of the data plugin. (1 sentence)
* boolean cacheEnabled() <br/>
Enable the cache for faster calculations.
* void onRegister() <br/>
Run any needed startup code when the plugin is registered.
* int getNumInputs() <br/>
The number of inputs the plugin needs.
* ArrayList<String> getInputDescription(); <br/>
The description of each input the plugin needs.
* CategoryCollection getData(List<String> inputs); <br/>
The data structure for the framework's usage compiled by the plugin.


# A Quick Data Plugin Guide
1) Get the Data from whatever data source you need (Web, csv, image, etc.)
2) Register each category of Data to the CategoryManager
3) Add all Data points to the CategoryCollections. 
4) Create a relation between two Data points after the addition of all Data points.
5) Fill out all the other methods that needs implementation accordingly. 


# A Quick Visualization Guide
1) There are two data structures passed to the getVisual method: relation: RelationshipData and analysis: AnalysisData.
2) The RelationshipData contains the relationship map which can be obtained by calling relation.getRelationshipMap()
   The return type will be Map<Data,Map<Data,Double>>
3) The AnalysisData contains several analysis results done by the framework. 
   Specific results can be obtained by calling for example analysis.getAverageRelationStrength.
4) Developer can choose to use both relation and analysis or just one of them.
5) Developer is free to do computations based on relation and analysis.
6) Use whatever graphing libraries developer chooses to plot the result and add the plot to a JPanel
   then return the JPanel back to framework. (please make sure the plotting library is compatibale with JPanel)
   
   





