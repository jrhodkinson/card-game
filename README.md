# Card Game

This game is deployed and is playable here: https://revelation218.com/

This is a large side project I have been working on since early 2020.
It is a deck-building card game (like Dominion) which is playable online (like Hearthstone).
Think Slay the Spire meets Magic: The Gathering.

## Some technical details

The bulk of the game is written in Java 15, and the front end is written in React.
The architecture of the game allows for complex card behaviour to be composed in data files, which allows for fast iteration.

For instance, [this card](https://github.com/jrhodkinson/card-game/blob/master/card-game-implementation/src/main/resources/assets/cards/money/ill-gotten-gains.json):
```json
{
  "id": "ILL_GOTTEN_GAINS",
  "name": "Ill-gotten Gains",
  "cost": 2,
  "purchasable": true,
  "behaviours": [
    [
      "damageOnPurchase",
      {
        "amount": 3,
        "targets": ["OTHER"]
      }
    ],
    [
      "money",
      2
    ]
  ]
}

```

Ill-gotten Gains composes the behaviours `damageOnPurchase` and `money`.
A description is automatically generated for the card ("On purchase, damage your opponent 3. Acquire 2M."), and the behaviours are implemented here:
https://github.com/jrhodkinson/card-game/blob/master/card-game-implementation/src/main/java/jrh/game/card/behaviour/DamageOnPurchaseBehaviour.java
https://github.com/jrhodkinson/card-game/blob/master/card-game-implementation/src/main/java/jrh/game/card/behaviour/MoneyBehaviour.java

A list of all cards can be found here: https://revelation218.com/library

## Caveat 

Please note, the game is a work in progress, and there are many things I would like to change.
In particular there is currently no artwork, and whilst there are some automated tests, there are not nearly as many as I would like.
