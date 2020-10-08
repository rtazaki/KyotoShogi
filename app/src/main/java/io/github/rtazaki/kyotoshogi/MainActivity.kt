package io.github.rtazaki.kyotoshogi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.rtazaki.kyotoshogi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 駒操作
        binding.iv11.setOnClickListener {
            common(5, 1)
        }
        binding.iv12.setOnClickListener {
            common(4, 1)
        }
        binding.iv13.setOnClickListener {
            common(3, 1)
        }
        binding.iv14.setOnClickListener {
            common(2, 1)
        }
        binding.iv15.setOnClickListener {
            common(1, 1)
        }
        binding.iv21.setOnClickListener {
            common(5, 2)
        }
        binding.iv22.setOnClickListener {
            common(4, 2)
        }
        binding.iv23.setOnClickListener {
            common(3, 2)
        }
        binding.iv24.setOnClickListener {
            common(2, 2)
        }
        binding.iv25.setOnClickListener {
            common(1, 2)
        }
        binding.iv31.setOnClickListener {
            common(5, 3)
        }
        binding.iv32.setOnClickListener {
            common(4, 3)
        }
        binding.iv33.setOnClickListener {
            common(3, 3)
        }
        binding.iv34.setOnClickListener {
            common(2, 3)
        }
        binding.iv35.setOnClickListener {
            common(1, 3)
        }
        binding.iv41.setOnClickListener {
            common(5, 4)
        }
        binding.iv42.setOnClickListener {
            common(4, 4)
        }
        binding.iv43.setOnClickListener {
            common(3, 4)
        }
        binding.iv44.setOnClickListener {
            common(2, 4)
        }
        binding.iv45.setOnClickListener {
            common(1, 4)
        }
        binding.iv51.setOnClickListener {
            common(5, 5)
        }
        binding.iv52.setOnClickListener {
            common(4, 5)
        }
        binding.iv53.setOnClickListener {
            common(3, 5)
        }
        binding.iv54.setOnClickListener {
            common(2, 5)
        }
        binding.iv55.setOnClickListener {
            common(1, 5)
        }
    }

    /**
     * 画面配置的には、左上→右下だが、将棋は右上→左下なのでボタンイベントを置き換える。
     * まずは泥臭く実装する。
     * (グループ化して、クリックリスナーでまとめるのが一番いい気がする。)
     * @param column 筋
     * @param row 段
     */
    private fun common(column: Int, row: Int) {
        Log.d("駒", "column: $column   row: $row")
    }
}