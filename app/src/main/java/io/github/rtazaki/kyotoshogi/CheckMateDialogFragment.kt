package io.github.rtazaki.kyotoshogi

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * 詰みダイアログ
 * @param turn 手番
 */
class CheckMateDialogFragment(turn: Boolean) : DialogFragment() {
    /**
     *勝者を、手番によって変える。
     */
    private val winner = if (!turn) "先手" else "後手"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
            .setTitle("詰み")
            .setMessage("${winner}の勝ち")
            .setPositiveButton("タイトルに戻る", null)
            .setNegativeButton("ログを再生する", null)
        return builder.create()
    }
}
