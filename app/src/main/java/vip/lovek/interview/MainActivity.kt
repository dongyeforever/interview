package vip.lovek.interview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import vip.lovek.annotation.ARouter
import vip.lovek.annotation.AsmTimer
import vip.lovek.interview.aidl.AidlActivity
import vip.lovek.interview.animate.AnimateMainActivity
import vip.lovek.interview.base.BaseActivity
import vip.lovek.interview.base.life.LifeActivityA
import vip.lovek.interview.databinding.ActivityMainBinding
import vip.lovek.interview.mvvm.MvvmActivity
import vip.lovek.interview.plugin.PluginActivity
import vip.lovek.interview.third_library.OkioActivity
import vip.lovek.interview.third_library.retrofit.OkhttpActivity
import vip.lovek.interview.third_library.retrofit.RetrofitActivity


/**
 * author： yuzhirui@douban.com
 * date: 2022-02-14 16:27
 */
@ARouter(path = "/app/main")
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler: Handler = Handler(Looper.getMainLooper())

    @AsmTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    @AsmTimer
    private fun initView() {
        binding.btnView.setOnClickListener {
            startActivity(Intent(this, AnimateMainActivity::class.java))
        }

        binding.btnPlugin.setOnClickListener {
            startActivity(Intent(this, PluginActivity::class.java))
        }
        binding.btnOkio.setOnClickListener {
            startActivity(Intent(this, OkioActivity::class.java))
//            Log.e("Tag", "发送消息消息")
//            handler.postDelayed({
//                Log.e("Tag", "3s 收到消息")
//            }, 3000)
//            Thread.sleep(8000)
//            Log.e("Tag", "主线程 8s 收到消息")
        }
        binding.btnOkhttp.setOnClickListener {
            startActivity(Intent(this, OkhttpActivity::class.java))
        }

        binding.btnRetrofit.setOnClickListener {
            startActivity(Intent(this, RetrofitActivity::class.java))
        }
        binding.btnAidl.setOnClickListener {
            startActivity(Intent(this, AidlActivity::class.java))
        }

        binding.btnLife.setOnClickListener {
            startActivity(Intent(this, LifeActivityA::class.java))
        }

        binding.btnMvvm.setOnClickListener {
            startActivity(Intent(this, MvvmActivity::class.java))
        }
    }
}