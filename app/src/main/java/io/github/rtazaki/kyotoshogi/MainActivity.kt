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
     * 棋譜は筋段(列行)で表現するため、ソフト側も同じロジックで実装を行う。
     * まずは愚直に実装する。(id名から行列をパースするのはなお泥臭い。)
     * @param column 筋
     * @param row 段
     */
    private val mapPtoB by lazy {
        mapOf(
            Player.Pos(1, 1) to binding.board11,
            Player.Pos(1, 2) to binding.board12,
            Player.Pos(1, 3) to binding.board13,
            Player.Pos(1, 4) to binding.board14,
            Player.Pos(1, 5) to binding.board15,
            Player.Pos(2, 1) to binding.board21,
            Player.Pos(2, 2) to binding.board22,
            Player.Pos(2, 3) to binding.board23,
            Player.Pos(2, 4) to binding.board24,
            Player.Pos(2, 5) to binding.board25,
            Player.Pos(3, 1) to binding.board31,
            Player.Pos(3, 2) to binding.board32,
            Player.Pos(3, 3) to binding.board33,
            Player.Pos(3, 4) to binding.board34,
            Player.Pos(3, 5) to binding.board35,
            Player.Pos(4, 1) to binding.board41,
            Player.Pos(4, 2) to binding.board42,
            Player.Pos(4, 3) to binding.board43,
            Player.Pos(4, 4) to binding.board44,
            Player.Pos(4, 5) to binding.board45,
            Player.Pos(5, 1) to binding.board51,
            Player.Pos(5, 2) to binding.board52,
            Player.Pos(5, 3) to binding.board53,
            Player.Pos(5, 4) to binding.board54,
            Player.Pos(5, 5) to binding.board55,
        )
    }
    private val mapBtoP by lazy {
        mapOf(
            binding.board11 to Player.Pos(1, 1),
            binding.board12 to Player.Pos(1, 2),
            binding.board13 to Player.Pos(1, 3),
            binding.board14 to Player.Pos(1, 4),
            binding.board15 to Player.Pos(1, 5),
            binding.board21 to Player.Pos(2, 1),
            binding.board22 to Player.Pos(2, 2),
            binding.board23 to Player.Pos(2, 3),
            binding.board24 to Player.Pos(2, 4),
            binding.board25 to Player.Pos(2, 5),
            binding.board31 to Player.Pos(3, 1),
            binding.board32 to Player.Pos(3, 2),
            binding.board33 to Player.Pos(3, 3),
            binding.board34 to Player.Pos(3, 4),
            binding.board35 to Player.Pos(3, 5),
            binding.board41 to Player.Pos(4, 1),
            binding.board42 to Player.Pos(4, 2),
            binding.board43 to Player.Pos(4, 3),
            binding.board44 to Player.Pos(4, 4),
            binding.board45 to Player.Pos(4, 5),
            binding.board51 to Player.Pos(5, 1),
            binding.board52 to Player.Pos(5, 2),
            binding.board53 to Player.Pos(5, 3),
            binding.board54 to Player.Pos(5, 4),
            binding.board55 to Player.Pos(5, 5),
        )
    }

    /**
     * 手番
     */
    private var turn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 初期配置
        val player1 = Player()
        for (p in player1.getOnBoards()) {
            mapPtoB.getValue(p.boardPos).text = p.onBoards
        }
        val player2 = Player()
        for (p in player2.getOnBoards()) {
            val mirrorPos = player2.getMirrorPos(p.boardPos)
            mapPtoB.getValue(mirrorPos).text = p.onBoards
            mapPtoB.getValue(mirrorPos).rotation = 180.0F
        }
        for (b in mapBtoP.keys) {
            b.setOnClickListener {
                if (turn) {
                    if (b.text != "" && b.rotation != 180.0F) {
                        Log.d("駒▲", "${b.text}")
                        turn = false
                    }
                } else {
                    if (b.text != "" && b.rotation != 0.0F) {
                        Log.d("駒△", "${b.text}")
                        turn = true
                    }
                }
            }
        }
    }
}