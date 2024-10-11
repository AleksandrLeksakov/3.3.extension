fun main() {
    val chatService = ChatService()

    // Отправляем сообщения в чаты (теперь чаты создаются автоматически)
    chatService.createMessage(1, 1, "Привет из чата 1") // Чат 1 будет создан автоматически
    chatService.createMessage(2, 2, "Привет из чата 2") // Чат 2 будет создан автоматически

    // Выводим количество непрочитанных чатов
    println("Непрочитанных чатов: ${chatService.getUnreadChatsCount()}") // 2

    // Выводим список всех чатов
    println("Список чатов:")
    for (chat in chatService.getChats()) {
        println("Чат ${chat.id} (пользователь ${chat.userId})")
    }

    // Выводим последние сообщения из чата 1
    println("Последние сообщения из чата 1:")
    for (message in chatService.getLastMessages(1)) {
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
    chatService.deleteChat(1)

    // Выводим количество непрочитанных чатов
    println("Непрочитанных чатов: ${chatService.getUnreadChatsCount()}") // 0 (так как чат 1 был удален)
}