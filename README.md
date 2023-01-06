# Tasty

- Repository for carrying out the open scope project to the second IDP project.

### Environment required for execution

- [Java](https://www.java.com/pt-BR/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/get-docker/)
- [Postman](https://www.postman.com/downloads/)
- [Eclipse](https://www.eclipse.org/downloads/)
- [Git](https://git-scm.com)

### 🚀 How to run

- Clone this repository:
```
https://github.com/mairaalvs/Tasty.git
```

- Register at [rapidapi Tasty API](https://rapidapi.com/apidojo/api/tasty/) to get a key "X-RapidAPI-Key".


- Replace in the **application.properties** and **Docker-Compose** files the value of this key.


- Run the following commands on Docker:
```
docker network create inatel
```

```
docker container run --name mysql --network=inatel -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql
```

### 💻 How it works

#### The application developed, called Recipe Evaluation, is a REST based application whose purpose is to store evaluations to recipes. A user can register as many evaluations as he wants for different recipes with the informations of  date the evaluation and stars the recipe deserve. But, the evaluation of a recipe can only be registered if the recipe is already registered in the external API (Tasty).

- To verify if a recipe is already registered:
#### GET https://tasty.p.rapidapi.com/recipes/auto-complete

With the recipe registered in the external API, we can start registering of an evaluations.

- To read all evaluations from Recipe Evaluation:
#### GET http://localhost:8081/evaluation

- To read only one evaluation in Recipe Evaluation:
#### GET http://localhost:8081/evaluation//recipe?id={{recipeId}}

- To register a new evaluation:
#### POST http://localhost:8081/evaluation
```	
{
	"recipeId": "chocolate cake",
	"evaluations": {
		"2022-03-10": 5
	}
}
```

- To delete the cache:
#### DELETE http://localhost:8081/evaluation/cache