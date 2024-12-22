# Damage API

The Damage API is a robust and modular system designed to handle custom combat, damage, and death mechanics for Minecraft servers. It provides server administrators and developers with fine-grained control over player and entity interactions, enhancing gameplay and enabling advanced functionality.

<br>

---

## Features

### Combat System
- **Combat Management**: Track and manage player combat states using `CombatManager`.
- **Custom Combat Events**: Extendable events for tracking and reacting to combat actions, including:
    - `CombatReceiveEvent`
    - `CombatRemoveEvent`
    - `CombatUpdaterEvent`
- **Combat Logging**: Prevents combat logging by tracking and handling player disconnects during combat.
- **Combat Modules**: Modular handlers for:
    - Damage during combat
    - Player death events
    - Combat state updates

### Damage Mechanics
- **Custom Damage Events**: Fine-tune how damage is dealt, mitigated, or displayed using events such as:
    - `CustomDamageEvent`
    - `CustomPreDamageEvent`
    - `CustomPostDamageEvent`
- **Damage Types and Reasons**: Specify unique damage reasons using `DamageReason`.
- **Armor and Weapon Durability**: Track and modify durability through:
    - `ArmourDurabilityEvent`
    - `WeaponDurabilityEvent`
- **Custom Knockback**: Control knockback effects using `CustomKnockbackEvent`.
- **Damage Modules**:
    - Potion effects
    - Sound effects
    - Knockback handling
    - Armor reduction calculations

### Death System
- **Custom Death Events**: Fully customizable death mechanics and messages through events such as:
    - `CustomDeathEvent`
    - `CustomDeathMessageEvent`
- **Death Modules**:
    - Summarize player deaths
    - Remove damage data upon death

### Utility Libraries
- **Damage Utilities**: Simplify handling of damage-related data and operations through `UtilDamage`.
- **Constants**: Predefined constants for managing damage and combat settings.

### Developer Tools
- **Interfaces and Abstract Classes**: Extend and customize core functionality using `ICombat`, `IDamageEvent`, and more.
- **Modular Handlers**: Plug-and-play modules for specific combat and damage use cases.

<br>

---

## Integration

The Damage API is designed to integrate seamlessly with other plugins. Use its modular architecture and comprehensive events to extend functionality or synchronize with other systems like combat logging or damage calculations.

<br>

---

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests to improve the API.
