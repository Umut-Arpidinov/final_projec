package com.example.demo.controller

import com.example.demo.model.Constants
import com.example.demo.model.Item
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
class WebController {
var items: MutableList<Item> = ArrayList<Item>()


@GetMapping("/")
fun getForm(model: Model, @RequestParam(required = false) id: String?): String? {
    val index = getIndexFromId(id)
    model.addAttribute("item", if (index == Constants.NOT_FOUND) Item() else items[index])
    model.addAttribute("categories", Constants.CATEGORIES)
    return "form"
}

@PostMapping("/submitItem")
fun handleSubmit(
    @Valid item: Item,
    result: BindingResult,
    redirectAttributes: RedirectAttributes,
    model: Model
): String? {
    model.addAttribute( "categories", Constants.CATEGORIES)
    println("Has errors?: " + result.hasErrors())
    println(item.name + "blank")
    if (result.hasErrors()) return "form"

    val index = getIndexFromId(item.id)
    var status: String = Constants.SUCCESS_STATUS
    if (index == Constants.NOT_FOUND) {
        items.add(item)
    } else if (within5Days(item.date, items[index].date)) {
        items[index] = item
    } else {
        status = Constants.FAILED_STATUS
    }
    redirectAttributes.addFlashAttribute("status", status)
    return "redirect:/inventory"
}

@GetMapping("/inventory")
fun getInventory(model: Model): String? {
    model.addAttribute("items", items)
    return "inventory"
}

fun getIndexFromId(id: String?): Int {
    for (i in items.indices) {
        if (items[i].id.equals(id)) return i
    }
    return Constants.NOT_FOUND
}

fun within5Days(newDate: Date?, oldDate: Date?): Boolean {
    return true
}

}
