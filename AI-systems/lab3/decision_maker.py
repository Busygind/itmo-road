from pyswip import Prolog

prolog = Prolog()
prolog.consult("lab1.pro")


def query(msg: str) -> bool:
    return list(prolog.query(msg))


def validate_weapons(weapons: list):
    for weapon in weapons:
        if not query(f"weapon({weapon})"):
            print("There is no weapon named", weapon)
            exit()


def validate_transport(transport_list: list):
    for transport in transport_list:
        if not query(f"transport({transport})"):
            print("There is no transport named", transport)
            exit()


def get_available_biomes(transport_list: list) -> list:
    biomes = []
    for transport in transport_list:
        for el in prolog.query(f"can_reach(Biome, {transport})"):
            biomes.append(el['Biome'])
    return biomes


def get_vulnerable_creatures(damages: dict) -> list:
    max_damage_weapon = max(damages, key=damages.get)
    vulnerable_creatures = []
    for el in prolog.query(f"can_kill(Creature,{max_damage_weapon})"):
        vulnerable_creatures.append(el['Creature'])
    return vulnerable_creatures


def get_weapon_damages(weapons: list) -> dict:
    damages = {}
    for weapon in weapons:
        for dmg in prolog.query(f"weapon_damage({weapon},Y)"):
            damages[weapon] = dmg['Y']
    return damages


def get_creatures_by_available_biomes(biomes: list, vul_creatures: list) -> list:
    creatures = []
    for creature in vul_creatures:
        for biome in biomes:
            if query(f"habitat({biome}, {creature})"):
                creatures.append(creature)
                break
    return creatures


def get_all_creatures() -> list:
    creatures = []
    for el in prolog.query(f"creature(Y)"):
        creatures.append(el['Y'])
    return creatures


print("Input weapons you have (use space as separator): ", end="")
weapons = input().split(" ")
validate_weapons(weapons)

print("Input transport you have (use space as separator): ", end="")
transport = input().split(" ")
validate_transport(transport)

biomes = get_available_biomes(transport)
print("You can reach the following biomes:", ', '.join(biomes))

creatures_can_kill = get_vulnerable_creatures(get_weapon_damages(weapons))
available_creatures = get_creatures_by_available_biomes(biomes, creatures_can_kill)
print("There you can catch and kill the following creatures:", ', '.join(available_creatures))

creatures_diff = set(creatures_can_kill) - set(available_creatures)
if len(creatures_diff) > 0:
    print("Also, if you create a better transport, you can kill more creatures in other biomes, like:",
          ', '.join(creatures_diff))
