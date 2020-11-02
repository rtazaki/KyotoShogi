package io.github.rtazaki.kyotoshogi

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.putpiece_dialog.view.*

/**
 * 打ち駒ダイアログ
 * @param text 選択した駒の種類
 * @param turn 手番
 */
class PutPieceDialogFragment(text: CharSequence, turn: Boolean) : DialogFragment() {
    /**
     * 複数枚の駒表記(×2)を取り除き、駒名(表/裏)を返す。
     */
    private val items = when (text.replace(Regex("×\\d+"), "")) {
        "香" -> listOf<CharSequence>("香", "と")
        "銀" -> listOf<CharSequence>("銀", "角")
        "金" -> listOf<CharSequence>("金", "桂")
        "飛" -> listOf<CharSequence>("飛", "歩")
        else -> listOf<CharSequence>("玉", "玉")
    }

    /**
     * 駒の向きを、手番によって変える。
     */
    private val rotation = if (turn) 0.0F else 180.0F

    /**
     * ダイアログの作成
     * ボタン選択された場合、dismiss()で抜ける。
     * @param savedInstanceState
     * @return
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.putpiece_dialog, null)
        view.rotation = rotation
        val mainActivity = activity as MainActivity
        listOf(view.button1, view.button2).forEachIndexed { i, button ->
            button.text = items[i]
            button.setOnClickListener {
                mainActivity.setPutPieceName(items[i])
                dismiss()
            }
        }
        builder.setView(view)
        return builder.create()
    }
}
