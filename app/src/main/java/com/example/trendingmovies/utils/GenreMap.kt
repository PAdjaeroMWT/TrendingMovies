package com.example.trendingmovies.utils

/**
 * id to genre category mappings
 */
class GenreMap {
    companion object{
        private val genreMap = mapOf<Int, String>(
            12 to "Adventure",
            14 to "Fantasy",
            16 to "Animation",
            18 to "Drama",
            27 to "Horror",
            28 to "Action",
            35 to "Comedy",
            36 to "History",
            37 to "Western",
            53 to "Thriller",
            80 to "Crime",
            99 to "Documentary",
            878 to "Science Fiction",
            9648 to "Mystery",
            10402 to "Music",
            10749 to "Romance",
            10751 to "Family",
            10752 to "War",
            10770 to "TV Movie"
        )

        fun getGenre(genreIdList: List<Int>?): String {
            return if(genreIdList != null){
                val genreList = mutableListOf<String>()
                genreIdList.forEach{
                    genreList.add(getGenreName(it))
                }
                genreList.joinToString(separator = ", ")
            }else{
                ""
            }
        }

        private fun getGenreName(id: Int?): String {
            return if(id != null){
                genreMap[id].toString()
            }else{
                ""
            }
        }
    }
}