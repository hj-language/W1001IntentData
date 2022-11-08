package kr.ac.kumoh.s20200370.w1001intentdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import kr.ac.kumoh.s20200370.w1001intentdata.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityImageBinding
    private var image: String? = null
    companion object {
        const val imageName = "image"
        const val resultName = "result"

        const val LIKE = 10
        const val DISLIKE = 20
        const val NONE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        image = intent.getStringExtra(MainActivity.keyName)
        var res = when (image) {
            "gundam" -> R.drawable.a
            "zaku" -> R.drawable.b
            else -> R.drawable.ic_launcher_foreground // default가 아니라 else!
        }
        binding.imgGundam.setImageResource(res)
        binding.btnLike.setOnClickListener(this)
        binding.btnDislike.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent()
        val value = when (v?.id) {
            binding.btnLike.id -> LIKE
            binding.btnDislike.id -> DISLIKE
            else -> NONE
        }
        intent.putExtra(imageName, image)   // callee -> caller 도 putExtra 이용!
        intent.putExtra(resultName, value)
        // callee는 caller를 startActivity하는 게 아니라 그냥 돌아가는 것!
        setResult(RESULT_OK, intent)        // RESULT_OK: 정상적으로 돌아간다.
        finish()                            // onDestroy까지 진행
    }
}