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
     * 手番
     * true: player1(先手)
     * false: player2(後手)
     * NOTE: 将来的に、先/後反転処理を加えた場合や、
     * 盤面をひっくり返した場合に先手/後手の意味合いが変わる可能性がある。
     */
    private var turn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val player = mapOf(true to MainGame.Player(), false to MainGame.Player())
        val mainGame = MainGame()
        // 初期配置
        player.forEach { (turn, player) ->
            player.piecePos.forEach { p ->
                val pos = if (turn) p.boardPos else {
                    mainGame.getMirrorPos(p.boardPos)
                }
                mapPtoB.getValue(pos).text = p.onBoards
                if (!turn) {
                    mapPtoB.getValue(pos).rotation = 180.0F
                }
            }
        }
        mapBtoP.keys.forEach { b ->
            b.setOnClickListener {
                player.getValue(turn).movePos.forEach { m ->
                    if (m == mapBtoP.getValue(b)) {
                        Log.d("駒", "ターン変更")
                        turn = !turn
                    }
                    mapBtoP.keys.forEach { it.setBackgroundResource(R.drawable.button_background) }
                }
                player.getValue(turn).piecePos.forEach { p ->
                    val pos = if (turn) p.boardPos else {
                        mainGame.getMirrorPos(p.boardPos)
                    }
                    if (pos == mapBtoP.getValue(b)) {
                        Log.d("駒", "選択: ${b.text}")
                        b.setBackgroundResource(R.drawable.button_background_select)
                        player.getValue(turn).selectPos = p.boardPos
                        player.getValue(turn).movePos =
                            mainGame.getMovePos(
                                p, player.getValue(turn), player.getValue(!turn), mirror = !turn
                            )
                        Log.d("駒", "稼働範囲: ${player.getValue(turn).movePos}")
                        player.getValue(turn).movePos.forEach { m ->
                            mapPtoB.getValue(m)
                                .setBackgroundResource(R.drawable.button_background_move)
                        }
                    }
                }
            }
        }
    }
}