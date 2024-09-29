package com.example.mediaplayer

class Music(val music: Int, val title: String) {
    companion object{
        val list = mutableListOf(
            Music(R.raw.agata_kristi, "Агата Кристи - На ковре вертолете"),
            Music(R.raw.imagine_dragons_thunder, "Imagine Dragon - Thunder"),
            Music(R.raw.michael_jackson_the_way_you_make_me_feel, "Michael Jackson - The way you make me feel"),
            Music(R.raw.queen_another_one_bites_the_dust, "Queen - Another one bites the dust"),
            Music(R.raw.queen_the_show_must_go_on, "Queen - Show must go on"),
            Music(R.raw.queen_we_will_rock_you, "Queen - We will rock you"),
            Music(R.raw.scorpions_still_loving_you, "Scorpions - Still loving you"),
            Music(R.raw.system_of_a_down_chop_suey, "System of a down - Chop suey"),
            Music(R.raw.the_cranberries_zombie, "The Cranberries - Zombie")
        )
    }
}