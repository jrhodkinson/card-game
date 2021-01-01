# 0.1.0

## Major
- [Game] The next card to be available in the market is now visible to the right of the market
- [Meta] Introduce new 'Spectator Mode' - you can watch an active match by clicking 'spectate' next to it in the lobby
- [Game] The player who goes second receives an additional card in their first hand, Patience ('Acquire 2M. Vanish.')
- [Game] Reduce the number of cards available for purchase in the market to 5 (was 6)

## Minor
- [Meta] Players are now automatically removed from the queue if they leave the website
- [Meta] Introduce https://revelation218.com/library which lists all cards and structures in the game
- [Meta] Provide admin controls (only to Richard) to allow upgrades to take place without disrupting in progress games
- [Game] Cards that are purchased or gained via another a card are now gained to your play area, to be discarded at the end of your turn and join your deck (previously the card immediately joined your discard pile)
- [UI] Display the contents of your deck (in alphabetical order) in a tooltip (hover over "N cards in deck")
- [UI] Don't display the cost of cards in your hand, to avoid confusion
- [UI] Add a tooltip stating what needs to be selected next if the selected card requires a target (e.g. damage cards, purge cards)

## Card / Structure / Mechanic changes
- [New Mechanic] Cleanse: 'Remove another card in your hand from the game'
- [New Mechanic] Unplayable: Some cards cannot be played from your hand
- [New Mechanic] Some cards can now end your turn when played
- [New Card] Annihilate: Cost 3M. 'Cleanse 1. Vanish.'
- [New Card] Bargain: Cost 0M. 'On purchase, acquire 3M. Damage yourself 3. Vanish.'
- [New Card] Covenant: Cost 0M. 'On purchase, acquire 5M. Unplayable.'
- [New Card] Last Resort: Cost 2M. 'On purchase, draw 1. Unplayable.'
- [New Card] Last Request: Cost 1M. 'Purge 1. End your turn.'
- [New Card] Desperation: Cost 0M. 'Damage yourself 5. Purge 1.'
- [New Card] Needle: Cost 6M. 'Damage your opponent 3. Purge 1.'
- [New Card] Patience: Not purchasable. 'Acquire 2M. Vanish.'
- [New Card] Purify: Cost 7M. 'Cleanse 1.'
- [New Structure] Brimstone: Costs 5M and 5 health to construct. 9 health. 'At the end of your opponent's turn, damage them by 2 then increase future damage by 1.'
- [New Structure] Pawnbroker: Costs 9M. 8 health. 'Acquire 1M whenever you purge.'
- [New Structure] Soul Trader: Costs 4M. 1 health. 'Acquire 1M whenever you purge.'
- [New Structure] Siege Engine: Costs 7M. 8 health. 'At the end of your opponent's turn, damage them by 3.'
- [Removed Structure] Removed City
- [Removed Structure] Removed Outpost
- [Renamed Structure] Rename 'Artefact Smuggler' to 'Smuggler'
- [Balance] Cast Out: Cost increased to 3M (was 2M)

# 0.0.7
- [Game] Swap a starting Denarius and Small Favour with 2x Devotion. New starting deck: 4x Denarius, 3x Blunt Dagger, 2x Devotion, 1x Cast Out
- [Game] The initial market is now guaranteed to contain a Half Shekel and a Stater (was guaranteed to contain 2x Half Shekel only)
- [Game] The turn timer is now visible when there are <30 seconds left in a turn (was 15s)
- [New Card] Devotion: Not purchasable. 'Damage yourself 3. Acquire 2M.'
- [Balance] Aureus, Idol: Increase cost to 7M (was 5M)
- [Balance] Riches and Wonders: Increase cost to 8M (was 7M)
- [Balance] Approach Artefact Smuggler: Increase cost to 5M (was 3M)
- [Balance] Artefact Smuggler: Increase self damage on purchasing a card to 2 (was 1)

# 0.0.6
- [Account] Allow passwords to contain any printable ASCII character
- [Web] Always display the number of active games, even if it is 0

# 0.0.5
- [Game] Swap the two starting Half Shekels with Denarius and Cast Out. New starting deck: 5x Denarius, 3x Blunt Dagger, Cast Out, Small Favour
- [Game] Rename 'Siphon' to 'Siphon Essence'
- [Game] Rename 'Hideout' to 'Artefact Smuggler'
- [New Card] Miracle, Lesser: Cost 2M. 'Draw 2. Vanish.'
- [New Card] Leech: Cost 2M. 'Damage 1. Heal yourself 1.'
- [Balance] Miracle: Increase draw to 4 and cost to 6M (was 3 and 3M)
- [Balance] Aureus, Idol: Reduce mammon gain to 6M (was 8M)
- [Balance] Power, Idol: Increase damage to 8 and cost to 9M (was 7 and 7M)
- [Balance] Repent: Increase self damage to 7 (was 3)
- [Balance] (Hideout)/Artefact Smuggler: Reduce health to 4, increase mammon per turn to 5M, decrease damage when purchasing a card to 1 (was 5 health, 4M and 2 damage)
- [Balance] Sleight of Hand: Reduce draw, now is 'Damage 4. Draw 1.' (was 'Damage 4. Draw 2.')
- [Balance] Contingency Plan: Reduce draw and increase heal, now is 'Heal 3. Draw 1.' (was 'Draw 2. Heal 2.')
- [Balance] (Siphon)/Siphon Essence: Increase damage to 3 and cost to 6M (was 2 and 2M)
- [Balance] Charity: Reduce draw and heal, now is 'Heal your opponent 4. Draw 2.' (was 'Heal your opponent 7. Draw 3.')
- [Balance] City: Reduce health to 8 (was 9). Reduce initial damage to 2 (was 3)
- [Balance] Wrath: Increase cost to 8M (was 6M)
- [Balance] Holy Lance: Increase cost to 7M (was 6M)
- [Fix] Fix typo in 'Watchtower' structure name (was 'Watchower')

# 0.0.4
- [Balance] Wrath: Increase cost to 6 (was 4)
- [Balance] City: Reduce health to 9 (was 12)
- [Balance] Riches and Wonders: Decrease the mammon increment per turn to +1 (was +2); increase the damage on purchase to 5 (was 3)
- [Game] Increase the turn timer to 75s (was 60s)
- [Fix] Reword the help text for 'Gain' to specify that gained cards go directly to the discard pile (previously stated the card was added 'to your deck')
- [Fix] Display the winner of the match at the end of the match
- [QoL] Remove the delay before the help text pops up when hovering over a card
- [QoL] Change the text displayed when a player's discard pile is empty to be '0 cards in discard pile' (was 'empty discard pile')
- [QoL] Add structures' health to their descriptions

# 0.0.3
- Fix 'Hideout' and 'Watchtower' powers

# 0.0.2
- Set page title to 'Revelation 21:8' and add '(alpha)' to version indicator

# 0.0.1
- Initial Release
