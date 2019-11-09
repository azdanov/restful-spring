# Restful Spring

Exploring REST in a Spring Boot application.

## Setup

### Create MySQL Db

```sql
create database photo_app;
use photo_app;
create user 'azdanov'@'localhost' identified by '123321';
grant all privileges on photo_app.* to 'azdanov'@'localhost';
```

### Tips

FriendlyID Library is used to map from friendlyId to UUID: 
https://github.com/Devskiller/friendly-id#Spring-Boot-integration

