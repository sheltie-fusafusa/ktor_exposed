package xyz.sheltiegarage

import com.zaxxer.hikari.HikariDataSource
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.sheltiegarage.data.Product
import xyz.sheltiegarage.data.ProductJson
import java.time.LocalDateTime

//fun main() {
//    embeddedServer(Tomcat, port = 8080, host = "0.0.0.0") {
//        configureRouting()
//    }.start(wait = true)
//}

// こちらの記述を利用しないと、application.confが有効にならない
fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {

    val hikariCP = HikariDataSource()
    hikariCP.jdbcUrl = "jdbc:mysql://localhost:3306/docker"
    hikariCP.username = "docker"
    hikariCP.password = "docker"
    Database.connect(hikariCP)

    install(ContentNegotiation){
        json(Json {
            ignoreUnknownKeys = true
        })
    }

    routing {
        route("/crud") {
            get("/"){

                var response : MutableList<ProductJson> = mutableListOf()

                transaction {
                    Product.selectAll().forEach {
                        val row = ProductJson(it[Product.productNo],
                                              it[Product.productName],
                                              it[Product.productCateory],
                                              it[Product.productDescription],
                                              it[Product.updatedAt].toString())
                        response.add(row)
                    }
                }

                call.respond(response)
            }
            post("/") {
                val post = call.receive<ProductJson>()

                transaction {
                    var id = 0
                    transaction {
                        id = Product.insert {
                            it[Product.productName] = post.productName
                            it[Product.productCateory] = post.productCateory
                            it[Product.productDescription] = post.productDescription
                            it[Product.updatedAt] = LocalDateTime.now()
                        }get Product.productNo
                    }
                }

                call.respond("INSERT Finish")
            }
            put("/") {

                val post = call.receive<ProductJson>()

                transaction {
                    Product.update({ Product.productNo eq post.productNo}) {
                        it[Product.productName] = post.productName
                        it[Product.productCateory] = post.productCateory
                        it[Product.productDescription] = post.productDescription
                        it[Product.updatedAt] = LocalDateTime.now()
                    }
                }

                call.respond("UPDATE Finish ")
            }
            delete("/") {

                val post = call.receive<ProductJson>()

                transaction {
                    Product.deleteWhere {
                        Product.productNo eq post.productNo
                    }
                }

                call.respond("DELETE Product No:${post.productNo}")
            }
        }
    }
}