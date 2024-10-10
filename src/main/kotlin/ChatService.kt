import java.util.*

class ChatService {
    // Храним все чаты в Map, где ключ - ID чата, а значение - сам Chat
    private val chats = mutableMapOf<Int, Chat>()
    // Счетчик для генерации уникальных ID чатов
    private var chatIdCounter = 1

    // Метод для получения количества непрочитанных чатов
    fun getUnreadChatsCount(): Int {
        // Используем лямбду для фильтрации чатов, которые имеют непрочитанные сообщения
        return chats.values.count { it.hasUnreadMessages() }
    }

    // Метод для получения списка всех чатов
    fun getChats(): List<Chat> {
        // Преобразуем Map в список с помощью values.toList()
        return chats.values.toList()
    }

    // Метод для получения последних 5 сообщений из чата
    fun getLastMessages(chatId: Int): List<String> {
        // Получаем чат по ID, если его нет - возвращаем пустой список
        val chat = chats[chatId] ?: return emptyList()
        // Используем extension-функцию takeLast для получения последних 5 сообщений
        // Затем используем map для преобразования каждого сообщения в текст
        return chat.messages.takeLast(5).map { it.text } // Используем extension-функции
    }

    // Метод для получения сообщений от пользователя с заданным ID
    // count - количество сообщений, которые нужно вернуть
    fun getMessages(userId: Int, count: Int): List<Message> {
        // Находим чат с пользователем, если чата нет - возвращаем пустой список
        val chat = chats.values.find { it.userId == userId } ?: return emptyList()
        // Получаем последние count сообщений из чата
        val messages = chat.messages.takeLast(count).toMutableList()
        // Используем лямбду для пометки всех полученных сообщений как прочитанные
        messages.forEach { it.isRead = true }
        return messages
    }

    // Метод для создания нового сообщения в чате
    fun createMessage(chatId: Int, userId: Int, text: String): Message {
        // Находим чат по ID, если его нет - бросаем исключение
        val chat = chats[chatId] ?: throw ChatNotFoundException("Chat with id $chatId not found.")
        // Создаем новое сообщение
        val message = Message(messageIdCounter++, chatId, userId, text)
        // Добавляем сообщение в список сообщений чата
        chat.messages.add(message)
        return message
    }

    // Метод для удаления сообщения по ID
    fun deleteMessage(messageId: Int): Boolean {
        // Используем flatMap для поиска сообщения по всем чатам
        val message = chats.values.flatMap { it.messages }
            .find { it.id == messageId }
        // Если сообщение найдено, помечаем его как удаленное
        if (message != null) {
            message.isDeleted = true
            return true
        }
        return false
    }

    // Метод для создания нового чата с пользователем
    fun createChat(userId: Int): Chat {
        // Создаем новый Chat с уникальным ID
        val chat = Chat(chatIdCounter++, userId)
        // Добавляем новый чат в Map
        chats[chat.id] = chat
        return chat
    }

    // Метод для удаления чата по ID
    fun deleteChat(chatId: Int): Boolean {
        // Если чат с заданным ID существует, удаляем его из Map
        if (chats.containsKey(chatId)) {
            chats.remove(chatId)
            return true
        }
        return false
    }

    // Счетчик для генерации уникальных ID сообщений
    private var messageIdCounter = 1
}
