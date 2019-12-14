# Choix architecturaux et Patrons de conception

## Architecture

L'application suit une architecture Modèle-Vue-Controleur. Nous avons choisi d'utiliser cette architecture pour les raisons suivantes :

- Modularité du code : L'architecture MVC sépare la logique de l'application en au moins trois parties. Ceci facilite le développement et l'entretien de l'application car avec cette architecture, l'impact d'un changement aux spécifications de l'application est localisé et n'affecte pas l'application en entier. Par exemple un changement dans l'affichage d'un élément de l'application affecte que le composant 'Vue' et un changement dans le modèle du domaine porte un changement que dans le composant 'Modèle'. Ceci est très avantageux car en développement AGILE les spécifications de l'application évoluent continuellement pendant son développement.

- Developpement simultané : L'architecture facilite aussi le développement à plusieurs. Grace au faible couplage entre les composants, plusieurs personnes peuvent travailler sur des composants différents sans avoir peur des confilts avec le code des autres.

## Patrons de conception

Dans notre application nous avons utilisé les patrons de conceptionsuivants :

- Polymorphisme. Ce patron de conception est utilisé dans le package `Algorithm` de l'application. En effet, pour trouver la meilleure tournée nous avons utilisé un algorithm Branch and Bound adapté à un TSP tout en prévoyant que nous pouvons avoir plusieurs heuristiques à utiliser dans l'algorithme. Donc nous avons une classe abstraite `TSP` qui implémente l'algorithme mais pas l'heuristique. Nous avons deux classes `TSPSimple` et `TSPHeuristic` qui hérite de `TSP` et qui implémente chacun son propre heuristique.

- État. Ce patron de conception est utilisé dans le package `Controller`. L'action que le controleur doit déclencher lors d'un événement donné dépends largement de l'état de l'application.
Par exemple, une clique sur un noeud lorque nous sommes en train de placer un nouveau point de livraison ne doit pas avoir le même effect qu'une clique sur un noeud lorsque nous sommmes en train de supprimer une livraison. Donc, nous définissions une classe pour chaque état possible de l'application et le controleur garde toujours un des ces états comme son état courant. L'état courant décide quelles actions sont à prendre lors des événements.

- Commande. Ce patron de conception est aussi utilisé dans le package `Controller`. L'utilisateur veut souvent defaire/refaire (undo/redo) des actions. Avec ce patron de conception nous gardons dans le controleur une liste des actions prises par l'utlisateur. Chaque action est enregistré comme une `Commande`, et chaque type de `Commande` définit ses propres méthodes de undo et de redo.
