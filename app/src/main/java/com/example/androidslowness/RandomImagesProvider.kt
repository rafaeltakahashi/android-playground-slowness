package com.example.androidslowness

fun getRandomImage(seed: Int, width: Int, height: Int): String {
    return "https://picsum.photos/seed/$seed/$width/$height"
}

fun getRandomImages(amount: Int, firstSeed: Int = 0, width: Int, height: Int): List<String> {
    return (0..amount).map { n -> getRandomImage(firstSeed + n, width, height) }
}
