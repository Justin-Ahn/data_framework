# Relationship Analysis Framework <br /> (Team35 - Justin Ahn, Tianyu Gu)

## Project Goals
Analyze the relationship strength between two different data objects, using mutual ownership of other data objects 
to calculate the strength.
The end goal of the tool is to analyze relationships and maybe even discover new, unexpected relations between objects.
Some examples could be: 
* Comparing Subreddits
* Comparing Cities
* Comparing People
* Comparing anything with a defining characteristic
* Other things that we haven't thought of but are still cool

Some tangible examples are...<br />
Something like this: http://www.jacobsilterra.com/subreddit_map/network/index.html

or this: <br /> ![alt tag](http://www.seilevel.com/requirements/wp-content/uploads/2015/02/1-Alluvial-Diagram-Ex..png)

There are also other types of more basic statistical analyses you can do with the framework, since it provides additional analyses
of the relationship data as well as providing the relationship data.

## Building/Using the Example Plugins
Download the Plugin project (hw5-team-35/plugin) and run 'gradle run' on terminal inside the plugin project directory. Done!

## Installation Guide
Add this to your gradle.build on your Plugin project:
```
  repositories {
      mavenCentral()
      maven {
          url "http://ckaestne-ftp.andrew.cmu.edu/webdav/"
      }
  }

  dependencies {
      compile group: "cs214", name: "<name>", version: '<version>'
  }
  
  mainClassName = "core.main.Main"
```
Have two META-INF/services files:
<br />META-INF/services/core.plugin.DataPlugin
<br />META-INF/services/core.plugin.VisualizationPlugin
<br />And list your plugins in each respective service file.
'gradle run' should then fetch the framework & run the framework with your Plugins.

To add the framework dependency to Intellij without a .jar file of the project, add:
```
apply plugin: 'idea'
```
and run 'gradle idea'. There are other ways to do this as well.

## Framework Description & Details
This framework will take in data from different categories. 
Two arbitrary categories (category 1, category 2) will be chosen from those categories. 
Relationship Strengths among members of category 1 will be calculated from the mutual ownership of members of category 2.
The framework will then visualize and analyze the relationship among members of category 1. 

We illustrate this point by an example. Suppose category 1 are songs, category 2 are people. 
The data given from the data plugin is parsed into the following data structure called CategoryCollection
```
{
  {Love the way you lie : <Justin, Tianyu, anonymous 214 TA> },
  {Sing for the moment: <Justin, Tianyu>},
  {Yesterday once more: <Christian, Bogdan>}
}
```
This means <Justin, Tianyu, anonymous 214 TA> like the song "Love the way you lie", 
           <Justin, Tianyu> like the song "Sing for the moment" and
           <Christian, Bogdan> like the song "Yesterday once more".

From this representation of ownership (the song owns its likers), we can 
tell that "Love the way you lie" is closely related to "Sing for the moment", while "Yesterday once more" is not related either songs 
because "Love the way you lie" and "Sing for the moment" have a group of people liking both. <Justin, Tianyu>, whil

Based on the ownership relationships, this framework quantifies the 
relationship strength among the members of category 1(i.e. the songs) and produces the 
following form of relationship:
```
             Love the way you lie
              /    		 \
             0.8		 0.0
            /		          \	
Sing for the moment -- 0.0 --   Yesterday once more
```
The edges are marked with numbers which signify the strength of the connections

Internally, this is represented as a double map, i.e
```
{
  Love the way you lie : { Sing for the moment:0.8 }
  Sing for the moment : { Love the way you lie:0.8 }
  Yesterday once more : {}
}
```
The data structure that holds the double map is called RelationshipData.

Note we omit the connection if the strength of that connection is 0.0. (Connection is nonexistent)

This RelationshipData is then passed to the visualization plugin to be plotted.

An important notice is that we are not limiting the song to be category 1. 
We can also pick people to be category 1. By doing this, the CategoryCollection data structure will look like this
```
{
  {Justin : <Love the way you lie, Sing for the moment>},
  {Tianyu : <Love theway you lie, Sing for the moment>},
  {anonymous 214 TA : <Love the way you lie>},
  {Christian : <Yesterday once more>},
  {Bogdan : <Yesterday once more>}
}
```
Now, this means Justin likes the songs <Love the way you lie...>
                Tianyu likes... etc

By changing category 1 to people, we can now compare similarity of people.
We know Christian and Bogdan are more similar because they both like the song Yesterday once more.

The same algorithm will be used to generate the RelationshipData. 

Because we want the users to be able to switch category 1 during runtime without having to 
call the data plugin multiple times to get the same data, we design the CategoryCollection to 
actually look like this:
```
{
  {Love the way you lie : <Justin, Tianyu, anonymous 214 TA> },
  {Sing for the moment: <Justin, Tianyu>},
  {Yesterday once more: <Christian, Bogdan>}, 
  {Justin : <Love the way you lie, Sing for the moment>},
  {Tianyu : <Love the way you lie, Sing for the moment>},
  {anonymous 214 TA : <Love the way you lie>},
  {Christian : <Yesterday once more>},
  {Bogdan : <Yesterday once more>}
}
```
The songs and the people internally have different types (probably 'song', and 'person'). The program will be able to 
distinguish a song from a person because each instance of Data will remember the
type and name of that specific instance.

## Plugin Information

This is the flow of data seen by a typical process:

Vague Data --A--> CategoryCollection --B--> RelationshipData|AnalysisData --C--> JPanel --D--> GUI Visual

A: CategoryCollection compiled by Data Plugin<br />
B: Framework Uses Data Plugin's CategoryCollection to produce RelationshipData & AnalysisData<br />
C: Visualization Plugin takes in RelationshipData and AnalysisData to create a JPanel<br />
D: The FrameworkGUI displays the JPanel given by the VisualPlugin.<br />

### Data Plugin API
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


### A Quick Data Plugin Guide
1) Get the Data from whatever data source you need (Web, csv, image, etc.)
2) Register each category of Data to the CategoryManager
3) Add all Data points to the CategoryCollections. 
4) Create a relation between two Data points after the addition of all Data points.
5) Fill out all the other methods that needs implementation accordingly. 


