To authenticate, just hit that `localhost:8000/auth/authenticate` url with a POST request with the following body:  
```json
{
  "login": "admin",
  "password": "password"
}
```
You'll get a JWT with `SUPER_USER` role, that will grant you access to any resource you wish to reach.
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
Note that `lastName` is not necessary to provide. But if you do, make sure it's not blank.
You should receive a confirmation mail on the email address you've specified. After following the provided link,
you can authenticate just like with the admin user. Also note that `login` property can be either email or username.
