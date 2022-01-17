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

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import jakhar.aseem.diva.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding::inflate)
        setSupportActionBar(binding.toolbar)

        binding.content.d1button.setOnClickListener {
            startActivity<LogActivity>()
        }
        binding.content.d2button.setOnClickListener {
            startActivity<HardcodeActivity>()
        }
        binding.content.d3button.setOnClickListener {
            startActivity<InsecureDataStorage1Activity>()
        }
        binding.content.d4button.setOnClickListener {
            startActivity<InsecureDataStorage2Activity>()
        }
        binding.content.d5button.setOnClickListener {
            startActivity<InsecureDataStorage3Activity>()
        }
        binding.content.d6button.setOnClickListener {
            startActivity<InsecureDataStorage4Activity>()
        }
        binding.content.d7button.setOnClickListener {
            startActivity<SQLInjectionActivity>()
        }
        binding.content.d8button.setOnClickListener {
            startActivity<InputValidation2URISchemeActivity>()
        }
        binding.content.d9button.setOnClickListener {
            startActivity<AccessControl1Activity>()
        }
        binding.content.d10button.setOnClickListener {
            startActivity<AccessControl2Activity>()
        }
        binding.content.d11button.setOnClickListener {
            startActivity<AccessControl3Activity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private inline fun <reified T> startActivity() {
        Intent(this, T::class.java).start()
    }

    private fun Intent.start() {
        startActivity(this)
    }
}