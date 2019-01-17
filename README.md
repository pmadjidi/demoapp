# demoapp
sample java springboot restapi code....

This is a demo app, no security, trottling, paging is implemented
Validation could be implemented with the help av springboot annotations but i left it for now...


clone the repo
cd repo

docker build -t demo-app .
docker run -d -p 8080:8080 demo-app

docker ps should display something like this.....

CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS                    NAMES
5ecf8e70ca9e        demo-app            "java -jar demoApp-0…"   About a minute ago   Up About a minute   0.0.0.0:8080->8080/tcp   distracted_goldstine

demoApp git:(master) ✗ curl localhost:8080/movie/all
{
"Dirty Harry":{"id":1,"name":"Dirty Harry","starring":"Clint Eastwood","language":"English","productionYear":"1971"},
"Kellys Heros":{"id":5,"name":"Kellys Heros","starring":"Clint Eastwood","language":"English","productionYear":"1970"},
"Unforgiven":{"id":2,"name":"Unforgiven","starring":"Clint Eastwood","language":"English","productionYear":"1992"},
"High Plains Drifter":{"id":7,"name":"High Plains Drifter","starring":"Clint Eastwood","language":"English","productionYear":"1973"},
"The Good, The Bad and the Ugly":{"id":4,"name":"The Good, The Bad and the Ugly","starring":"Lee Van Cleff","language":"English","productionYear":"1966"},
"Thunderbolt and Lightfoot":{"id":6,"name":"Thunderbolt and Lightfoot","starring":"Clint Eastwood","language":"English","productionYear":"1974"}
}     

api:

/movie/all
/movie/add/{name}/{starring}/{lang}/{year}
/movie/delete/{name}/{starring}/{lang}/{year}
/movie/name/{name}
/movie/year/{year}
/movie/starring/{starring}


