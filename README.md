# Soulflow

The Soulflow Mod uses the Impersonate library to add a variety of items that let you swap identities with other players! You can find these items in structures or from mob drops, and you can also craft them at the Alchemy Table.

###  Soulexchanging Items:

### Totem of Transference
The Totem of Transference will exchange your soul with that of a targeted player!

Obtained from:
- The Alchemy Table
- Killing Witches
- Woodland Mansions 
- Pillager Outposts

### Totem of the Void
The Totem of the Void exchanges your soul with someone random nearby!

Obtained from:
- The Alchemy Table
- End Cities 
- Strongholds

### Totem of Spores
The Totem of Spores mimics another player's appearance!

Obtained from:
- The Alchemy Table
- Killing Mooshrooms

### Sunken Grimoire
The Sunken Grimoire will turn you back into your real self!

Obtained from:
- Killing Drowneds
- Buried Treasure
- Shipwrecks

###  Crafting Items:

### Hollow Totem
Used as a base totem for the Alchemy Table!

Obtained from:
- Crafting it! (1 Netherite Scrap, 4 Iron Nuggets)
- Mineshafts 
- Mob Spawners 
- Ruined Portals 
- Woodland Mansions

### Soul
Used for crafting things in the Alchemy Table!

A soul is only obtained by smelting Soul Sand or Soul Soil!

### Alchemy Table
Used to craft totems!

It can only be obtained from crafting it! (6 Blackstone, 2 Amethyst Shards, 1 Soul)

Recipes:
- Fermented Spider Eye + Hollow Totem + Soul -> Totem of Transference
- Ender Eye + Hollow Totem + Soul -> Totem of the Void
- Red Mushroom Block + Hollow Totem + Soul -> Totem of Spores


## Commands

### /soultransform
A command exclusive to operators! It lets you set/clear anyone's appearance!

*The Impersonate Library brings its own command to do this, however it is recommended to use this one!*

### Gamerules:

| *Gamerule*  | *Purpose* |
| ------------- | ------------- |
| ```soulflow:exchangeInventories```  | Whether a soul exchange will swap the inventories of players!  |
| ```soulflow:exchangeEnderchest```  | Whether a soul exchange will swap the contents of players' ender chests!  |
| ```soulflow:exchangeHealth```  | Whether a soul exchange will swap the health of players!  |
| ```soulflow:exchangeEffects```  | Whether a soul exchange will swap the effects of players!  |
| ```soulflow:disablePrivateMessages```  | Disables the /msg command, so that it's harder to tell who's who!  |

On Default, all Gamerules are enabled!

However, the Impersonate Library brings its own gamerules!

| *Gamerule*  | *Purpose* |
| ------------- | ------------- |
| ```impersonate:fakeCapes```  | Whether impersonators should get the cape and elytra of impersonated players.   |
| ```impersonate:opRevealImpersonations```  | Whether ongoing impersonations should be revealed to online server operators.  |
| ```impersonate:logRevealImpersonations```  | Whether ongoing impersonations should be revealed in the server logs.   |

## Libraries

This project uses Impersonate by Pyrofab, licensed under LGPL-3.0-only.

The library's source code can be obtained from: https://github.com/Ladysnake/Impersonate
