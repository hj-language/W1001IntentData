package kr.ac.kumoh.s20200370.w1001intentdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.kumoh.s20200370.w1001intentdata.databinding.ActivityMainBinding

// OnClickListener는 메소드를 하나만 가지고 있는 인터페이스
class MainActivity : AppCompatActivity(), OnClickListener {
    companion object {
        const val keyName = "image"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 내 객체 안에 onClick이 있어!
        // 만약 다른 객체 안에 구현돼있다면 그 클래스의 객체를 만들고 그걸 넣어야 함
        binding.btnGundam.setOnClickListener(this)
        binding.btnZaku.setOnClickListener(this)

        // Set launcher. 못외우겠다 !!! 교수님이 못 외우는 건 안 내실 가능성이 ... 나중에 생각해보자
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { // result 가 넘어왔을 때 실행할 콜백함수!
            if (it.resultCode != RESULT_OK)
                return@registerForActivityResult // 어디까지 return 할 건지 지정

            // getStringExtra에서 값이 없으면 null이 반환되지만
            // getIntExtra에서는 값이 없으면... -1? 0? 애매하다. => 두 번째 인자로 명시해주어야 함!
            val str = when (it.data?.getIntExtra(ImageActivity.resultName, ImageActivity.NONE)) {
                ImageActivity.LIKE -> "좋아요"
                ImageActivity.DISLIKE -> "싫어요"
                else -> ""
            }

            when (it.data?.getStringExtra(ImageActivity.imageName)) {
                "gundam" -> binding.btnGundam.text = "건담 ($str)"
                "zaku" -> binding.btnZaku.text = "자쿠 ($str)"
            }
        }
    }

    // OnClickListener의 SAM(Single Abstract Method)
    override fun onClick(v: View?) {
        val intent = Intent(this, ImageActivity::class.java)
        val value = when (v?.id) {
            binding.btnGundam.id -> "gundam"
            binding.btnZaku.id -> "zaku"
            else -> return // startActivity를 하지 않음
        }
        intent.putExtra(keyName, value)
        launcher.launch(intent)
    }
}