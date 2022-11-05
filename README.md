Movie App
Star Movies is an upcoming online movie store. They offer services for streaming the movies online and provide offline mechanisms for rental etc. They have several customers spread who are purely online or offline or both. The existing system does not cater to the increasing needs - therefore they have come up with few sets of requirements in their first phase of implementation. They are as following:


The default path should show all the Movies available in the database.


User can register in the application.


User needs to login to add the Movie to favourites or download the movie.


User can see his/her favourite and downloaded movies.


Admin user to be created for adding / updating / deleting the movies.


All the requests after login should be parsed through the JWT token.



The sections of the Movie API are:
- Favourite Movie with Id #favorite.

- Downloaded Movie with ID #downloadedMovie

- Search: Search for a particular Movie or director 

- View all Favourite Movie cards under Favourite section

- View all Movie downloads under download section

Services to be created:
•	Movie-service
•	User-service
•	Authentication-service
•	Favourite-service
•	Movie-download-service

The Application needs to meet the below requirements
a)	Microservices Architecture to be followed
b)	Polyglot persistence to be used
c)	Every end point should be having postitive and negative scenarios (different http statuses)
d)	Authentication should be done before accessing endpoints
e)  JWT Filter should be applied for all the API calls of the secured endpoints.
f)  AOP & Swagger to be implemented
g)  Entire application should be containerized
h)  Register the services with Eureka
Entire Application should be deployed
