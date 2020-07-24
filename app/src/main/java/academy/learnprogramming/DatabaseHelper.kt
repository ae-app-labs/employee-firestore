package academy.learnprogramming

import com.google.firebase.firestore.FirebaseFirestore

class DatabaseHelper {

    private lateinit var database: FirebaseFirestore

    fun allEmployees(adapter: UserAdapter?) {
        val docRef = database.collection("users")
        docRef.addSnapshotListener{ snapshots, e ->

            if(e != null){
                return@addSnapshotListener;
            }

            adapter?.clear()
            var counter = 1

            for (user in snapshots!!){
                val u = User(user.id, user.getString("name"),
                    user.getString("address"),
                    (counter++).toString()
                )
                adapter?.add(u)
            }

        }
    }

    fun open() {
        database = FirebaseFirestore.getInstance()
    }

    fun add(name: String, address: String) {
        val data = hashMapOf(
            "name" to name,
            "address" to address
        )

        database.collection("users").add(data)
    }

    fun update(_id: String, name: String, address: String) {
        val ref = database.collection("users").document(_id)
        ref.update("name", name)
        ref.update("address", address)
    }

    fun delete(_id: String) {
        database.collection("users").document(_id).delete()
    }
}
