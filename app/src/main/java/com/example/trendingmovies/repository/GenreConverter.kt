package com.example.trendingmovies.repository

import androidx.room.TypeConverter

/**
 * type converter class for list
 */
class GenreConverter {
    @TypeConverter
    fun convertListToString(idList: List<Int>): String{
        val genreList = mutableListOf<String>()
        idList.forEach{
            genreList.add(it.toString())
        }
        return genreList.joinToString(",")
    }

    @TypeConverter
    fun convertStringToList(str: String): List<Int> {
        val intList = mutableListOf<Int>()
        str.split(",").forEach{
            intList.add(it.toInt())
        }
        return intList
    }

    @TypeConverter
    fun convertListToString2(idList: List<String>): String{
        val genreList = mutableListOf<String>()
        idList.forEach{
            genreList.add(it.toString())
        }
        return genreList.joinToString(",")
    }

    @TypeConverter
    fun convertStringToList2(str: String): List<String> {
        val strList = mutableListOf<String>()
        str.split(",").forEach{
            strList.add(it)
        }
        return strList
    }
}