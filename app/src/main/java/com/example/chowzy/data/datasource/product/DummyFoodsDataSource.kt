package com.example.chowzy.data.datasource.product

import com.example.chowzy.data.model.Menu

class DummyFoodsDataSource : FoodsDataSource {
    override fun getFoods(): List<Menu> {
        return mutableListOf(
            Menu(
                name = "Cheese Burger",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_cheese_burger.jpg?raw=true",
                price = 15000.0,
                desc = "Beef burger with melted cheese slices and special sauce, served in a crispy bun.",
                location = "Burger King, 123 Main St"
            ),
            Menu(
                name = "Croissant",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_croissant.jpg?raw=true",
                price = 7000.0,
                desc = "Fresh croissant with soft layers baked to perfection, perfect for breakfast or a light snack.",
                location = "Bakery, 456 Elm St"
            ),
            Menu(
                name = "Dimsum",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_dimsum.jpg?raw=true",
                price = 9000.0,
                desc = "Assorted dim sum including dumplings, buns, and rolls, served with dipping sauces.",
                location = "Dim Sum Restaurant, 789 Oak St"
            ),
            Menu(
                name = "French Fries",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_french_fries.jpg?raw=true",
                price = 10000.0,
                desc = "Crispy golden French fries seasoned with salt, served hot and delicious.",
                location = "Fast Food Restaurant, 101 Pine St"
            ),
            Menu(
                name = "Fried Chicken",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_fried_chicken.jpg?raw=true",
                price = 14000.0,
                desc = "Juicy fried chicken pieces with a crispy coating, served with your choice of dipping sauce.",
                location = "Fried Chicken Joint, 222 Cedar St"
            ),
            Menu(
                name = "Grilled Chicken",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_grilled_chicken.jpg?raw=true",
                price = 20000.0,
                desc = "Tender grilled chicken breast seasoned to perfection, served with steamed vegetables.",
                location = "Grill House, 333 Maple St"
            ),
            Menu(
                name = "Noodle",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_noodle.jpg?raw=true",
                price = 10000.0,
                desc = "Savory noodle dish with your choice of meat or vegetables, served in a flavorful broth.",
                location = "Noodle House, 444 Walnut St"
            ),
            Menu(
                name = "Spaghetti",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_spaghetti.jpg?raw=true",
                price = 12000.0,
                desc = "Classic spaghetti pasta topped with rich marinara sauce and grated Parmesan cheese.",
                location = "Italian Restaurant, 555 Cherry St"
            ),
            Menu(
                name = "Sushi",
                imgUrl = "https://github.com/daudhiyaa/chowzy-assets/blob/main/product_img/food_sushi.jpg?raw=true",
                price = 13000.0,
                desc = "Fresh sushi rolls with a variety of fillings, served with soy sauce and wasabi.",
                location = "Sushi Bar, 666 Birch St"
            )
        )
    }

}