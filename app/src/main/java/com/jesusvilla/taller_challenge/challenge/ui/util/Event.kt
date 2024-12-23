package com.jesusvilla.taller_challenge.challenge.ui.util

/**
 * Created by Jesus Villa on 23/12/24.
 *
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<T>(content: T?) {
    private val mContent: T

    private var hasBeenHandled = false


    init {
        requireNotNull(content) { "null values in Event are not allowed." }
        mContent = content
    }

    val contentIfNotHandled: T?
        get() {
            if (hasBeenHandled) {
                return null
            } else {
                hasBeenHandled = true
                return mContent
            }
        }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }

    override fun toString(): String {
        return "Event{" +
                "content=" + mContent +
                ", hasBeenHandled=" + hasBeenHandled +
                '}'
    }
}