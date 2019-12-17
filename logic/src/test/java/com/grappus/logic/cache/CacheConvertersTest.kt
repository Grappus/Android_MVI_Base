package com.grappus.logic.cache

import com.grappus.logic.dagger.BaseLogicTest
import org.junit.Test

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -CacheConvertersTest
 */

class CacheConvertersTest : BaseLogicTest() {
    val converters = CacheConverters()

    @Test
    fun `list string to list and vice versa conversion happens correctly`() {
        val list = listOf("1")
        val convertedString = converters.fromListStringToString(list)!!
        assert(convertedString == "[\"1\"]")
        val convertedList = converters.fromStringToListString(convertedString)
        assert(list == convertedList)
    }
}