TODO :
- Barre de recherche, dans le fichier SearchAlert !!!


Rendu le 19/03/2024 (tant qu'il n'y a pas marqué 20/03 c'est bon, donc on peut le rendre le 19 à 23h59 par exemple...)
Ne pas inclure le répertoire de build dans le rendu moodle


7 mars 4h recherche

14 mars 4h favoris

Exposés 

Présence
attitude en TP
projet rendu
Exposé
de 7 min + 3 minutes de questions posées par la prof

Nous on est sur les solutions hybrides, donc contrairement aux solutions natives comme Kotlin, il existe des solutions hybrides telles que Flutter gngagnagna et en plus j'ai déjà fait une appli Flutter donc on va pouvoir le présenter 


La prof veut qu'on mette un fichier README expliquant ce qu'on a fait


TODO : add GameOver music when no game is found


sg16951bcbb49ntm5w2ma13r5vqtje
ovxw2ybcdq8r0jo5prxjfl25tvw5ul



curl -X POST "https://id.twitch.tv/oauth2/token?client_id=sg16951bcbb49ntm5w2ma13r5vqtje&client_secret=ovxw2ybcdq8r0jo5prxjfl25tvw5ul&grant_type=client_credentials"

{"access_token":"ygp92co7qdz0usoelrdlk6xbnz5ieb","expires_in":5124618,"token_type":"bearer"}

curl -X POST "https://api.igdb.com/v4/games" -H "Client-ID: sg16951bcbb49ntm5w2ma13r5vqtje" -H "Authorization: Bearer ygp92co7qdz0usoelrdlk6xbnz5ieb" -d "fields *; limit 10;"


POST : https://id.twitch.tv/oauth2/token?client_id=sg16951bcbb49ntm5w2ma13r5vqtje&client_secret=ovxw2ybcdq8r0jo5prxjfl25tvw5ul&grant_type=client_credentials