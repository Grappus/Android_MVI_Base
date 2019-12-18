package com.grappus.logic.extensions

import org.junit.Test

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -
 */

class DatatypeExtensionsTest {
    @Test
    fun `assert float formatted string returns proper values `() {
        val f1 = 1.0f
        val f2: Float? = null
        val f3 = 1.3f

        assert(f1.toFormattedString() == "1")
        assert(f2.toFormattedString().isEmpty())
        assert(f3.toFormattedString() == "1.3")
    }

    @Test
    fun `assert int formatted string returns proper values `() {
        val i1 = 1
        val i2: Int? = null

        assert(i1.toFormattedString() == "1")
        assert(i2.toFormattedString().isEmpty())
    }

    @Test
    fun `assert when int is null it returns proper values`() {
        val i1: Int? = null
        val i2 = 99

        assert(i1.orZero() == 0)
        assert(i2.orZero() == i2)
    }

    @Test
    fun `assert when float is null it returns proper values`() {
        val i1: Float? = null
        val i2 = 99f

        assert(i1.orZero() == 0.0f)
        assert(i2.orZero() == i2)
    }

    @Test
    fun `assert when Long is null it returns proper values`() {
        val i1: Long? = null
        val i2 = 99L

        assert(i1.orZero() == 0L)
        assert(i2.orZero() == i2)
    }
}