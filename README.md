# Oh, How the Tabletops Turn!

**A CPSC 210 Project Proposal by Liana-Khim Dy**

## Project Summary:

This application is an electronic turn-tracker for turn-based tabletop role-playing games (TTRPGs) such as Call of Cthulhu and Dungeons & Dragons. 
Specifically, this application is to be used by Game Masters who oversee the game and control non-player characters for players to battle with.
It will include a turn-tracker system that allows players to be assigned a turn order, declare the end of their turn, and follow the order of battle.
My passion for this project stems from my love of TTRPGs and board games, as well as my own grievances with many current turn-tracker systems found online that
fail to automatically calculate character health points, resulting in time-consuming calculations. With my application, I hope to restore flow and efficient 
storytelling to TTRPGs, as well as more action and fun gameplay!

## User Stories
- As a user, I want to be able to add player and enemy characters to my turn-tracker, complete with their character name, amount of health points, and amount of damage they can deal.
- As a user, I want to be able to view the turn-tracker as a list, with the first player/enemy at the top of the list and the upcoming players/enemies underneath.
- As a user, on each characters' turn, I want to be able to have a player deal damage to an enemy, resulting in the amount of damage dealt to be taken away from that enemy's health points.
- As a user, I want to be able to end a character's turn, resulting in their character going to the back of the line of the turn-tracker.
- As a user, I want to have the option to save the current state of the turn-tracker to file.
- As a user, I want to have the option to reload the state of the saved turn-tracker from file and resume from where I left off from an earlier time.

## Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking on the "Add Player" or "Add Enemy" buttons and filling in the required prompts.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking on the "FINISHED button and seeing the list of created characters. Once this has been done, you can click buttons to view the first character's health, damage others, and end their turn.
- You can locate my visual component by clicking the "View Character HP" button after adding characters and pressing "FINISHED".
- You can save the state of my application by clicking the "SAVE" button in the bottom left corner.
- You can reload the state of my application by clicking the "LOAD" button in the bottom left corner.

## PHASE 4 TASK 2

- PRINTING EVENTLOG:
-------------------
Added Player SansUndertale to turn tracker.
Wed Nov 29 12:18:00 PST 2023

Added Enemy BowserLuigi to turn tracker.
Wed Nov 29 12:18:16 PST 2023

Loaded previous Turn Tracker from file.
Wed Nov 29 12:18:20 PST 2023

Added Player Liana to turn tracker.
Wed Nov 29 12:18:21 PST 2023

Added Enemy EvilLiana to turn tracker.
Wed Nov 29 12:18:21 PST 2023

Accessed Liana's Health Points.
Wed Nov 29 12:18:28 PST 2023

Accessed EvilLiana's Health Points.
Wed Nov 29 12:18:33 PST 2023

Damage dealt to EvilLiana for 10.0 HP.
Wed Nov 29 12:18:36 PST 2023

Saved current Turn Tracker to file.
Wed Nov 29 12:18:40 PST 2023

Ended Liana's turn.
Wed Nov 29 12:18:41 PST 2023

Accessed EvilLiana's Health Points.
Wed Nov 29 12:18:42 PST 2023

Accessed Liana's Health Points.
Wed Nov 29 12:18:48 PST 2023

Damage dealt to Liana for 10.0 HP.
Wed Nov 29 12:18:49 PST 2023

Ended EvilLiana's turn.
Wed Nov 29 12:18:51 PST 2023

Accessed Liana's Health Points.
Wed Nov 29 12:18:53 PST 2023

Accessed EvilLiana's Health Points.
Wed Nov 29 12:18:59 PST 2023

Damage dealt to EvilLiana for 10.0 HP.
Wed Nov 29 12:19:01 PST 2023

Removed EvilLiana from turn tracker.
Wed Nov 29 12:19:02 PST 2023

Saved current Turn Tracker to file.
Wed Nov 29 12:19:07 PST 2023


## PHASE 4 TASK 3

If I had more time to work on my project, the main thing point of refactoring that I would do to improve my design  is
to implement better error handling, specifically in terms of my GUI. Currently, when one wants to add a character
(a player or an enemy) to the turn tracker, they are prompted with a popup to input the character's health points and 
damage they can do. However, if the user closes the popup, or inputs something that isn't a double number, then the 
application shows error messages and throws null pointer exceptions. As such, I believe that if I were to refactor the 
application to be better able to handle and catch these thrown exceptions, then this will improve the design and make 
the application more robust.

## CITATION
Parts of code modelled after sample application provided from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

pepe.jpg: https://biches-of-jacksfilms.fandom.com/wiki/Pepe