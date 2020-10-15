package io.github.rtazaki.kyotoshogi

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class PutPieceDialogFragment(piece: CharSequence) : DialogFragment() {
    private val items = when (piece) {
        "香" -> arrayOf<CharSequence>("香", "と")
        "銀" -> arrayOf<CharSequence>("銀", "角")
        "金" -> arrayOf<CharSequence>("金", "桂")
        "飛" -> arrayOf<CharSequence>("飛", "歩")
        else -> arrayOf<CharSequence>("玉", "玉")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
            .setTitle("駒を選んでください")
            .setItems(items) { _, which ->
                val mainActivity = activity as MainActivity
                mainActivity.getPutPiece(items[which])
            }
            .setNegativeButton("Cancel", null)
        return builder.create()
    }
}