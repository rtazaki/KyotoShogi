package io.github.rtazaki.kyotoshogi

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment

class PutPieceDialogFragment(private val hands: Button) : DialogFragment() {
    private val items = when (hands.text.replace(Regex("×\\d+"), "")) {
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
                hands.setBackgroundResource(R.drawable.button_background_select)
                mainActivity.setPutPiece(items[which])
            }
            .setNegativeButton("Cancel", null)
        return builder.create()
    }
}