### A Quick Visualization Plugin Guide
1) There are two data structures passed to the getVisual method: relation: RelationshipData and analysis: AnalysisData.
2) The RelationshipData contains the relationship map which can be obtained by calling relation.getRelationshipMap()
   The return type will be Map<Data,Map<Data,Double>>
3) The AnalysisData contains several analysis results done by the framework. 
   Specific results can be obtained by calling for example analysis.getAverageRelationStrength.
4) Developer can choose to use both relation and analysis or just one of them.
5) Developer is free to do computations based on relation and analysis.
6) Use whatever graphing libraries developer chooses to plot the result and add the plot to a JPanel
   then return the JPanel back to framework. (please make sure the plotting library is compatibale with JPanel)
   
## A Little Tutorial on the GUI
```
----Frame------------------------------------------------------------------------
|File  DataPlugin VisualPlugin DataCategory                                      |
|                                                                                |
|                                                                                |                                      
|                                                                                |
|                                                                                |
|                               __VISUAL__                                       |
|                                                                                |
|                                                                                |
|                                                                                |
|                                                                                |
|                                                                                |
|                                                                                |
---------------------------------------------------------------------------------                                                          
```

The Menu contains Four submenus - File, DataPlugin, VisualPlugin, and DataCategory.


1) We must explicitly choose which plugins to run through the framework to see the visualizataion.
Lets choose a DataPlugin first:
2) Some Data Plugins need some user inputs. The GUI will request those inputs on behalf on the Plugin when you select them.
3) Some Data Plugins need a lot of time to initialize. The GUI may become unresponsive for a couple seconds after the selection.
4) The DataCategory menu will be enabled after you select a DataPlugin. The DataCategory menu will enable you to choose which
    two data categories you want to select as the Node and the Link. You cannot select one category for both the Link and Node. However
    you can choose any two arbitrary categories as a Node and a Link. 
    
5) Choose a VisualPlugin to render the data from the DataPlugin with. 
6) Select "Apply Plugin" on the File Menu. You will then be able to visualize the data.



