package io.github.rtazaki.kyotoshogi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.rtazaki.kyotoshogi.databinding.ActivityMainBinding

/**
 * メイン画面
 */
class MainActivity : AppCompatActivity() {
    /**
     * ビューバインディング
     * **See:** [ViewBinding応用](https://matsudamper.hatenablog.com/entry/2019/10/29/013329)
     */
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /**
     * ポジション → 画面
     * 画面配置的には、左上→右下だが、将棋は右上→左下
     * (column:筋, row:段で表現。 )
     * 棋譜は筋段(列行)で表現するため、ソフト側も同じロジックで実装を行う。
     * まずは愚直に実装する。(id名から行列をパースするのはなお泥臭い。)
     */
    private val mapPtoB by lazy {
        mapOf(
            MainGame.Pos(1, 1) to binding.board11,
            MainGame.Pos(1, 2) to binding.board12,
            MainGame.Pos(1, 3) to binding.board13,
            MainGame.Pos(1, 4) to binding.board14,
            MainGame.Pos(1, 5) to binding.board15,
            MainGame.Pos(2, 1) to binding.board21,
            MainGame.Pos(2, 2) to binding.board22,
            MainGame.Pos(2, 3) to binding.board23,
            MainGame.Pos(2, 4) to binding.board24,
            MainGame.Pos(2, 5) to binding.board25,
            MainGame.Pos(3, 1) to binding.board31,
            MainGame.Pos(3, 2) to binding.board32,
            MainGame.Pos(3, 3) to binding.board33,
            MainGame.Pos(3, 4) to binding.board34,
            MainGame.Pos(3, 5) to binding.board35,
            MainGame.Pos(4, 1) to binding.board41,
            MainGame.Pos(4, 2) to binding.board42,
            MainGame.Pos(4, 3) to binding.board43,
            MainGame.Pos(4, 4) to binding.board44,
            MainGame.Pos(4, 5) to binding.board45,
            MainGame.Pos(5, 1) to binding.board51,
            MainGame.Pos(5, 2) to binding.board52,
            MainGame.Pos(5, 3) to binding.board53,
            MainGame.Pos(5, 4) to binding.board54,
            MainGame.Pos(5, 5) to binding.board55,
        )
    }

    /**
     * 画面 → ポジション
     * keyのみで使うときもある(盤面ボタンの一覧が欲しい時など)
     */
    private val mapBtoP by lazy {
        mapOf(
            binding.board11 to MainGame.Pos(1, 1),
            binding.board12 to MainGame.Pos(1, 2),
            binding.board13 to MainGame.Pos(1, 3),
            binding.board14 to MainGame.Pos(1, 4),
            binding.board15 to MainGame.Pos(1, 5),
            binding.board21 to MainGame.Pos(2, 1),
            binding.board22 to MainGame.Pos(2, 2),
            binding.board23 to MainGame.Pos(2, 3),
            binding.board24 to MainGame.Pos(2, 4),
            binding.board25 to MainGame.Pos(2, 5),
            binding.board31 to MainGame.Pos(3, 1),
            binding.board32 to MainGame.Pos(3, 2),
            binding.board33 to MainGame.Pos(3, 3),
            binding.board34 to MainGame.Pos(3, 4),
            binding.board35 to MainGame.Pos(3, 5),
            binding.board41 to MainGame.Pos(4, 1),
            binding.board42 to MainGame.Pos(4, 2),
            binding.board43 to MainGame.Pos(4, 3),
            binding.board44 to MainGame.Pos(4, 4),
            binding.board45 to MainGame.Pos(4, 5),
            binding.board51 to MainGame.Pos(5, 1),
            binding.board52 to MainGame.Pos(5, 2),
            binding.board53 to MainGame.Pos(5, 3),
            binding.board54 to MainGame.Pos(5, 4),
            binding.board55 to MainGame.Pos(5, 5),
        )
    }

    /**
     * 持ち駒
     */
    private val mapHands by lazy {
        mapOf(
            true to setOf(
                binding.player1Hands1,
                binding.player1Hands2,
                binding.player1Hands3,
                binding.player1Hands4,
            ),
            false to setOf(
                binding.player2Hands1,
                binding.player2Hands2,
                binding.player2Hands3,
                binding.player2Hands4,
            )
        )
    }

    /**
     * プレイヤー
     */
    private val players = mapOf(true to MainGame.Player(), false to MainGame.Player())

    /**
     * 手番
     * true: player1(先手)
     * false: player2(後手)
     * NOTE: 将来的に、先/後反転処理を加えた場合や、
     * 盤面をひっくり返した場合に先手/後手の意味合いが変わる可能性がある。
     */
    private var turn = true

    /**
     * 最後に動かした駒(位置)
     * (色付きかつ駒の向きにより、先手/後手が区別できる)
     */
    private var latest = MainGame.Pos(0, 0)

    /**
     * 選択した駒(位置)
     *  0,  0: 無選択
     * それ以外: 盤面の駒
     */
    private var select = MainGame.Pos(0, 0)

    /**
     * 移動可能範囲(位置)
     */
    private var moves: MutableSet<MainGame.Pos> = mutableSetOf()

    /**
     * 押された駒名
     * "": 押されていない
     * それ以外: 押された駒名
     */
    private var putPieceName: CharSequence = ""

    /**
     * ダイアログから、押された駒名を取得
     * @param putPiece 押された駒名
     */
    fun setPutPiece(putPiece: CharSequence) {
        Log.d("駒", "putPiece: $putPiece")
        putPieceName = putPiece
        moves = MainGame.getPutPiecePos(players, putPiece, turn)
        moves.forEach { move ->
            mapPtoB.getValue(move)
                .setBackgroundResource(R.drawable.button_background_move)
        }
    }

