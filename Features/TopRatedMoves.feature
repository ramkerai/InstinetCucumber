Feature: Top Rated Movies

The "Top Rated Movies" page displays a list of theatrical films which have received the highest ratings on IMDB. This feature file describes the behavior of the top rated films page: http://www.imdb.com/chart/top

Scenario: Refining Movies List by Genre

On the Top Rated Movies page the user is capable of refining the list of titles based on a particular genre.

Given I load the Top Rated Movies page
And I refine by Genre "Comedy"
Then the list of movies should only contain relevant results

Scenario: Sorting Movies List (by Release Date)

The user is able to change the ordering of the list of titles based on the year in which the movie was released.

Given I load the Top Rated Movies page
And I sort the list by "Release Date"
Then the list of movies should be displayed in order of release date
