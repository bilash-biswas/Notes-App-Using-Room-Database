package com.example.roomdatabaseinkotlin.Utility

import android.content.Context
import android.widget.Toast

class NotesUtility {
    companion object {
        fun toast(context: Context, message: String) {
            return Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}