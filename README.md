This framework will take in data from different categories. Two categories (category 1, category 2) will be chosen from these categories. Relationship among members of category 1 will be calculated from the ownership relationship between the members of category 1 and members of category2. The framework will then visualize the relationship among members of category 1. 

We illustrate this point by an example. Suppose category 1 are songs, category 2 are people. The data the framework takes in will have the following form

{
  {Love the way you lie : <Justin, Tianyu, Somebody else> },
  {Sing for the moment: <Justin, Tianyu>},
  {Yesterday once more: <Christian, Bogdan>}
}

This means <Justin, Tianyu, Sihao, Avi> likes the song Love the way you lie, 
           <Justin, Tianyu> likes the song Sing for the moment and
           <Christian, Bogdan> likes the song Yesterday once more.

From this representation of ownership relationship (the song owns its likers), we can 
tell that Love the way you lie is more similar to or more closely related to Sing for the moment instead of Yesterday once more because Love the way you lie and Sing for the moment have similar group of people liking it, i.e <Justin, Tianyu>. 

Based on the different ownership relationships, this framework quantifies the 
relationship strength among the members of category 1(i.e. the songs) and produce the 
following form of relationship:

              Love the way you lie
            /    				  \
           0.8					  0.0
          /							\	
    Sing for the moment -- 0.0 --   Sing for the moment

The edges are marked with numbers which mean the strength of the connections

Our framework will then visualize the above relationship among songs and do some 
more analysis based on that

