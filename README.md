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
This means <Justin, Tianyu, anonymous 214 TA> likes the song Love the way you lie, 
           <Justin, Tianyu> likes the song Sing for the moment and
           <Christian, Bogdan> likes the song Yesterday once more.

From this representation of ownership (the song owns its likers), we can 
tell that Love the way you lie is more closely related to Sing for the moment instead of Yesterday once more
because Love the way you lie and Sing for the moment have similar group of people liking it, i.e. <Justin, Tianyu>. 

Based on the ownership relationships, this framework quantifies the 
relationship strength among the members of category 1(i.e. the songs) and produce the 
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
The songs and the people internally have the type Data. The program will be able to 
distinguish a song from a person because each instance of a Data will remember the
actual type and name of that specific instance.


























