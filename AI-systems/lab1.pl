% Факты
% Существо
:- dynamic creature/1.
creature(peeper).
creature(bladderfish).
creature(oculus).
creature(stalker).
creature(reefback).
creature(crabsnake).
creature(leviathan).
creature(gasopod).

% Оружие
weapon(hand).
weapon(knife).
weapon(propulsionGun).
weapon(laser).

% Транспорт
:- dynamic transport/1.
transport(flippers).
transport(glider).
transport(moth).
transport(cyclops).
transport(crabCostume).

% Локация
:- dynamic biome/1.
biome(crashsite).
biome(bigReef).
biome(kelpForest).
biome(underwaterIslands).
biome(jellyShroomCaves).
biome(shores).

% Единицы жизни существа
:- dynamic hp/2.
hp(peeper, 20).
hp(bladderfish, 30).
hp(oculus, 40).
hp(stalker, 110).
hp(reefback, 1300).
hp(crabsnake, 160).
hp(leviathan, 290).
hp(gasopod, 140).

% Максимальное расстояние, которое можно преодолеть на транспорте
max_distance(flippers, 600).
max_distance(glider, 2200).
max_distance(moth, 5600).
max_distance(cyclops, 12400).
max_distance(crabCostume, 3600).

% Среда обитания существа
:- dynamic habitat/2.
habitat(crashsite, peeper).
habitat(bigReef, peeper).
habitat(kelpForest, bladderfish).
habitat(underwaterIslands, bladderfish).
habitat(jellyShroomCaves, oculus).
habitat(crashsite, stalker).
habitat(bigReef, reefback).
habitat(jellyShroomCaves, crabsnake).
habitat(shores, leviathan).
habitat(crashsite, gasopod).

% Урон оружия
weapon_damage(hand, 40).
weapon_damage(knife, 120).
weapon_damage(propulsionGun, 230).
weapon_damage(laser, 300).

% Количество существ
:- dynamic creature_population/2.
creature_population(peeper, 100).
creature_population(bladderfish, 120).
creature_population(oculus, 70).
creature_population(stalker, 40).
creature_population(reefback, 10).
creature_population(crabsnake, 30).
creature_population(leviathan, 5).
creature_population(gasopod, 50).

% Площадь локации
biome_area(crashsite, 500).
biome_area(kelpForest, 300).
biome_area(jellyShroomCaves, 140).
biome_area(bigReef, 700).
biome_area(shores, 650).
biome_area(underwaterIslands, 540).

% Удаленность локации от изначальной точки
biome_remoteness(crashsite, 0).
biome_remoteness(kelpForest, 50).
biome_remoteness(jellyShroomCaves, 550).
biome_remoteness(bigReef, 1500).
biome_remoteness(shores, 2500).
biome_remoteness(underwaterIslands, 5700).

% Реализация итерации по списку локаций и проверке на возможность встретить существо
iterate([]).
iterate(Creature, [H|T], Transport, Probability) :-
    can_reach(H, Transport),
    meet_is_possible(Creature, H, Probability),
    write("В биоме '"), write(H), write("' вы сможете поймать существо '"), 
    write(Creature), write("'"), nl,
    iterate(Creature, T, Transport, Probability).

% Правило можно ли встретить существо, находясь в указанной локации, с указанной вероятностью
meet_is_possible(Creature, Biome, Probability) :-
   	creature(Creature), 
    biome(Biome),
    findall(X, habitat(X, Creature), Habitats),
    contains_term(Biome, Habitats),
    biome_area(Biome, Area), 
    creature_population(Creature, Pop),
    (Pop / Area >= Probability).

% Правило можно ли убить существо указанным оружием
can_kill(Creature, Weapon) :-
    creature(Creature),
    weapon(Weapon),
    hp(Creature, Hp), weapon_damage(Weapon, Dmg), Hp =< Dmg.

% Правило можно ли добраться до локации на указанном транспорте
can_reach(Biome, Transport) :-
    biome(Biome), transport(Transport),
    biome_remoteness(Biome, Rem), max_distance(Transport, Dis), Rem < Dis.

% Правило можно ли поймать существо с указанной вероятностью
catch_is_possible(Creature, Transport, Probability) :-
    creature(Creature), 
    transport(Transport),
    findall(X, habitat(X, Creature), Habitats),
    iterate(Creature, Habitats, Transport, Probability).    

% Добавление нового существа в базу знаний
:- dynamic add_creature/3.
add_creature(Name, Habitat, Population, Hp) :-
    biome(Habitat),
	assert(creature(Name)),
    assert(hp(Name, Hp)),
	assert(habitat(Habitat, Name)),
    assert(creature_population(Name, Population)).	

% Добавление нового транспорта в базу знаний
:- dynamic add_transport/2.
add_transport(Name, Distance) :-
	assert(transport(Name)),
    assert(max_distance(Name, Distance)).
	