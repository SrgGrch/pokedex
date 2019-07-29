package com.srggrch.testapp.model

/**
 * @param name String
 * @param abilities PokemonAbility -> NamedAPIResource -> Ability
 * @param base_experience Int
 * @param forms NamedAPIResource -> PokemonForm
 * @param height Int
 * @param is_default Boolean
 * @param order Int
 * @param weight Int
 * @param held_items PokemonHeldItem -> NamedAPIResource -> Item
 * @param location_area_encounters String
 * @param moves List<PokemonMove> -> NamedAPIResource -> Move
 * @param sprites PokemonSprites
 * @param species NamedAPIResource -> PokemonSpecies
 * @param stats PokemonStat -> NamedAPIResource -> Stat
 * @param types PokemonType -> NamedAPIResource -> Type
 */
data class Pokemon(
    val name: String,
    val abilities: ArrayList<PokemonAbility>,
    val base_experience: Int,
    val forms: ArrayList<NamedAPIResource>,
    val height: Int,
    val is_default: Boolean,
    val order: Int,
    val weight: Int,
    val held_items: ArrayList<PokemonHeldItem>,
    val location_area_encounters: String,
    val moves: ArrayList<PokemonMove>,
    val sprites: PokemonSprites,
    val species: NamedAPIResource,
    val stats: ArrayList<PokemonStat>,
    val types: ArrayList<PokemonType>

)