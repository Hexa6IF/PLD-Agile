# Choix architecturaux et Patrons de conception

## Architecture

L'application suit une architecture Modèle-Vue-Contrôleur. Nous avons choisi d'utilisé cette architecture pour les raisons suivantes :

- Modularité du code : L'architecture MVC sépare la logique de l'application en au moins trois parties. Ceci facilite le développement et l'entretien de l'application car avec cette architecture, l'impact d'un changement aux spécifications de l'application est localisé et n'affecte pas l'application en entier. Par exemple un changement dans l'affichage d'un élément de l'application n'affecte que le composant 'Vue' et un changement dans le modèle du domaine ne porte un changement que dans le composant 'Modèle'. Ceci est très avantageux car en développement AGILE les spécifications de l'application évoluent continuellement pendant son développement.

- Developpement simultané : L'architecture facilite aussi le développement à plusieurs. Grâce au faible couplage entre les composants, plusieurs personnes peuvent travailler sur des composants différentes sans avoir peur des conflits avec le code des autres.

## Patrons de conception

Dans notre application nous avons utilisé les patrons de conception suivants :

- Polymorphisme. Ce patron de conception est utilisé dans le package `Algorithm` de l'application. En effet, pour trouver la meilleure tournée nous avons utilisé un algorithm Branch and Bound adapté à un TSP tout en prévoyant que nous pouvons avoir plusieurs heuristiques à utiliser dans l'algorithme. Donc nous avons une classe abstraite `TSP` qui implémente l'algorithme mais pas l'heuristique. Nous avons deux classes `TSPSimple` et `TSPHeuristic` qui héritent de `TSP` et qui implémentent chacun son propre heuristique.

- État. Ce patron de conception est utilisé dans le package `Controller`. L'action que le contrôleur doit déclencher lors d'un événement donné dépend largement de l'état de l'application.
Par exemple, un clic sur un noeud lorque nous sommes en train de placer un nouveau point de livraison ne doit pas avoir le même effet qu'un clic sur un noeud lorsque nous sommmes en train de supprimer une livraison. Donc, nous définissons une classe pour chaque état possible de l'application et le contrôleur garde toujours un de ces états comme son état courant. L'état courant décide quelles actions sont à prendre lors des événements.

- Commande. Ce patron de conception est aussi utilisé dans le package `Controller`. L'utilisateur veut souvent défaire/refaire (undo/redo) des actions. Avec ce patron de conception nous gardons dans le contrôleur une liste des actions prises par l'utlisateur. Chaque action est enregistré comme une `Commande`, et chaque type de `Commande` définit ses propres méthodes de undo et de redo.

Remarque : Le patron Singleton est utilisé pour le parser de fichiers XML. Cependant il n'est pas indispensable au bon fonctionnement de l'application, c'est pourquoi nous le laissons ici en remarque.
