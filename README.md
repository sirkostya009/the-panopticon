Prerequisites:
* IntelliJ IDEA
* JDK 17
* Docker

***

First, clone this repo to any folder on your pc. Second, open the cloned repo in your IDEA and run the docker-compose
file by either typing `docker-compose up` in your terminal or via IDEA's interface. Finally, run the main classes.

No other setup is required. However, `users` database may not be created on the docker-compose run, in case of such an
inconvenience you have to look up postgresql's container id by running `docker ps`. After that, copy the container id for
postgres and run the following command `docker exec -it <container id here>`. After that just type `create database users;`
in the command prompt and restart the UserMicroservice.

And that's it! If you want to test just the UI alone, you only need to go to `localhost:9090` in any browser of your
choice. If you're here to work with microservices, move on to the next section of this readme to find out how to
authenticate!

***

To authenticate, just hit that `localhost:8000/auth/authenticate` url with a POST request with the following body:  
```json
{
  "login": "admin",
  "password": "password"
}
```
You'll get a JWT with `SUPER_USER` set of authorities that will grant you access to any resource you wish to reach.
If you want to create a normal user, you can easily register yourself as one by sending a POST request on
`localhost:8000/auth/register` with similar body like this:
```json
{
  "email": "valid@email.com",
  "username": "mbj",
  "password": "coolpassword",
  "firstName": "Michael",
  "lastName": "Jackson"
}
```
Note that `lastName` is not necessary to provide, but if you do, make sure it's not blank.
You should receive a confirmation mail on the email address you've specified. After following the provided link,
you can authenticate just like with the admin user. Also note that `login` property can be either email or username.
