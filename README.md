# Programmation Mobile Kotlin - My Games List

## Présentation

Application Kotlin permettant de parcourir une liste de jeux vidéos, de les consulter et de les ajouter à ses favoris.

Jetpack Compose est utilisé pour la partie UI. Le projet est basé sur une architecture MVVM.

## Version

- Version 1.0 : le 19/03/2025

## Auteurs
**LALLIER** Anna

Forké à partir du projet [INSA_MyGamesList_20242025](https://github.com/Adjizan/INSA_MyGamesList_20242025)

## Cloner le dépôt

    git clone https://github.com/AnnaLallier/INSA_MyGamesList

## Fonctionnalités

- **TP2 & 4** Liste des jeux, Détails d'un jeu
- **TP 3** Navigation
- **TP 5** Recherche
- **TP 6** Favoris
- **TP 7** API (le seul point qui n'est pas implémenté est le fait que le token ne devrait pas être stocké en dur dans le code)

### Bonus implémentés


- La pagination est implémentée pour la liste des jeux. Lorsque l'utilisateur arrive en bas de la liste, une nouvelle requête est faite pour récupérer les jeux suivants. L'application tente également de récupérer les jeux suivants lorsque l'on effectue une recherche et que l'on arrive en bas de la liste des résultats.
- Un filtre pour afficher uniquement les jeux favoris est disponible dans l'app bar (icône en forme de coeur à côté du titre). Ce filtre fonctionne sur la page d'accueil et lors d'une recherche. Pour le désactiver, il suffit de cliquer à nouveau sur l'icône.


#### `insa.mygamelist.data.local`
- Stockage des favoris et récupération à l'ouverture de l'application à partir d'un fichier JSON
- Mise en cache / mode offline pour les jeux. Pour cela les jeux sont stockés dans un fichier JSON (`games_updated.json`) lorsque l'application parvient à récupérer les jeux depuis l'API (soit au lancement et dès qu'une nouvelle requête est faite pour récupérer plus de jeux). Si l'application est lancée sans connexion internet, elle ira chercher les jeux dans ce fichier. Donc pour que ce mode avion fonctionne, il faut que l'application ait été lancée au moins une fois avec une connexion internet. 
  - Afin qu'il ne soit pas nécessaire de la redémarrer si le téléphone a été mis un brève moment en mode avion ou si la connexion Internet était de mauvaise qualité, l'application retentera toujours de récupérer des jeux depuis l'API. 
  - Un message est affiché à la fin de la liste des jeux, à la place de la barre de chargement habituelle, pour indiquer que l'application est hors-ligne.


#### Général

- Qualité du code (documentation sur les classes, commentaires, utilisation d'une seule langue - l'anglais - pour le code, etc.)
- J'ai fait en sorte que l'application respecte une architecture Model-View-ViewModel (MVVM). Si toutefois des violations de ce modèle sont relevées, elles ne sont pas intentionnelles et devraient avoir été corrigées.

## Documentations utilisées :

J'ai notamment utilisé les documentations suivantes (sans compter les pages StackOverflow...) ;


#### Style
- https://developer.android.com/guide/navigation/design?hl=fr#compose
- https://developer.android.com/develop/ui/compose/text/style-text?hl=fr
- https://developer.android.com/codelabs/basic-android-kotlin-compose-text-composables?hl=fr#5
- https://developer.android.com/develop/ui/compose/touch-input/pointer-input/scroll?hl=fr
- https://developer.android.com/reference/kotlin/androidx/compose/foundation/interaction/HoverInteraction
- https://developer.android.com/reference/kotlin/androidx/compose/foundation/shape/RoundedCornerShape

#### Navigation Component
- https://developer.android.com/guide/navigation/design?hl=fr#compose
- https://developer.android.com/guide/navigation/design/type-safety?hl=fr
- https://kotlinlang.org/docs/serialization.html
- https://medium.com/androiddevelopers/json-in-kotlin-with-kotlinx-serialization-4765a8a3c275
- https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-core/kotlinx.serialization/-serializable/
- https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/builtin-classes.md#lists

#### AppBar
- https://developer.android.com/develop/ui/compose/components/app-bars?hl=fr

#### Alert/Dialog

- https://blog.kotlin-academy.com/dialogs-in-jetpack-compose-2b7f72b14651
- https://developer.android.com/develop/ui/compose/components/dialog?hl=fr


#### SearchBar
- https://medium.com/@desilio/searchbar-with-jetpack-compose-and-material-design-3-1f735f383c1f
- https://composables.com/material3/searchbar
- https://stackoverflow.com/questions/75541072/how-can-i-make-this-custom-text-search-field-in-jetpack-compose
- 

#### API
- https://api-docs.igdb.com/
- https://square.github.io/retrofit/
- https://developer.android.com/topic/libraries/architecture/coroutines
- https://medium.com/exploring-android/android-networking-with-coroutines-and-retrofit-a2f20dd40a83
- https://www.axopen.com/blog/2021/01/retrofit-projet-android-kotlin/
- https://developer.android.com/reference/com/google/android/material/progressindicator/LinearProgressIndicator

#### Json
-  https://github.com/google/gson/blob/main/UserGuide.md
-  https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520

#### MVVM
- https://medium.com/@zorbeytorunoglu/mvvm-in-android-059e9aae84c1
- https://medium.com/androidmood/comprendre-larchitecture-mvvm-sur-android-aa285e4fe9dd