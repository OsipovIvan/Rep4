package ru.osipov.nmediaapp.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var share: Int = 0,
    var views: Int = 0,
    var video: String = ""
){

    fun getLikes() = numberToString(likes)

    fun getShare() = numberToString(share)

    private fun numberToString(number: Int): String =
        when (number) {
            in 0 until 1_000 -> {
                number.toString()
            }
            1000 -> {
                "1K"
            }
            in 1_000 until 10_000 -> {
                String.format("%.1fK", (number / 1_000).toDouble())
            }
            in 10_000 until 1_000_000 -> {
                String.format("%dK", number / 1_000)
            }
            1_000_000 -> {
                "1M"
            }
            in 1_000_000 until Int.MAX_VALUE -> {
                String.format("%.1fM", (number / 1_000_000).toDouble())
            }
            else -> {
                ""
            }
        }
}
