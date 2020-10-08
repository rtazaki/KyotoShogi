package io.github.rtazaki.kyotoshogi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.rtazaki.kyotoshogi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}