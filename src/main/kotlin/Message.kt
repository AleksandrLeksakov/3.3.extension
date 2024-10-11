data class Message(
    val id: Int,
    val chatId: Int,
    val userId: Int,
    val text: String,
    // Флаг, указывающий, прочитано ли сообщение
    var isRead: Boolean = false,
    // Флаг, указывающий, удалено ли сообщение
    var isDeleted: Boolean = false
)