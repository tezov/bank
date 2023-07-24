# Tezov project (Kotlin / Compose)

## What's New
- 09/07/2023 - custom navigation + navigator supporting overlay and customizable animation transition 

## What's Next
- clean + refactor test
- refactor java lib

## Description

This project is done in Kotlin with Compose in order to find a convenient architecture to fast build an app with stable UI modules. 

I'm also using this project to refactor [my Java lib](https://github.com/tezov/crypter_and_lib) in Kotlin. Right now, just doing the bare minimum.

This application is available in the playstore [App](https://play.google.com/store/apps/details?id=com.tezov.bank.rse) without obfuscation since my proguard rule and my gradlle plugin are still not refactored to kotlin. 

Here some screens:

![image](https://github.com/tezov/bank/assets/111152291/49ed31f4-b722-4743-a225-b9f58b9a1deb)
![image](https://github.com/tezov/bank/assets/111152291/c5278a9f-ad60-44f1-8206-476570e974e7)
![image](https://github.com/tezov/bank/assets/111152291/2d4fa507-2a37-41f3-8ce7-fcf97604ea1a)
![image](https://github.com/tezov/bank/assets/111152291/47dc9eea-ac88-4979-ac56-1cdabea9dc0c)
![image](https://github.com/tezov/bank/assets/111152291/0bcf25b4-4498-45b3-b174-31a03e9a4917)
![image](https://github.com/tezov/bank/assets/111152291/f12042b0-bbd4-4080-a9e3-ea6c5afe3e2e)

Right now I'm working only on the UI side to get enough content to look how I gonna integrate the ViewModel module followed by an clean architecture to link toward the network/cache/data.

Look at the todo list if you wanna now what is my next step (todo only in french sorry :))

---

Ce projet est en Kotlin avec Compose pour trouver une architecture viable de création rapide d'application avec des modules core stable en tentant d'adopter une structure de clean architecture et par la suite avec des modules intégralement testable.

Je me sers de ce projet également pour migrer [ma librairie java](https://github.com/tezov/crypter_and_lib) vers Kotlin. Pour le moment, je fais que le strict minimum pour récupérer les outils Java dont j'ai besoin.

L'application est disponible sur le playstore [App](https://play.google.com/store/apps/details?id=com.tezov.bank.rse) non obfusqué pour le moment (regles proguard + plugin gradle pas encore porté)

Voici quelques screens:

![image](https://github.com/tezov/bank/assets/111152291/49ed31f4-b722-4743-a225-b9f58b9a1deb)
![image](https://github.com/tezov/bank/assets/111152291/c5278a9f-ad60-44f1-8206-476570e974e7)
![image](https://github.com/tezov/bank/assets/111152291/2d4fa507-2a37-41f3-8ce7-fcf97604ea1a)
![image](https://github.com/tezov/bank/assets/111152291/47dc9eea-ac88-4979-ac56-1cdabea9dc0c)
![image](https://github.com/tezov/bank/assets/111152291/0bcf25b4-4498-45b3-b174-31a03e9a4917)
![image](https://github.com/tezov/bank/assets/111152291/f12042b0-bbd4-4080-a9e3-ea6c5afe3e2e)

Ce projet est actuellement focalisé sur l'UI en particulier une grosse structure sur le theming. Mes autres projets étaient davantage technique. 

Voir mon fichier TODO pour savoir où j'en suis.


