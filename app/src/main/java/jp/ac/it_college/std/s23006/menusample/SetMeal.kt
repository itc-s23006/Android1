package jp.ac.it_college.std.s23006.menusample

import kotlinx.serialization.Serializable

@Serializable
data class SetMeal(
    val id: Long,
    val name: String,
    val price: Int,
    val desc: String
)