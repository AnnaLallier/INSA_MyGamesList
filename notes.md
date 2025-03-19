### Le rendu
Rendu le 19/03/2024 (tant qu'il n'y a pas marqué 20/03 c'est bon, donc on peut le rendre le 19 à 23h59 par exemple...)
Ne pas inclure le répertoire de build dans le rendu moodle
Mettre des commentaires;...

TODO :
!!!!!! tester sur un autre tel 


- Barre de recherche, dans le fichier SearchAlert !!!

La prof veut qu'on mette un fichier README expliquant ce qu'on a fait

TODO : add GameOver music when no game is found

7 mars 4h recherche

14 mars 4h favoris

Exposés

Présence
attitude en TP
projet rendu

### Notes concernant le TP Facultatif
Le MVC :
- Model : Retrofit, suspend acceptés
- Vue : Main, screens etc. Jamais de suspend dans la vue !
- Controleur : ViewModel suspend

Suspend, remember, mutablestateflow dans le controleur permettent de faire un remember sur un stateflow ce qui permet de recomposer automatiquement la vue quand il est mis à jour

Si la liste de jeux est nulle on affiche un loader, si elle est vide on affiche :( et si elle est pleine on affiche la liste

Il fallait faire une liste de Games simple, pas Game, Cover, Genre, etc.
=> Le prof a fait une ConsolidatedGames (genres, url, toutes les infos eh oui....................................)

#### Exposé
de 7 min + 3 minutes de questions posées par la prof

Nous on est sur les solutions hybrides, donc contrairement aux solutions natives comme Kotlin, il existe des solutions hybrides telles que Flutter gngagnagna et en plus j'ai déjà fait une appli Flutter donc on va pouvoir le présenter 


#### Mes curl
sg16951bcbb49ntm5w2ma13r5vqtje
ovxw2ybcdq8r0jo5prxjfl25tvw5ul


curl -X POST "https://id.twitch.tv/oauth2/token?client_id=sg16951bcbb49ntm5w2ma13r5vqtje&client_secret=ovxw2ybcdq8r0jo5prxjfl25tvw5ul&grant_type=client_credentials"

{"access_token":"ygp92co7qdz0usoelrdlk6xbnz5ieb","expires_in":5124618,"token_type":"bearer"}

curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields *; limit 10;"


POST : https://id.twitch.tv/oauth2/token?client_id=sg16951bcbb49ntm5w2ma13r5vqtje&client_secret=ovxw2ybcdq8r0jo5prxjfl25tvw5ul&grant_type=client_credentials

curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields *; limit 10;"


curl -X POST https://api.igdb.com/v4/games \
Content-Type: text/plain; charset=utf-8 \
Content-Length: 52 \
Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje \
Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb \
fields id, name, genres, cover, platforms; limit 10;

curl -X POST "https://api.igdb.com/v4/games" \
-H "Content-Type: text/plain; charset=utf-8" \
-H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" \
-H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" \
--data "fields id, name, genres, cover, platforms; limit 10;"

--> POST https://api.igdb.com/v4/games
Content-Type: text/plain; charset=utf-8
Content-Length: 52
Client-ID: 1234
Auhorization: Bearer 1234
fields id, name, genres, cover, platforms; limit 10;
--> END POST (52-byte body)


// méthodes suspend dans un CoroutineScope. Le Scope est combiné avec un Dispatcher pour indiquer sur quel Thread exécuter l'opération asynchrone
// Retrofit pour les appels réseaux
// DAns l'objet interface qui va servir à représenter l'API, utiliser @POST, @Body
//RequestBody pour les appels à IGDB


Un beau curl :

curl -X POST "https://api.igdb.com/v4/games" 
-H "Content-Type: text/plain; charset=utf-8" 
-H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" 
-H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" 
--data "fields id, cover, first_release_date, genres.id, genres.name, name, platforms.id, platforms.name, platforms.platform_logo, summary, total_rating, cover.id, cover.url; limit 10; where platforms.platform_logo !=null; sort name_asc"


curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields id, cover.id, cover.url, first_release_date, genres.id, genres.name, name, platforms.id, platforms.name, platforms.platform_logo, summary, total_rating; limit 10; where platforms.platform_logo !=null;"


curl -X POST "https://api.igdb.com/v4/platform_logos" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields *; limit 10;"
curl -X POST "https://api.igdb.com/v4/platform_logos" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields *; limit 10;"

curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields name, platforms.name, platforms.platform_logo.url; limit 10; where platforms.platform_logo != null;"

curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields id, cover.id, cover.url, first_release_date, genres.id, genres.name, name, platforms.name, platforms.platform_logo.url, summary, total_rating;  limit 10; where platforms.platform_logo != null & total_rating > 75  & first_release_date > 1000000000  sort total_rating desc, first_release_date desc;"


La cover de BOTW est :
{
"id": 172453,
"url": "//images.igdb.com/igdb/image/upload/t_cover_big/co3p2d.jpg"
},

curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields id, cover.id, cover.url, first_release_date, genres.id, genres.name, name, platforms.name, platforms.platform_logo.url, summary, total_rating; limit 100; where platforms.platform_logo != null;"



curl -X POST "https://api.igdb.com/v4/covers" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields *; limit 10;"

https://api.igdb.com/v4/covers

curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields first_release_date, name, id; limit 10; where platforms.platform_logo != null ; sort first_release_date desc;"
