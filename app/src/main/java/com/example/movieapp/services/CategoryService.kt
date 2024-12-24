package com.example.movieapp.services

import com.example.movieapp.models.CategoryItem

class
CategoryService {
    fun getCategories(): List<CategoryItem> {
        return listOf(
            CategoryItem(1, "url_to_image_1", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(2, "url_to_image_2", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(3, "url_to_image_3", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(4, "Panda", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(5, "The Flash", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(6, "The Arrow", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(7, "The Flash", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(8, "The Arrow", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(9, "The Flash", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg"),
            CategoryItem(10, "The Arrow", "https://res.cloudinary.com/dzogfvwih/image/upload/v1705938854/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY_._V1__uifapv.jpg")
            // Add more categories as needed
        )
    }
}