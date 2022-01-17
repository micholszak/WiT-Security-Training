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

import android.os.Bundle
import android.view.View
import android.widget.Toast
import jakhar.aseem.diva.databinding.ActivityApicreds2Binding

class APICreds2Activity : BindingActivity<ActivityApicreds2Binding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityApicreds2Binding::inflate)
        val intent = intent
        val bcheck = intent.getBooleanExtra(getString(R.string.chk_pin), true)
        if (!bcheck) {
            // Connect to vendor cloud and send User PIN
            // Get API credentials and other confidential details of the user
            val apidetails =
                "TVEETER API Key: secrettveeterapikey\nAPI User name: diva2\nAPI Password: p@ssword2"
            // Display the details on the app
            binding.apic2TextView.text = apidetails
        } else {
            binding.apic2TextView.text =
                "Register yourself at http://payatu.com to get your PIN and then login with that PIN!"
            binding.aci2pinText.visibility = View.VISIBLE
            binding.aci2button.visibility = View.VISIBLE
        }

        binding.aci2button.setOnClickListener {
            viewCreds()
        }
    }

    private fun viewCreds() {
        //Save user PIN
        // Connect to vendor cloud and send User PIN
        // Get API credentials and other confidential details of the user
        // If PIN is wrong ask user to enter correct pin
        Toast.makeText(this, "Invalid PIN. Please try again", Toast.LENGTH_SHORT).show()
    }
}