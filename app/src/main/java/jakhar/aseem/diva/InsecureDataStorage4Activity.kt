/*
 * DIVA Android - Damn Insecure and Vulnerable App for Android
 *
 * Copyright 2016 © Payatu
 * Author: Aseem Jakhar aseem[at]payatu[dot]com
 * Websites: www.payatu.com  www.nullcon.net  www.hardwear.io www.null.co.in
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */
package jakhar.aseem.diva

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import jakhar.aseem.diva.databinding.ActivityInsecureDataStorage4Binding
import java.io.File

class InsecureDataStorage4Activity : BindingActivity<ActivityInsecureDataStorage4Binding>() {

    companion object {
        private const val PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted) {
            saveCredentials()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityInsecureDataStorage4Binding::inflate)

        binding.ids4button.setOnClickListener {
            tryToSaveCredentials()
        }
    }

    private fun tryToSaveCredentials() {
        if(ContextCompat.checkSelfPermission(this, PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            saveCredentials()
        } else {
            requestPermissionLauncher.launch(PERMISSION)
        }
    }

    private fun saveCredentials() {
        val sdir = Environment.getExternalStorageDirectory()
        try {
            if (!sdir.exists()) {
                sdir.createNewFile()
            }
            val uinfo = File(sdir!!.absolutePath + "/.uinfo.txt")

            uinfo.setReadable(true)
            uinfo.setWritable(true)
            uinfo.printWriter().use { output ->
                output.println("${binding.ids4Usr.text}:${binding.ids4Pwd.text}")
            }
            Toast.makeText(this, "3rd party credentials saved successfully!", Toast.LENGTH_SHORT)
                .show()
            // Now you can read the temporary file where ever the credentials are required.
        } catch (e: Exception) {
            Toast.makeText(this, "File error occurred", Toast.LENGTH_SHORT).show()
            Log.d("Diva", "File error: ${e.message}")
        }
    }
}