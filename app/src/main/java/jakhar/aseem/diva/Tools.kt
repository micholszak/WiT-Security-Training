package jakhar.aseem.diva

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.content.SharedPreferences

fun Context.getSharedPreferences(): SharedPreferences =
    getSharedPreferences("jakhar.aseem.diva.preferences", MODE_PRIVATE)