    /**
     * onCreate
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val rotation = mapOf(true to 0.0F, false to 180.0F)

        // 初期配置
        players.forEach { (turn, player) ->
            player.pieces[MainGame.Pos(5, 5)] = "と"
            player.pieces[MainGame.Pos(4, 5)] = "銀"
            player.pieces[MainGame.Pos(3, 5)] = "玉"
            player.pieces[MainGame.Pos(2, 5)] = "金"
            player.pieces[MainGame.Pos(1, 5)] = "歩"
            player.pieces.forEach { piece ->
                val pos = if (!turn) MainGame.getMirrorPos(piece.key) else piece.key
                mapPtoB.getValue(pos).text = piece.value
                mapPtoB.getValue(pos).rotation = rotation.getValue(turn)
                mapPtoB.getValue(pos).setBackgroundResource(R.drawable.button_background_piece)
            }
        }

        // 打ち駒
        mapHands.forEach { (key, value) ->
            value.forEach { hands ->
                hands.setOnClickListener {
                    // 盤面リフレッシュ
                    refreshBoard()
                    if (key == turn && hands.text.isNotBlank()) {
                        val dialog = PutPieceDialogFragment(hands)
                        dialog.show(supportFragmentManager, "PPDialog")
                    }
                }
            }
        }

        // 画面外
        binding.root.setOnClickListener {
            Log.d("駒", "画面外押下")
            // 盤面リフレッシュ
            refreshBoard()
        }

        // 盤処理
        mapBtoP.keys.forEach { button ->
            button.setOnClickListener {
                val buttonPos = mapBtoP.getValue(button)
                // 駒移動 → ターン変更
                if (moves.containsAll(listOf(buttonPos))) {
                    Log.d("駒", "ターン変更: $buttonPos")

                    // 打ち駒の場合
                    if (putPieceName != "") {
                        mapPtoB.getValue(buttonPos).text = putPieceName
                        mapPtoB.getValue(buttonPos).rotation = rotation.getValue(turn)
                        MainGame.addPutPiece(
                            putPieceName, buttonPos, players.getValue(turn), mirror = !turn
                        )
                    } else {
                        mapPtoB.getValue(buttonPos).text =
                            MainGame.invertPiece(mapPtoB.getValue(select).text)
                        mapPtoB.getValue(buttonPos).rotation = rotation.getValue(turn)
                        mapPtoB.getValue(select).text = ""
                        MainGame.changePiece(
                            select, buttonPos, players.getValue(turn), mirror = !turn
                        )
                        MainGame.changeEnemyPiece(
                            buttonPos,
                            players.getValue(!turn),
                            players.getValue(turn),
                            mirror = turn
                        )
                    }
                    // 持ち駒更新
                    updateHands()
                    latest = buttonPos
                    turn = !turn
                    // 詰み判定
                    if (MainGame.isCheckMate(players, turn)) {
                        Log.d("駒", "詰み")
                    }
                }

                // 盤面リフレッシュ
                refreshBoard()

                // 駒選択
                val piece =
                    if (!turn) MainGame.getMirrorPos(buttonPos) else buttonPos
                if (players.getValue(turn).pieces.contains(piece)) {
                    Log.d("駒", "選択: ${button.text}")
                    button.setBackgroundResource(R.drawable.button_background_select)
                    select = buttonPos
                    moves =
                        MainGame.getMovePos(
                            piece = mapOf(piece to button.text).entries.first(),
                            players.getValue(turn),
                            players.getValue(!turn),
                            mirror = !turn
                        )
                    Log.d("駒", "稼働範囲: $moves")
                    moves.forEach { move ->
                        mapPtoB.getValue(move)
                            .setBackgroundResource(R.drawable.button_background_move)
                    }
                }
            }
        }
    }

    /**
     * 持ち駒更新
     */
    private fun updateHands() {
        val h = players.getValue(turn).hands.toMutableMap()
        mapHands.getValue(turn).forEach {
            // 一旦コピーを取って、見つかったら消していく。
            val name = it.text.replace(Regex("×\\d+"), "")
            when {
                h.containsKey(name) -> {
                    if (h.getValue(name) > 1) {
                        val tmp = "$name×${h.getValue(name)}"
                        it.text = tmp
                    } else {
                        it.text = name
                    }
                    h.remove(name)
                }
                h.isNotEmpty() -> {
                    it.text = h.entries.first().key
                    h.remove(it.text)
                }
                else -> {
                    it.text = ""
                }
            }
        }
    }

    /**
     * 盤面リフレッシュ
     */
    private fun refreshBoard() {
        mapBtoP.keys.forEach { button ->
            if (button.text == "") {
                button.setBackgroundResource(R.drawable.button_background)
                button.rotation = 0.0F
            } else {
                button.setBackgroundResource(R.drawable.button_background_piece)
            }
        }
        mapHands.values.forEach {
            it.forEach { button ->
                if (button.text == "") {
                    button.setBackgroundResource(R.drawable.button_background)
                } else {
                    button.setBackgroundResource(R.drawable.button_background_piece)
                }
            }
        }
        if (latest != MainGame.Pos(0, 0)) {
            mapPtoB.getValue(latest)
                .setBackgroundResource(R.drawable.button_background_latest)
        }
        select = MainGame.Pos(0, 0)
        moves.clear()
        putPieceName = ""
    }
}
