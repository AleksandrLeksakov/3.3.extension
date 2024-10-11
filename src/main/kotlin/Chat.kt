data class Chat(
    val id: Int,
    val userId: Int,
    // Список сообщений в чате
    val messages: MutableList<Message> = mutableListOf()
) {
    // Метод для проверки, есть ли непрочитанные сообщения в чате
    fun hasUnreadMessages(): Boolean {
        // Используем лямбду для проверки, есть ли хотя бы одно непрочитанное и неудаленное сообщение
        return messages.any { !it.isRead && !it.isDeleted }
    }
}