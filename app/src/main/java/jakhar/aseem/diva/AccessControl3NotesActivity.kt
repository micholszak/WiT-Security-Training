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
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import jakhar.aseem.diva.databinding.ActivityAccessControl3NotesBinding

class AccessControl3NotesActivity : BindingActivity<ActivityAccessControl3NotesBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityAccessControl3NotesBinding::inflate)

        binding.aci3naccessbutton.setOnClickListener {
            accessNotes()
        }
    }

    fun accessNotes() {
        val spref = getSharedPreferences()
        val pin = spref.getString(getString(R.string.pkey), "").orEmpty()
        val userpin = binding.aci3notesPinText.text.toString()

        // XXX Easter Egg?
        if (userpin == pin) {
            // Display the private notes
            val cr = contentResolver.query(
                NotesProvider.CONTENT_URI,
                arrayOf("_id", "title", "note"),
                null,
                null,
                null
            )
            val columns = arrayOf(NotesProvider.C_TITLE, NotesProvider.C_NOTE)
            val fields = intArrayOf(R.id.title_entry, R.id.note_entry)
            val adapter = SimpleCursorAdapter(this, R.layout.notes_entry, cr, columns, fields, 0)
            binding.aci3nlistView.adapter = adapter
            binding.aci3notesPinText.visibility = View.INVISIBLE
            binding.aci3naccessbutton.visibility = View.INVISIBLE
            //cr.close();
        } else {
            Toast.makeText(this, "Please Enter a valid pin!", Toast.LENGTH_SHORT).show()
        }
    }
}