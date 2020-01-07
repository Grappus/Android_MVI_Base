package com.grappus.android.api.dispatcher

import android.annotation.SuppressLint
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import java.io.IOException

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - LocalResponseDispatcher built on top of QueueDispatcher to add our test specific scenarios
 */

@SuppressLint("DefaultLocale")
class LocalResponseDispatcher : QueueDispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        val mockResponse = MockResponse()
        val scenario = getScenario(request)
        if (scenario != null) {
            try {
                mockResponse.apply {
                    setBody(readFile(scenario))
                    setResponseCode(200)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return mockResponse
    }

    //Manipulating the default Scenario obtained by [QueueDispatcher]
    private fun getScenario(request: RecordedRequest): String? {
        var scenerio = ""
        val path = request.path
        val requestedMethod = request.method.toLowerCase()

        scenerio += requestedMethod + path.replace("/", "_").replace("?", "_") + ".json"

        return scenerio
    }

    private fun readFile(jsonFileName: String): String {
        val inputStream =
            LocalResponseDispatcher::class.java.getResourceAsStream("/$jsonFileName")
                ?: throw NullPointerException("Have you added local resources correctly? Hint: name it as: $jsonFileName")

        val buffer = Buffer()
        buffer.readFrom(inputStream)
        return buffer.readUtf8()
    }
}