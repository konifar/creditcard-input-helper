package io.konifar.cardinputhelper.validator

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class LuhnAlgorithmTest(
    private val input: String,
    private val output: Boolean
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<out Any?>> {
            // https://stripe.com/docs/testing#cards
            return listOf(
                arrayOf("4242424242424242", true),
                arrayOf("4000056655665556", true),
                arrayOf("5555555555554444", true),
                arrayOf("2223003122003222", true),
                arrayOf("5200828282828210", true),
                arrayOf("5105105105105100", true),
                arrayOf("378282246310005", true),
                arrayOf("371449635398431", true),
                arrayOf("6011111111111117", true),
                arrayOf("6011000990139424", true),
                arrayOf("3056930009020004", true),
                arrayOf("36227206271667", true),
                arrayOf("3566002020360505", true),
                arrayOf("6200000000000005", true),
                arrayOf("1234567890123456", false)
            )
        }
    }

    @Test
    fun from() {
        val actual = LuhnAlgorithm.isValid(input)
        assertEquals(output, actual)
    }
}