package com.example.demo.model

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

class Item {
    @NotBlank(message = "Category field can not be blank!")
    var category: String? = null

    @NotBlank(message = "Name field can not be blank!")
    var name: String? = null

    @NotBlank(message = "Time for starting can not be blank!")
    var price: String? = null

    @NotBlank(message = "Time to end can not be blank!")
    var discount: String? = null

    @Future(message = "The booking date must be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var date: Date? = null
    var id: String

    init {
        id = UUID.randomUUID().toString()
    }
}
