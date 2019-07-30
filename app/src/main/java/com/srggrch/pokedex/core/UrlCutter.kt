package com.srggrch.pokedex.core


class UrlCutter {

    companion object {
        fun cut(url: String): String{
            return return url.replace("https://pokeapi.co/api/v2/", "")
        }
    }

}