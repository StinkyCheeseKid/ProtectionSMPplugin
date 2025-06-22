# ProtSMP - Protection Multiplier System Plugin

A comprehensive Minecraft plugin that implements a dynamic multiplier system with custom items, runes, and legendary weapons.

## Features

### 1. Player Multiplier System
- **Multiplier Persistence**: Player multipliers are stored in `multipliers.yml` and persist across server restarts
- **Visual Display**: Multipliers are shown in action bar and player names (e.g., "PlayerName §7[§e1.50x§7]")
- **Gain/Loss Mechanics**: 
  - +0.10x multiplier for each player kill
  - -0.10x multiplier on any death
  - Cannot go below 0.0x
- **Multiplier Cap**: Natural cap of 2.0x (can be increased by items)
- **Protection Rune Rewards**: Players at cap receive Protection Runes instead of multiplier increases
- **Armor Scaling**: Multiplier directly affects armor effectiveness
- **Auto-Ban**: Players dying with 0.0x multiplier are automatically banned

### 2. Custom Items & Runes

#### Protection Rune (CustomModelData: 1)
- **Effect**: Increases multiplier by 0.10x
- **Recipe**: DND/CSC/BNB (D=diamond block, N=netherstar, C=diamond chestplate, S=shield, B=netherite block)

#### Strength Rune (CustomModelData: 2)
- **Effect**: Doubles damage until death
- **Recipe**: NPN/CEC/NPN (N=netherite block, P=protection rune, C=diamond chestplate, E=dragon egg)

#### Heart Rune (CustomModelData: 3)
- **Effect**: Doubles max health (40 HP) until death
- **Recipe**: NPN/CEC/NPN (same as strength but different center)

#### Power Rune (CustomModelData: 4)
- **Effect**: Sets multiplier to 3.0x and cap to 3.0x until death
- **Recipe**: NPN/CEC/NPN (same pattern)

### 3. Legendary Items

#### Mace of Calamity (CustomModelData: 7)
- **Passive Effects**:
  - Sets max health to 40 HP
  - Sets multiplier to 3.5x and cap to 3.5x
  - Doubles all damage dealt
- **Right-Click**: Dash ability (10-second cooldown)
- **On-Kill**: Permanently bans killed players
- **Recipe**: HMC/SFR/LPB (H=netherite helmet, M=mace, C=netherite chestplate, S=strength rune, F=core of calamity, R=heart rune, L=netherite leggings, P=power rune, B=netherite boots)

#### Respawn Beacon (CustomModelData: 8)
- **Function**: Opens GUI to unban players banned by the plugin
- **Unban Effect**: Sets multiplier to 0.50x when players rejoin

### 4. Commands

#### Admin Commands
- `/giverune <type>` - Give yourself a rune or custom item
- `/viewmultiplier <player>` - View a player's multiplier and cap
- `/editmultiplier <player> <value>` - Set a player's multiplier
- `/removerunes <player>` - Remove all rune effects and items from a player

#### Player Commands
- `/extractrune <strength|heart|power>` - Extract an active rune effect and get the item back (5-second cooldown)

## Installation

1. **Download**: Place the compiled JAR file in your server's `plugins` folder
2. **Start Server**: The plugin will create necessary configuration files
3. **Permissions**: Ensure admins have the `protsmp.admin` and `protsmp.giverune` permissions
4. **Resource Pack**: For custom textures, create a resource pack with the CustomModelData values

## Configuration

### multipliers.yml
Automatically generated file storing player multiplier data:
```yaml
multipliers:
  <player-uuid>:
    value: 1.0
    cap: 2.0
```

### plugin.yml
Contains command definitions and permissions.

## Building from Source

1. **Prerequisites**: Java 17+, Maven
2. **Clone**: `git clone <repository-url>`
3. **Build**: `mvn clean package`
4. **Install**: Copy the generated JAR from `target/` to your server's `plugins/` folder

## Permissions

- `protsmp.giverune` - Allows giving runes and custom items (default: op)
- `protsmp.admin` - Allows admin commands (default: op)
- `protsmp.extractrune` - Allows extracting rune effects (default: true)
- `protsmp.unbanned` - Internal permission for unbanned players

## Technical Details

### Multiplier System
- Uses `runTaskTimer` to update action bar every second
- Persistent data stored in YAML format
- Real-time display name updates

### Custom Items
- All based on `CARROT_ON_A_STICK` with unique CustomModelData
- Persistent Data Containers for item identification
- Right-click functionality for all items

### Event Handling
- Player join/quit events for display updates
- Death events for multiplier changes and effect cleanup
- Combat events for damage scaling and kill rewards
- Crafting events for recipe validation and announcements

## Potential Exploits & Fixes

### 1. Duplication Exploits
**Risk**: Players might find ways to duplicate runes or items
**Fix**: Implement proper inventory checks and use `setItemInMainHand(null)` instead of `remove()`

### 2. Damage Scaling Issues
**Risk**: Multiple damage multipliers could stack exponentially
**Fix**: Current implementation uses additive stacking, consider multiplicative if needed

### 3. Mace of Calamity Abuse
**Risk**: Players might abuse the permanent ban feature
**Fix**: Add admin confirmation or cooldown for ban effects

### 4. Multiplier Manipulation
**Risk**: Players might find ways to manipulate their multiplier
**Fix**: Server-side validation and regular audits

## Support

For issues, feature requests, or questions, please create an issue in the repository.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
