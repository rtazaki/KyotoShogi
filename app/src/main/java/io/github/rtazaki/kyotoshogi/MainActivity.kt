package io.github.rtazaki.kyotoshogi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.rtazaki.kyotoshogi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /**
     * ビューバインディング
     * **See:** [ViewBinding応用](https://matsudamper.hatenablog.com/entry/2019/10/29/013329)
     */
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /**
     * ポジション ⇔ 画面
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
    private val hands by lazy {
        mapOf(
            true to listOf(
                binding.player1Hands1,
                binding.player1Hands2,
                binding.player1Hands3,
                binding.player1Hands4,
            ),
            false to listOf(
                binding.player2Hands1,
                binding.player2Hands2,
                binding.player2Hands3,
                binding.player2Hands4,
            )
        )
    }

    /**
     * 手番
     * true: player1(先手)
     * false: player2(後手)
     * NOTE: 将来的に、先/後反転処理を加えた場合や、
     * 盤面をひっくり返した場合に先手/後手の意味合いが変わる可能性がある。
     */
    private var turn = true

    /**
     * 最後に動かした駒
     */
    private var latest = MainGame.Pos(0, 0)

    fun getPutPiece(putPiece: CharSequence) {
        Log.d("駒", "putPiece: $putPiece")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        val mainGame = MainGame()
        val rotation = mapOf(true to 0.0F, false to 180.0F)
        // 初期配置
        player.forEach { (turn, player) ->
            player.piece.forEach { p ->
                val pos = if (turn) p.pos else {
                    mainGame.getMirrorPos(p.pos)
                }
                mapPtoB.getValue(pos).text = p.name
                mapPtoB.getValue(pos).rotation = rotation.getValue(turn)
            }
        }
        // 打ち駒
        hands.forEach { (key, value) ->
            value.forEach { hands ->
                hands.setOnClickListener {
                    if (key == turn && hands.text.isNotBlank()) {
                        val dialog = PutPieceDialogFragment(hands.text)
                        dialog.show(supportFragmentManager, "PPDialog")
                    }
                }
            }
        }
        // 盤処理
        mapBtoP.keys.forEach { b ->
            b.setOnClickListener {
                run loop@{
                    player.getValue(turn).move.forEach { m ->
                        if (m == mapBtoP.getValue(b)) {
                            Log.d("駒", "ターン変更: ${mapBtoP.getValue(b)}")
                            mapPtoB.getValue(m).text =
                                mainGame.invertPiece(mapPtoB.getValue(player.getValue(turn).select).text)
                            mapPtoB.getValue(m).rotation = rotation.getValue(turn)
                            mapPtoB.getValue(player.getValue(turn).select).text = ""
                            mainGame.changePiece(m, player.getValue(turn), mirror = !turn)
                            // 持ち駒更新
                            val convertHandsName =
                                mainGame.changeEnemyPiece(m, player.getValue(!turn), mirror = turn)
                            if (convertHandsName != "") {
                                updateHands(convertHandsName)
                            }
                            latest = m
                            turn = !turn
                            return@loop
                        }
                    }
                }
                refreshBoard()
                mainGame.clearMoveSelect(player)
                player.getValue(turn).piece.forEach { p ->
                    val pos = if (turn) p.pos else {
                        mainGame.getMirrorPos(p.pos)
                    }
                    if (pos == mapBtoP.getValue(b)) {
                        Log.d("駒", "選択: ${b.text}")
                        b.setBackgroundResource(R.drawable.button_background_select)
                        player.getValue(turn).select = pos
                        player.getValue(turn).move =
                            mainGame.getMovePos(
                                p,
                                player.getValue(turn),
                                player.getValue(!turn),
                                mirror = !turn
                            )
                        Log.d("駒", "稼働範囲: ${player.getValue(turn).move}")
                        player.getValue(turn).move.forEach { m ->
                            mapPtoB.getValue(m)
                                .setBackgroundResource(R.drawable.button_background_move)
                        }
                    }
                }
            }
        }
    }

    /**
     * 持ち駒更新
     * @param convertHandsName 持ち駒
     */
    private fun updateHands(convertHandsName: CharSequence) {
        run loop@{
            hands.getValue(turn).forEach {
                if (it.text.isBlank()) {
                    it.text = convertHandsName
                    return@loop
                } else if (it.text == convertHandsName) {
                    val tmp = "${convertHandsName}×2"
                    it.text = tmp
                    return@loop
                }
            }
        }
    }

    /**
     * 盤面リフレッシュ
     */
    private fun refreshBoard() {
        mapBtoP.keys.forEach { it.setBackgroundResource(R.drawable.button_background) }
        if (latest != MainGame.Pos(0, 0)) {
            mapPtoB.getValue(latest)
                .setBackgroundResource(R.drawable.button_background_latest)
        }
    }
}