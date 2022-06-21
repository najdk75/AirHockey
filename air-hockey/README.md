# Air Hockey Mai 2021

### Fonctionnement
Le but du jeu est de taper le palet dans les buts adverses afin de marquer le plus de points possibles!


### Développeurs
Ce jeu a été développé par l'équipe Air Hockey 2 pour le projet de quatrième semestre de l'Université Paris Diderot.
Cette équipe est composée de :

- Chaima BELHOUT 
- Bouchra NOURRINE 
- Alexandre PATAUT 
- Najd KACEM (Partie physique)

### Compilation
    
Le jeu peut être lancé en utilisant les commandes suivantes :
    
    ./gradlew build
    ./gradlew run


### Partie technique
####  Partie physique
#####  A. Comment gérer les collisions ?
La gestion des collisions a été une majeure partie du code concernant la partie
physique, en effet il était primordial de les gérer d'une façon la plus réaliste
possible afin que le jeu paraisse crédible.

Cependant, contrairement à la continuité temporelle de notre réalité physique,
la modélisation informatique de notre jeu fonctionne différement. En effet,
notre modèle physique s'actualisant frame par frame et non en continu, nous 
avons du adapter notre code afin de minimiser des comportements et des réactions 
physiques approximatives.

Il fallait donc prendre en compte le temps qui s'écoulait entre chaque frame qu'on notera dt. Cette valeur est
fondamentale. On l'utilisera notamment afin de gérer l'interpénétration avec les murs mais surtout lors de l'actualisation des positions, en effet elle servira à proportionner nos valeurs avec la vitesse de rafraîchissement du jeu.

Un des principaux problèmes a été de résoudre l'interpénétration de
nos palets. En effet, le concept de rigidité des objets physiques n'est 
pas applicable au niveau informatique. C'est pour cela qu'on a du adapter
notre code de la façon suivante :

Soient A et B deux cercles (représentant deux palets)


- Regarder si les deux cercles sont en collision à la frame f, 
  c'est-à-dire vérifier si la distance entre les centres des cercles
  A et B est égale à la somme des deux rayons (d <= r1 + r2).
Ici on ne teste pas l'égalité car les cercles seront quasiment 
  toujours en interpénétration.
  

- Quand les cercles sont en interpénétration, c’est-à-dire qu'il existe
  un court segment nommé " profondeur d'interpénétration (overlap)"
Il faudra donc séparer chaque cercle d'une distance de "overlap/2" selon 
  la direction du vecteur normalisé reliant les deux centres. Après que 
  cette séparation ait été effectuée, l'interpénétration sera corrigée et 
  une réponse adéquate à cette collision sera apportée par la suite.
  

- Finalement, on peut maintenant appliquer la loi des chocs élastiques
  à nos deux cercles car leur masse et leur énergie cinétique sont conservées 
  après l'impact. Même si nous travaillons en deux dimensions, il suffit de faire
  une rotation d'angle theta, où theta représente l'angle de collision entre les deux 
  cercles puis
  d'appliquer les équations de chocs élastiques en une dimension.


#### B. Lien entre modèle physique et graphique

Afin de faire le lien entre le modèle physique et le modèle graphique, nous avons décidé
de transformer les propriétés (attributs) 
de nos objets physiques en Properties.
En effet, en les liant avec les Properties des 
objets graphiques, l'actualisation des positions est automatique.


#### C. Les pistes afins d'améliorer le réalisme du jeu

Dans ce projet, nous avons utilisé un algorithme de détection de collisions assez naïf. En effet, nous vérifions 
à chaque frame si deux objets sont en collision. Cela pose un problème si un des objets a une vélocité beaucoup trop élevée.
En effet si un joueur déplace la souris trop rapidement entre deux frames, il 
est possible qu'il traverse entièrement le Puck et que la collision soit résolue d'une façon irréaliste et même erronée.

La méthode, nommée "Continuous Collision Dectection" aurait permis de 
calculer le moment exact de collision, grâce aux équations de mouvement des cercles 
 dans le temps ainsi de supprimer l'interpénétration
et de gérer le cas où les objets auraient une trop grande vitesse.


Pour voir la différence : 
 - https://www.youtube.com/watch?v=5JcSbQzZfdY

Pour l'idée mathématiques : 
 - https://physique.cmaisonneuve.qc.ca/svezina/nya/note_nya/NYA_XXI_Chap%206.X2.pdf









    





