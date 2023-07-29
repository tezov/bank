# Tezov project (Kotlin / Compose)

## What's New
- 24/07/2023 - refactor gradle groovy to kts + split project with external modules. Applied tezov config and catalog plugin
- 09/07/2023 - custom navigation + navigator supporting overlay and customizable animation transition 

## What's Next
- add init script, remove buildSrc, clean plugin refactor tag, update gradle to portal gradle plugin
- refactor test
- refactor java lib

## Description

This project is done in Kotlin with Compose in order to find a convenient architecture to fast build an app with stable UI modules. 

I'm also using this project to refactor [my Java lib](https://github.com/tezov/crypter_and_lib) in Kotlin. Right now, just doing the bare minimum.

This application is available in the playstore [App](https://play.google.com/store/apps/details?id=com.tezov.bank.rse). 

Right now I'm working only on the UI side to get enough content to look how I gonna integrate the ViewModel module followed by an clean architecture to link toward the network/cache/data.

Look at the todo list if you wanna now what is my next step (todo only in french sorry :))

---

Ce projet est en Kotlin avec Compose pour trouver une architecture viable de création rapide d'application avec des modules core stable en tentant d'adopter une structure de clean architecture et par la suite avec des modules intégralement testable.

Je me sers de ce projet également pour migrer [ma librairie java](https://github.com/tezov/crypter_and_lib) vers Kotlin. Pour le moment, je fais que le strict minimum pour récupérer les outils Java dont j'ai besoin.

L'application est disponible sur le playstore [App](https://play.google.com/store/apps/details?id=com.tezov.bank.rse)

Ce projet est actuellement focalisé sur l'UI en particulier une grosse structure sur le theming. Mes autres projets étaient davantage technique. 

Voir mon fichier TODO pour savoir où j'en suis.



