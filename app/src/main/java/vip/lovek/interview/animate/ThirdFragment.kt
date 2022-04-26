package vip.lovek.interview.animate

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vip.lovek.interview.R
import vip.lovek.interview.databinding.FragmentThirdBinding
import vip.lovek.interview.utils.Utils

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    val provinceList = listOf(
        "北京市", "天津市", "上海市", "深圳市", "重庆市", "石家庄", "郑州市", "合肥市", "南昌市", "长沙市", "广州市", "武汉市",
        "北京市", "天津市", "上海市", "深圳市", "重庆市", "石家庄", "郑州市", "合肥市", "南昌市", "长沙市", "广州市", "武汉市"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonThird.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        val animator = binding.iv.animate().translationX(Utils.dp2px(400F)).setInterpolator(DecelerateInterpolator())
        animator.startDelay = 1000
        animator.start()

        val targetPoint = Point(Utils.dp2px(300F).toInt(), Utils.dp2px(200F).toInt());
        val objectAnimator = ObjectAnimator.ofObject(binding.point, "point", PointEvaluator(), targetPoint)
        objectAnimator.startDelay = 1000
        objectAnimator.duration = 1000
        objectAnimator.start()

        val provinceEvaluator = ObjectAnimator.ofObject(
            binding.province, "province", ProvinceEvaluator(), provinceList.last()
        )
        provinceEvaluator.duration = 2000
        provinceEvaluator.start()

        ObjectAnimator.ofFloat(binding.circleView, "radius", Utils.dp2px(100F)).start()

        binding.buttonThird.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FourthFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class PointEvaluator : TypeEvaluator<Point> {
        override fun evaluate(fraction: Float, startValue: Point, endValue: Point): Point {
            val x = startValue.x + (endValue.x - startValue.x) * fraction
            val y = startValue.y + (endValue.y - startValue.y) * fraction
            return Point(x.toInt(), y.toInt())
        }
    }

    inner class ProvinceEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
            val startIndex = provinceList.indexOf(startValue)
            val endIndex = provinceList.indexOf(endValue)
            val index = startIndex + (endIndex - startIndex) * fraction
            return provinceList[index.toInt()]
        }
    }
}