package xyz.sheltiegarage.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Product: Table("product") {
    val productNo = integer("product_no").autoIncrement()
    val productName = varchar("product_name", 64)
    val productCateory = varchar("product_category", 20)
    val productDescription = varchar("product_description", 1024)
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(productNo, name = "product_no")

}
