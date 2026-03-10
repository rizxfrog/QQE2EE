package me.fuckqq.e2ee

import me.fuckqq.e2ee.util.CryptoManager
import org.junit.Assert.*
import org.junit.Test
import java.security.SecureRandom

/**
 * CryptoManager 单元测试类
 * 测试加密、解密、BaseN 编码等核心功能
 */
class CryptoManagerTest {

    @Test
    fun testEncryptAndDecrypt() {
        val key = CryptoManager.generateKey()
        val message = "这是一条测试消息"
        val keyString = String(key.encoded, Charsets.UTF_8)
        val encrypted = CryptoManager.encrypt(message, keyString)
        println("encrypted: $encrypted")
    }


    // ==================== 密钥生成测试 ====================
//
//    @Test
//   fun `generateKey 应该生成有效的 AES-256 密钥`() {
//       val key = CryptoManager.generateKey()
//        assertNotNull("生成的密钥不应为 null", key)
//        assertEquals("AES 算法", "AES", key.algorithm)
//        assertEquals("密钥长度应为 256 位 (32 字节)", 32, key.encoded.size)
//    }
//
//    @Test
//   fun `generateKey 每次应该生成不同的密钥`() {
//       val key1 = CryptoManager.generateKey()
//       val key2 = CryptoManager.generateKey()
//
//        assertNotEquals("两次生成的密钥应该不同",
//            key1.encoded.joinToString(),
//            key2.encoded.joinToString()
//        )
//    }
//
//    // ==================== 字符串加解密测试 ====================
//
//    @Test
//   fun `encrypt 和 decrypt 应该正确往返加密解密字符串`() {
//       val originalMessage = "这是一条测试消息"
//       val key = "test_secret_key_123"
//
//       val encrypted = CryptoManager.encrypt(originalMessage, key)
//       val decrypted = CryptoManager.decrypt(encrypted, key)
//
//        assertNotNull("解密结果不应为 null", decrypted)
//        assertEquals("解密后的内容应与原文一致", originalMessage, decrypted)
//    }
//
//    @Test
//   fun `encrypt 应该对相同内容生成不同的密文`() {
//       val message = "Hello World"
//       val key = "same_key"
//
//       val encrypted1 = CryptoManager.encrypt(message, key)
//       val encrypted2 = CryptoManager.encrypt(message, key)
//
//        assertNotEquals("由于 IV 随机，两次加密结果应不同", encrypted1, encrypted2)
//    }
//
//    @Test
//   fun `decrypt 使用错误密钥应该返回 null`() {
//       val message = "Secret Message"
//       val correctKey = "correct_key"
//       val wrongKey = "wrong_key"
//
//       val encrypted = CryptoManager.encrypt(message, correctKey)
//       val decryptedWithWrongKey = CryptoManager.decrypt(encrypted, wrongKey)
//
//        assertNull("使用错误密钥解密应该返回 null", decryptedWithWrongKey)
//    }
//
//    @Test
//   fun `decrypt 空字符串应该返回 null 或空`() {
//       val key = "test_key"
//
//       val result= CryptoManager.decrypt("", key)
//
//        // 空字符串解密可能返回 null 或空字符串，取决于实现
//        assertTrue("空字符串解密应该返回 null 或空字符串",
//            result == null || result.isEmpty()
//        )
//    }
//
//    @Test
//   fun `应该支持中文和多语言字符加密`() {
//       val testMessages = listOf(
//            "中文测试消息",
//            "English Test Message",
//            "日本語テストメッセージ",
//            "Emoji 测试😀🎉🚀",
//            "混合 Mixed 文字 123 !@#"
//        )
//       val key = "multi_language_key"
//
//        testMessages.forEach { message ->
//           val encrypted = CryptoManager.encrypt(message, key)
//           val decrypted = CryptoManager.decrypt(encrypted, key)
//
//            assertNotNull("'$message' 解密结果不应为 null", decrypted)
//            assertEquals("'$message' 应该能正确解密", message, decrypted)
//        }
//    }
//
//    @Test
//   fun `应该支持长文本加密`() {
//       val longMessage = "A".repeat(10000) // 10000 字符
//       val key = "long_text_key"
//
//       val encrypted = CryptoManager.encrypt(longMessage, key)
//       val decrypted = CryptoManager.decrypt(encrypted, key)
//
//        assertNotNull("长文本解密结果不应为 null", decrypted)
//        assertEquals("长文本应该能完整解密", longMessage, decrypted)
//    }
//
//    // ==================== ByteArray 加解密测试 ====================
//
//    @Test
//   fun `encrypt ByteArray 和 decrypt ByteArray 应该正确往返`() {
//       val originalData = byteArrayOf(0x01, 0x02, 0x03, 0xFF, 0xFE, 0xFD)
//       val key = "byte_array_key"
//
//       val encrypted = CryptoManager.encrypt(originalData, key)
//       val decrypted = CryptoManager.decrypt(encrypted, key)
//
//        assertNotNull("解密结果不应为 null", decrypted)
//        assertArrayEquals("解密后的字节数组应与原文一致", originalData, decrypted)
//    }
//
//    @Test
//   fun `应该能加密空字节数组`() {
//       val emptyData = ByteArray(0)
//       val key = "empty_data_key"
//
//       val encrypted = CryptoManager.encrypt(emptyData, key)
//       val decrypted = CryptoManager.decrypt(encrypted, key)
//
//        assertNotNull("空数据解密结果不应为 null", decrypted)
//        assertArrayEquals("空数据应该能正确解密", emptyData, decrypted)
//    }
//
//    // ==================== BaseN 编码测试 ====================
//
//    @Test
//   fun `containsCiphertext 应该正确识别含密文字符串`() {
//       val message = "这是一条普通消息"
//       val key = "test_key"
//
//       val encrypted = CryptoManager.encrypt(message, key)
//
//        assertFalse("原始消息不应包含密文特征", message.containsCiphertext())
//        assertTrue("加密后的消息应包含密文特征", encrypted.containsCiphertext())
//    }
//
//    @Test
//   fun `containsCiphertext 应该处理混合字符串`() {
//       val message = "Hello 你好"
//       val key = "mixed_key"
//
//       val encrypted = CryptoManager.encrypt(message, key)
//       val mixedString = "一些前缀 $encrypted 一些后缀"
//
//        assertTrue("混合字符串中包含密文特征", mixedString.containsCiphertext())
//    }
//
//    // ==================== 密钥派生测试 ====================
//
//    @Test
//   fun `deriveKeyFromString 应该对相同输入生成相同密钥`() {
//       val keyString = "my_secret_password"
//
//       val derivedKey1 = CryptoManager.deriveKeyFromString(keyString)
//       val derivedKey2 = CryptoManager.deriveKeyFromString(keyString)
//
//        assertArrayEquals("相同字符串应派生出相同的密钥",
//            derivedKey1.encoded,
//            derivedKey2.encoded
//        )
//    }
//
//    @Test
//   fun `deriveKeyFromString 应该对不同输入生成不同密钥`() {
//       val keyString1 = "password1"
//       val keyString2 = "password2"
//
//       val derivedKey1 = CryptoManager.deriveKeyFromString(keyString1)
//       val derivedKey2 = CryptoManager.deriveKeyFromString(keyString2)
//
//        assertNotEquals("不同字符串应派生出不同的密钥",
//            derivedKey1.encoded.joinToString(),
//            derivedKey2.encoded.joinToString()
//        )
//    }
//
//    @Test
//   fun `deriveKeyFromString 应该生成 256 位密钥`() {
//       val keyString = "any_password"
//       val derivedKey = CryptoManager.deriveKeyFromString(keyString)
//
//        assertEquals("派生的密钥长度应为 256 位 (32 字节)", 32, derivedKey.encoded.size)
//    }
//
//    // ==================== 边界情况测试 ====================
//
//    @Test
//   fun `应该处理特殊字符密钥`() {
//       val message = "Test message"
//       val specialKey = "!@#$%^&*()_+-=[]{}|;':\",./<>?"
//
//       val encrypted = CryptoManager.encrypt(message, specialKey)
//       val decrypted = CryptoManager.decrypt(encrypted, specialKey)
//
//        assertNotNull("特殊字符密钥解密结果不应为 null", decrypted)
//        assertEquals("应该能用特殊字符密钥正确解密", message, decrypted)
//    }
//
//    @Test
//   fun `应该处理 Unicode 密钥`() {
//       val message = "Unicode test"
//       val unicodeKey = "密钥🔑キー🗝️"
//
//       val encrypted = CryptoManager.encrypt(message, unicodeKey)
//       val decrypted = CryptoManager.decrypt(encrypted, unicodeKey)
//
//        assertNotNull("Unicode 密钥解密结果不应为 null", decrypted)
//        assertEquals("应该能用 Unicode 密钥正确解密", message, decrypted)
//    }
//
//    @Test
//   fun `空密钥也应该能工作`() {
//       val message = "Empty key test"
//       val emptyKey = ""
//
//       val encrypted = CryptoManager.encrypt(message, emptyKey)
//       val decrypted = CryptoManager.decrypt(encrypted, emptyKey)
//
//        assertNotNull("空密钥解密结果不应为 null", decrypted)
//        assertEquals("应该能用空密钥正确解密", message, decrypted)
//    }
//
//    // ==================== 性能测试 ====================
//
//    @Test
//   fun `应该在合理时间内完成加密`() {
//       val message = "Performance test message"
//       val key = "perf_key"
//
//       val startTime = System.currentTimeMillis()
//        repeat(100) {
//            CryptoManager.encrypt(message, key)
//        }
//       val endTime = System.currentTimeMillis()
//
//       val totalTime = endTime - startTime
//        assertTrue("100 次加密应在 5 秒内完成", totalTime < 5000)
//       println("100 次加密耗时：${totalTime}ms")
//    }
//
//    @Test
//   fun `应该在合理时间内完成解密`() {
//       val message = "Performance test message"
//       val key = "perf_key"
//       val encrypted = CryptoManager.encrypt(message, key)
//
//       val startTime = System.currentTimeMillis()
//        repeat(100) {
//            CryptoManager.decrypt(encrypted, key)
//        }
//       val endTime = System.currentTimeMillis()
//
//       val totalTime = endTime - startTime
//        assertTrue("100 次解密应在 5 秒内完成", totalTime < 5000)
//       println("100 次解密耗时：${totalTime}ms")
//    }
}
