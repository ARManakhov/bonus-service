# bonus_service
Service to manage users bonuses
# Requirements
* docker
* postgresql

# How to run
Change docker-compose.yml file (env variables):
* DATABASE_URL - jdbc url to postgres database
* DATABASE_USERNAME - database username
* DATABASE_PASSWORD - database password

Then start by command ```docker-compose up -d```. Standard admin user will be admin:admin

# Swagger
you can use swagger on endpoint ```/swagger-ui/index.html```