package jj.appproject.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvResult : TextView? = null // TextView == 사용자에게 text를 표시하는 사용자 인터페이스
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult) // findViewById == activity_main.xml에서 적용 시켰던 글자 특징 (글꼴, 색상, 스타일) 등을 변경할 수 있는 method

    }

    fun onDigit(view: View){
        tvResult?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View){
        tvResult?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvResult?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){   // nullable 형식의 기능 사용위해  -> let 사용
        tvResult?.text?.let{

            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvResult?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvResult?.text.toString() // text는 charSequence이기 때문에 문자열로 변환해주자
            var prefix = ""

            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1) // 설정한 startindex 앞의 index값들은 버려준다.
                }


                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
                else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace() // printStackTrace() == error의 근원지를 찾아서 단계별로 error를 출력
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}