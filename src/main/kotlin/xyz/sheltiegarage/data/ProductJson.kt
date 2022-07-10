
package xyz.sheltiegarage.data

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ProductJson(
    val productNo : Int,
    val productName : String,
    val productCateory : String,
    val productDescription : String,
    val updatedAt : String
)
