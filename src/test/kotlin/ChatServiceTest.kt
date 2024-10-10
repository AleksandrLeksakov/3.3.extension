import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class ChatServiceTest {
    private lateinit var chatService: ChatService

    @Before
    fun setUp() {
        chatService = ChatService()
    }

    // Тест для метода getUnreadChatsCount
    @Test
    fun `should get unread chats count`() {
        val chat1 = chatService.createChat(1)
        chatService.createMessage(chat1.id, 1, "Message 1")
        val chat2 = chatService.createChat(2)
        chatService.createMessage(chat2.id, 2, "Message 2")

        assertEquals(2, chatService.getUnreadChatsCount())
    }

    // Тест для метода getChats
    @Test
    fun `should get chats`() {
        val chat1 = chatService.createChat(1)
        val chat2 = chatService.createChat(2)

        val chats = chatService.getChats()
        assertEquals(2, chats.size)
        assertTrue(chats.contains(chat1))
        assertTrue(chats.contains(chat2))
    }

    // Тест для метода getLastMessages
    @Test
    fun `should get last messages`() {
        val chat = chatService.createChat(1)
        chatService.createMessage(chat.id, 1, "Message 1")
        chatService.createMessage(chat.id, 1, "Message 2")
        chatService.createMessage(chat.id, 1, "Message 3")
        chatService.createMessage(chat.id, 1, "Message 4")
        chatService.createMessage(chat.id, 1, "Message 5")

        val lastMessages = chatService.getLastMessages(chat.id)
        assertEquals(5, lastMessages.size)
        assertEquals("Message 1", lastMessages[0])
        assertEquals("Message 2", lastMessages[1])
        assertEquals("Message 3", lastMessages[2])
        assertEquals("Message 4", lastMessages[3])
        assertEquals("Message 5", lastMessages[4])
    }

    // Тест для метода getLastMessages, когда чат пуст
    @Test
    fun `should get last messages when chat has no messages`() {
        val chat = chatService.createChat(1)
        val lastMessages = chatService.getLastMessages(chat.id)

        assertTrue(lastMessages.isEmpty())
    }

    // Тест для метода getMessages
    @Test
    fun `should get messages by user id and count`() {
        val chat = chatService.createChat(1)
        chatService.createMessage(chat.id, 1, "Message 1")
        chatService.createMessage(chat.id, 1, "Message 2")
        chatService.createMessage(chat.id, 1, "Message 3")
        chatService.createMessage(chat.id, 1, "Message 4")
        chatService.createMessage(chat.id, 1, "Message 5") // 5 сообщений от пользователя 1

        val messages = chatService.getMessages(1, 2) // Получаем 2 последних сообщения

        assertEquals(2, messages.size) // Проверяем размер списка сообщений
        assertEquals("Message 4", messages[0].text) // Проверяем текст первого сообщения (последнего)
        assertEquals("Message 5", messages[1].text) // Проверяем текст второго сообщения (предпоследнего)
    }

    // Тест для метода getMessages, когда нет сообщений для пользователя
    @Test
    fun `should get empty list when no messages for user`() {
        val messages = chatService.getMessages(1, 3)
        assertTrue(messages.isEmpty())
    }

    // Тест для метода createMessage
    @Test
    fun `should create message`() {
        val chat = chatService.createChat(1)
        val message = chatService.createMessage(chat.id, 1, "Test Message")

        assertEquals(1, chat.messages.size)
        assertEquals(message, chat.messages[0])
    }

    // Тест для метода createMessage, когда чата не существует
    @Test(expected = ChatNotFoundException::class)
    fun `should throw exception when creating message for non-existent chat`() {
        chatService.createMessage(100, 1, "Test Message")
    }

    // Тест для метода deleteMessage
    @Test
    fun `should delete message`() {
        val chat = chatService.createChat(1)
        val message = chatService.createMessage(chat.id, 1, "Test Message")
        assertTrue(chatService.deleteMessage(message.id))

        assertFalse(chat.messages.any { it.id == message.id && !it.isDeleted })
    }

    // Тест для метода deleteMessage, когда нужно удалить сообщение в конкретном чате
    @Test
    fun `should delete message in specific chat`() {
        val chat1 = chatService.createChat(1)
        val chat2 = chatService.createChat(2)
        val message1 = chatService.createMessage(chat1.id, 1, "Test Message 1")
        val message2 = chatService.createMessage(chat2.id, 2, "Test Message 2")

        assertTrue(chatService.deleteMessage(message1.id))
        assertFalse(chat1.messages.any { it.id == message1.id && !it.isDeleted })
        assertTrue(chat2.messages.any { it.id == message2.id && !it.isDeleted })
    }

    // Тест для метода createChat
    @Test
    fun `should create chat`() {
        val chat = chatService.createChat(1)

        assertEquals(1, chat.id)
        assertEquals(1, chat.userId)
        assertTrue(chat.messages.isEmpty())
    }

    // Тест для метода deleteChat
    @Test
    fun shouldDeleteChat() {
        val chat = chatService.createChat(1) // Создаем чат
        val chatId = chat.id // Получаем ID созданного чата
        assertTrue(chatService.deleteChat(chatId)) // Удаляем чат по ID

        assertNull(chatService.getChats().stream().filter { c: Chat -> c.id == chatId }.findFirst().orElse(null))

        // Проверяем, что чат больше не существует в Map
    }

    // Тест для метода deleteChat, когда чата не существует
    @Test
    fun `should not delete chat if it doesn't exist`() {
        assertFalse(chatService.deleteChat(100))
    }
}