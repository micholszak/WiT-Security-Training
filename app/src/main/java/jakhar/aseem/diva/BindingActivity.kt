package jakhar.aseem.diva

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<B : ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: B
    protected val binding get() = _binding

    protected fun setContentView(bindingInflater: (LayoutInflater) -> B) {
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }
}