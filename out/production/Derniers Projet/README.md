---
titre: Java
sous-titre: Miniprojet 3 - Pendu avec Swing
auteur: Philippe \textsc{Roussille}
date: 3iL 1A 2023
---

**Date de rendu du projet : 13/03/2024 - 17h05**

# Bienvenue dans votre projet

Nous approchons bientôt des huit mois de pratique en Java, et jusqu'à présent, vous avez été habitués à recevoir des sujets détaillés pour vos travaux. Cette fois-ci, c'est un peu différent : je vous propose un projet orienté conception. Cela signifie que la façon dont vous concevez et structurez votre code sera bien plus prise en compte que la partie purement codée. Cependant, assurez-vous que votre projet minimal compile et soit fonctionnel. Bon courage !

## Tests unitaires, `git` et Javadoc

- Il vous est demandé de bien *commenter* et de bien réaliser la documentation (au format Javadoc) de vos fichiers.
- Pensez à commiter & pusher votre travail ***de façon régulière***.
- Les messages de vos commits sont essentiels (pas de "lol ça marche", "ENFIN!!!!", "toto", ou autres). Soyez clairs (vous pouvez vous inspirer de [cette norme](https://buzut.net/cours/versioning-avec-git/bien-nommer-ses-commits)).
- N'oubliez pas de réaliser des tests unitaires afin de valider votre code au fur et à mesure de votre production. Essayez d'en réaliser au moins un par partie demandée.

## Structure du rendu

1. Il vous est demandé un rendu *propre*, c'est à dire qui **compile** à minima.
2. Si votre projet **ne compile pas**, la note est automatiquement plafonnée à 10.
3. Votre projet comportera à la racine un fichier `AUTHORS` qui comporte votre nom et votre adresse 3il (du genre `Philippe ROUSSILLE <roussille@3il.fr>`).
4. Ce projet est à faire ***individuellement***. Toutefois, je n'ai rien contre l'entraide entre vous (tant que cela ne se résume pas à "pomper le code du voisin"). Si vous avez été aidé par quelqu'un, réalisez un fichier `HELPERS` à la racine de votre projet où vous indiquez celui qui vous a aidé, et de quelle façon (cela constituera un bonus pour cette personne lors de la notation).
5. Enfin, vous réaliserez un fichier `README.md` à la racine du projet dans lequel vous indiquerez, sous réserve de pertinence :
    - ce que vous avez réalisé du projet ;
    - les réponses aux diverses questions du sujet s'il y en a ;
    - un retour personnel sur les points qui vous ont paru difficiles ;
    - tout autre commentaire que vous jugerez utile...

**Vous devez également inclure une section détaillée sur vos réflexions concernant la conception et la réflexion sur le projet. Cette section est à votre responsabilité pour assurer la clarté de votre approche et la qualité de votre réflexion.**

## Fonctionnalités attendues

1. Lecture aléatoire d'un mot à deviner à partir d'un fichier texte donné à la racine du projet.
2. Affichage graphique de l'interface du jeu à l'aide de Swing.
3. Affichage graphique du pendu qui évolue en fonction des erreurs du joueur.
4. Affichage graphique des lettres déjà proposées par le joueur.
5. Affichage (ou non) de la définition (niveau de difficulté).
6. Utilisation (ou non) d'un timer (niveau de difficulté).
7. Gestion des entrées utilisateur pour proposer des lettres.
8. Vérification de la validité des entrées utilisateur (lettre de l'alphabet uniquement).
9. Gestion du décompte des tentatives restantes.
10. Gestion de la victoire ou de la défaite du joueur.
11. Possibilité de rejouer une partie après la fin d'une partie.

## Contraintes techniques

- Le fichier texte contenant la liste des mots à deviner sera nommé `mots.txt` et sera placé à la racine du projet.
- Le format du fichier `mots.txt` sera d'un mot par ligne, suivi d'un espace, et de sa définition.
- Le programme devra lire aléatoirement un mot à partir de ce fichier pour chaque nouvelle partie.

## Réalisations bonus

1. **Mode multijoueur**: Ajouter la possibilité de jouer en mode multijoueur, où un joueur peut deviner le mot tandis qu'un autre joueur sélectionne le mot à deviner. Cela nécessiterait la mise en place d'une interface pour entrer et valider les mots, ainsi que la gestion des tours entre les joueurs.
2. **Séparateur**: Utiliser un autre séparateur que l'espace, ce qui permettrait de faire deviner des groupes de mots.
3. **Thèmes graphiques personnalisés**: Permettre aux joueurs de choisir parmi différents thèmes graphiques pour l'interface du jeu. Par exemple, ils pourraient sélectionner un thème de couleur ou un ensemble d'images pour représenter le pendu et les lettres.
4. **Gestion des scores**: Implémenter un système de gestion des scores pour enregistrer les scores des joueurs au fil du temps. Cela pourrait inclure le suivi du nombre de victoires et de défaites, ainsi que la possibilité de comparer les scores entre les joueurs.
5. **Difficulté réglable**: Ajouter la possibilité de régler la difficulté du jeu en modifiant le nombre maximum d'erreurs autorisées ou en sélectionnant des mots plus courts ou plus longs à deviner.
6. **Support pour les langues multiples**: Étendre le jeu pour supporter la saisie et la sélection de mots dans différentes langues. Cela pourrait impliquer la gestion de différents fichiers de mots pour chaque langue prise en charge.
7. **Effets sonores**: Intégrer des effets sonores pour accompagner les actions du joueur, tels que des sons pour les bonnes et mauvaises devinettes, ainsi que des bruits d'ambiance pour créer une atmosphère immersive.
8. **Animations supplémentaires**: Ajouter des animations supplémentaires pour rendre l'interface du jeu plus dynamique. Par exemple, des animations pour représenter le mouvement du pendu ou la sélection des lettres.
9. **Sauvegarde et chargement de parties**: Permettre aux joueurs de sauvegarder leur progression dans une partie et de la charger ultérieurement pour reprendre là où ils se sont arrêtés.
10. **Suggestions de mots**: Ajouter une fonctionnalité qui suggère des mots à deviner en fonction des mots déjà joués ou du niveau de difficulté choisi.
11. **Mode entraînement**: Intégrer un mode entraînement où les joueurs peuvent deviner des mots sans limite de temps ou d'erreurs, pour pratiquer et améliorer leurs compétences.
12. **Aide contextuelle**: Intégrer une fonctionnalité qui fournit des indices ou des suggestions au joueur lorsque celui-ci est bloqué pendant le jeu. Par exemple, afficher une lettre aléatoire du mot à deviner ou donner une indication sur le thème du mot.
13. **Extensions de mot**: Permettre aux joueurs d'ajouter leurs propres mots au fichier mots.txt pour étendre la base de mots à deviner. Cela pourrait être réalisé à travers une interface conviviale dans le jeu.
14. **Personnalisation des règles**: Offrir aux joueurs la possibilité de personnaliser les règles du jeu, telles que le nombre maximum d'erreurs autorisées, le temps imparti pour deviner un mot, ou même des règles uniques créatives.
15. **Mode histoire**: Ajouter un mode histoire narratif où les joueurs suivent une intrigue et résolvent des énigmes basées sur le jeu du pendu. Cela pourrait inclure des dialogues, des personnages et des événements spéciaux tout au long du jeu.
16. **Mode puzzle**: Transformer le jeu en un puzzle où les lettres sont mélangées et le joueur doit les remettre dans l'ordre correct pour former le mot.
17. **Mini-jeux bonus**: Intégrer des mini-jeux bonus dans le jeu principal, offrant des distractions amusantes et des récompenses supplémentaires pour les joueurs.
18. **Intégration de l'intelligence artificielle**: Ajouter une intelligence artificielle avancée qui peut jouer contre les joueurs, s'adapter à leur niveau de compétence et fournir une expérience de jeu compétitive et stimulante.

## Interdiction de travailler sur le projet chez soi

***Il est strictement interdit de travailler sur le projet en dehors des heures de cours et des locaux de l'établissement. Tout travail effectué en dehors ou commit suspect entraînera une forte minoration de la note.***
