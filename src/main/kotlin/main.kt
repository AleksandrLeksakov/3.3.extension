fun main() {
    val chatService = ChatService()

    // Создаем два чата
    val chat1 = chatService.createChat(1)
    val chat2 = chatService.createChat(2)

    // Отправляем сообщения в чаты
    chatService.createMessage(chat1.id, 1, "Привет из чата 1")
    chatService.createMessage(chat2.id, 2, "Привет из чата 2")

    // Выводим количество непрочитанных чатов
    println("Непрочитанных чатов: ${chatService.getUnreadChatsCount()}") // 2

    // Выводим список всех чатов
    println("Список чатов:")
    for (chat in chatService.getChats()) {
        println("Чат ${chat.id} (пользователь ${chat.userId})")
    }

    // Выводим последние сообщения из чата 1
    println("Последние сообщения из чата 1:")
    for (message in chatService.getLastMessages(chat1.id)) {
        println(message)
    }

    // Получаем и выводим 2 сообщения из чата 1
    println("2 последних сообщения из чата 1:")
    val messages = chatService.getMessages(1, 2)
    for (message in messages) {
        println("${message.text} (прочитано: ${message.isRead})")
    }

    // Удаляем сообщение из чата 1
    chatService.deleteMessage(messages[0].id)

    // Выводим количество непрочитанных чатов
    println("Непрочитанных чатов: ${chatService.getUnreadChatsCount()}") // 1 (так как одно сообщение было удалено)

    // Удаляем чат 1
    chatService.deleteChat(chat1.id)

    // Выводим количество непрочитанных чатов
    println("Непрочитанных чатов: ${chatService.getUnreadChatsCount()}") // 0 (так как чат 1 был удален)
}